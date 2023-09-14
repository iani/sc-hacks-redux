/*  7 Jun 2023 15:45
Simple specialized class for handling input from Minibee sensors.

~spec = [0.44, 0.56].asSpec;

~spec.unmap(0.55)

*/

Minibee {
	classvar <>sensormsg = '/minibee/data';
	classvar <>numSensors = 12; // three sets of sense-stage, connected via osc groups
	classvar <all;
	classvar <values;
	classvar <>min = 0.44;
	classvar <>max = 0.56;
	classvar <>forwardAddr;
	classvar <of;
	classvar <>verbose = false;
	classvar <smoothEnabled = true;
	// ====================
	var <id = 1;
	var <busses;

	*enableSmoothing { this.smoothEnabled = true }
	*disableSmoothing { this.smoothEnabled = false }
	*smoothEnabled_ { | bool = true |
		smoothEnabled = bool;
		this.initSmoothing;
		this.changed(\smoothing);
	}
	*cmdPeriod { this.initSmoothing; }
	*initSmoothing {
		if (smoothEnabled) { this.makeSmoothForwarder } { this.stopSmoothForwarder }
	}
	*initClass { ServerBoot add: this; CmdPeriod add: this; }
	*doOnServerBoot { | server |
		// "Sensors do on server boot".postln
		// workaround for a bug: make sure the server is booted:
		server doWhenBooted: { // remake busses
			this.init;
		}
	}

	*testSendOsc {
		NetAddr("127.0.0.1", 1000).sendMsg(\testing, *Array.rand(10, 0, 10));
	}

	*init {
		// "Minibee initing".postln;
		this.makeForwardAddresses;
		// postln("all before initing is:" + all);
		all = { | i | this.new(i + 1) } ! numSensors; // 1-12
		// postln("all after initing is:" + all);
		this.getValues;
		this.makeSmoothForwarder;
	}

	*makeSmoothForwarder {
		var sendmsg = '/minibeesmooth';
		if (Server.default.serverRunning.not) {
			^"Minibee cannot start minibesmooth synth. Boot the default server".postln;
		};
		"Starting Minibee smooth synth".postln;
		{
			var sensorlags;
			sensorlags = (1..12).collect(_.slag).flat;
			SendReply.kr(Impulse.kr(50), '/minibeesmooth', sensorlags);
		} +> \minibeesmooth;
		\minibeesmooth >>> { | n, args |
			if (verbose) { postln("sending to of at: " + of + sendmsg + args[3..]); };
			of.sendMsg('/minibeesmooth', *args[3..]);
		};
		// CmdPeriod.addDependant
	}

	*stopSmoothForwarder { nil +> \minibeesmooth }

	*postSmooth { verbose = true }
	*unpostSmooth { verbose = false }
	*makeForwardAddresses {
		of = NetAddr("127.0.0.1", 10000);
		this.addForwardAddr(of);
		OscGroups.enable;
		this.addForwardAddr(OscGroups.sendAddress)
	}

	*addForwardAddr { | portnum = 10000 |
		if (portnum.isKindOf(NetAddr)) {
			this.getForwardAddr add: portnum;
		}{
			this.getForwardAddr add: NetAddr("127.0.0.1", portnum);
		}
	}

	*getForwardAddr {
		forwardAddr ?? { this.resetForwardAddr };
		^forwardAddr;
	}

	*resetForwardAddr { forwardAddr = Set() }

	*getValues {
		values = 0.dup(all.size * 3);
		all do: _.getBusValues;
	}

	*new { | id = 1 |
		^this.newCopyArgs(id).init;
	}

	getBusValues {
		var offset;
		offset = id - 1 * 3;
		busses do: { | b, i |
			b.get({ | val | values[offset + i] = val })
		};
	}

	init {
		this.makeBusses;
	}

	makeBusses {
		busses = [\x, \y, \z] collect: { | s |
			format("%%", s, id).asSymbol.sensorbus;
		}
	}

	*busses { ^all collect: _.busses }

	*enable {
		Server.default.waitForBoot({
			OSC addDependant: this; this.changed(\status);
			"Minibee enabled".postln;
		});
		OscGroups.enable;
	}

	*disable {
		OSC removeDependant: this; this.changed(\status);
		"Minibee disabled".postln;
	}

	*enabled { ^OSC.dependants includes: this }

	*update { | sender, cmd, msg |
		var index;
		switch(cmd,
			sensormsg, { // handle values input from local sensors
				index = msg[1];
				this.changed(\values, index, all[index - 1].input(msg[2..]));
			},
			'/minibee', { // handle values input via OscGroups
				index = msg[1];
				this.changed(\values, all[index - 1].inputScaled(msg[2..]));
			}
		);
		// if (cmd === sensormsg) }
	}

	input { | xyz |
		var scaledValues;
		scaledValues = xyz.linlin(min, max, 0.0, 1.0);
		// this.class.testSendOsc;
		// forwardAddr.postln;
		forwardAddr do: { | addr | addr.sendMsg('/minibee', id, *scaledValues) }
		^scaledValues do: { | val, i |
			values[id - 1 * 3 + i] = val;
			busses[i].set(val);
		}
	}

	inputScaled { | scaledValues |
		of.sendMsg('/minibee', id, *scaledValues);
		^scaledValues do: { | val, i |
			values[id - 1 * 3 + i] = val;
			busses[i].set(val);
		}
	}

	*gui {
		this.tr_.vlayout(
			MultiSliderView()
			.thumbSize_(5)
			.size_(3 * 25)
			.addNotifier(this, \values, { | n |
				n.listener.value_(values);
			})
		)
	}
}
