/*  7 Jun 2023 15:45
Simple specialized class for handling input from PosePerson sensors.

~spec = [0.44, 0.56].asSpec;

~spec.unmap(0.55)

frameN personInx frameConfidence resX resY (x y confidence) * 17
*/

PosePerson {
	classvar <>sensormsg = '/pose/person';
	classvar <>numSensors = 2; // three sets of sense-stage, connected via osc groups
	classvar <all;
	classvar <values;
	// classvar <>min = -2048;
	// classvar <>max = 2048;
	classvar <>keypoints;
	classvar <>confidenceMinGlobal = 0.7;
	classvar <>confidenceMin = 0.9;
	classvar <>forwardAddr;
	classvar <of;
	classvar <>verbose = false;
	classvar <enabled = false;
	classvar <smoothEnabled = false;
	// ====================
	var <id = 1;
	var <busses;
	var <>minX = 0, <>minY = 0;
	var <>maxX = 640, <>maxY = 640;

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
		if ( enabled, { // mc: but only, if enabled!
			server doWhenBooted: { // remake busses
				this.init;
			}
		})
	}

	*testSendOsc {
		NetAddr("127.0.0.1", 1000).sendMsg(\testing, *Array.rand(10, 0, 10));
	}

	*init {
		// "PosePerson initing".postln;
		this.makeForwardAddresses;
		// keypoints = ['nose', 'left_eye', 'right_eye', 'left_ear', 'right_ear', 'left_shoulder', 'right_shoulder', 'left_elbow', 'right_elbow', 'left_wrist', 'right_wrist', 'left_hip', 'right_hip', 'left_knee', 'right_knee', 'left_ankle', 'right_ankle'];
		keypoints = ['nose', 'eyeL', 'eyeR', 'earL', 'earR', 'shoulderL', 'shoulderR',
			'elbowL', 'elbowR', 'wristL', 'wristR', 'hipL', 'hipR', 'kneeL', 'kneeR',
			'ankleL', 'ankleR'];
		// postln("all before initing is:" + all);
		all = { | i | this.new(i + 1) } ! numSensors; // 1-12
		// postln("all after initing is:" + all);
		this.getValues;
		// if (smoothEnabled) { this.makeSmoothForwarder; }
	}

	*makeSmoothForwarder {
		var sendmsg = '/posepersonsmooth';
		// "PosePerson smooth synth not implemented".postln;
		if (Server.default.serverRunning.not) {
			^"PosePerson cannot start posepersonsmooth synth. Boot the default server".postln;
		};
		"Starting PosePerson smooth synth".postln;
		{
			var sensorlags;
			sensorlags = (1..2).collect(_.slag).flat;
			SendReply.kr(Impulse.kr(50), '/minibeesmooth', sensorlags);
		} +> \posepersonsmooth;
		\posepersonsmooth >>> { | n, args |
			if (verbose) { postln("sending to of at: " + of + sendmsg + args[18..]); };
			of.sendMsg('/posepersonsmooth', *args[18..]);
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
		values = 0.dup(all.size * (17 * 3 + 1));
		all do: _.getBusValues;
	}

	*new { | id = 1 |
		^this.newCopyArgs(id).init;
	}

	getBusValues {
		var offset;
		offset = id - 1 * 17 * 3  + 1;
		busses do: { | b, i |
			// postln("bus: " + i + " " + b);
			b.get({ | val | values[offset + i] = val })
		};
	}

	init {
		this.makeBusses;
	}

	makeBusses {
		busses = [format("%C", id).asSymbol.sensorbus];
		keypoints do: { | n | // n.postln;
			busses = busses ++
			[\x, \y, \c].collect{|s| format("%%%", id, n, s).asSymbol.sensorbus}
		}
	}

	*busses { ^all collect: _.busses }

	*enable {
		enabled = true;
		Server.default.waitForBoot({
			OSC addDependant: this; this.changed(\status);
			"PosePerson enabled".postln;
		});
		OscGroups.enable;
	}

	*disable {
		enabled = false;
		OSC removeDependant: this; this.changed(\status);
		"PosePerson disabled".postln;
	}

	*activated { ^OSC.dependants includes: this }
	// *enabled { ^OSC.dependants includes: this }

	*update { | sender, cmd, msg |
		var index;
		switch(cmd,
			sensormsg, { // handle values input from local sensors
				index = msg[2];
				this.changed(\values, index, all[index - 1].input(msg[3..]));
			},
			'/poseperson', { // handle values input via OscGroups
				index = msg[1];
				this.changed(\values, index, all[index - 1].inputScaled(msg[2..]));
			}
		);
	}

	input { | raw |
		var scaledValues;
		var confidence;
		// raw.postln;
		confidence = raw[0];
		maxX = raw[1];
		maxY = raw[2];
		scaledValues = [ confidence ];
		if ( confidence < confidenceMinGlobal, {
			scaledValues = scaledValues ++ 0.dup(17 * 3);
		},{
			raw = raw.drop(3);
			// raw[3..5].postln; // eyeL
			17 do: { | i |
				confidence = raw[ i * 3 + 2 ];
				if ( confidence < confidenceMin, {
					scaledValues = scaledValues ++ [ 0, 0, confidence ]
				},{
				scaledValues = scaledValues ++ [
					raw[ i * 3 ].linlin(minX, maxX, 0.0, 1.0)
					, raw[ i * 3 + 1 ].linlin(minY, maxY, 0.0, 1.0)
					, confidence // raw[ i * 3 + 2 ]
				]})};
		});
		// scaledValues[4..6].postln; // eyeL
		// this.class.testSendOsc;
		// forwardAddr.postln;
		forwardAddr do: { | addr | addr.sendMsg('/poseperson', id, *scaledValues) }
		^scaledValues do: { | val, i |
			values[id - 1 * (17 * 3 + 1) + i] = val;
			busses[i].set(val);
		}
	}

	inputScaled { | scaledValues |
		of.sendMsg('/poseperson', id, *scaledValues);
		^scaledValues do: { | val, i |
			values[id - 1 * (17 * 3 + 1) + i] = val;
			busses[i].set(val);
		}
	}

	*gui {
		this.enable;
		this.tr_.vlayout(
			MultiSliderView()
			.thumbSize_(5)
			.size_((17 * 3 + 1) * 2)
			.addNotifier(this, \values, { | n |
				n.listener.value_(values);
			})
		)
	}
}
