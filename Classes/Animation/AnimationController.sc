//:日 22  6 2025 00:52
// Control the currently playing animation:
// Provide access to its busses for writing, reading etc.
// When an AnimatinPlayer starts playing, it stores itself
// in the default instance of AnimationController.
// Multiple named AnimationControllers can be created
// To provide control to multiple concurrently playing AnimationPlayers.
// REMEMBER:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// 	*sendPort_ { | argPort = 22245 |
// REMEMBER:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

AnimationController : NamedSingleton2 {
	// REMEMBER:
	// 	*sendPort_ { | argPort = 22245 |
	classvar <>verbose = false;
	classvar <current; // current controller
	var <>player; // the player currently playing
	var <databus, <modbus, <dataJoints, <modJoints;
	var <oscMaker; // construct osc messages from polled number array
	var <synth, <pollRoutine;
	var <data; // polled from synth
	var <ctlSynths, <soundSynths;

	init {
		// init only once
		oscMaker !? { ^this };
		ctlSynths = MultiLevelIdentityDictionary();
		soundSynths = MultiLevelIdentityDictionary();
		this.makeOscMaker;
		this.makeBuses;
		this.makeSynth;
		this.makePollRoutine;
		CmdPeriod add: this;
	}

	makeOscMaker {
		oscMaker = RokokoJoints();
		oscMaker.activeJoints = RokokoJoints.joints;
	}

	doOnCmdPeriod {
		this.makeSynth;
		this.makePollRoutine;
	}

	makeBuses {
		this.makeDataBus;
		this.makeModBus;
		this.makeJoints;
	}


	makeDataBus {
		databus = Bus.control(Server.default, oscMaker.numChannels);
	}

	makeModBus {
		modbus = Bus.control(Server.default, oscMaker.numChannels);
	}

	makeJoints {
		var index;
		dataJoints = MultiLevelIdentityDictionary();
		modJoints = MultiLevelIdentityDictionary();
		oscMaker enumerate: { | i, j, v |
			dataJoints.put(j, v,
				Bus(\control, databus.index + i, 1, Server.default);
			);
			modJoints.put(j, v,
				Bus(\control, modbus.index + i, 1, Server.default);
			);
		}
	}

	makeSynth {
		synth = {
			var mod;
			mod = In.kr(modbus.index, modbus.numChannels);
			Out.kr(databus.index, mod);
		}.play;
	}

	makePollRoutine {
		pollRoutine = fork {
			loop {
				databus.getn(databus.numChannels, { | nums |
					if (data != nums) {
						data = nums;
						this.sendData;
					};
				});
				30.reciprocal.wait;
			}
		}
	}

	sendData {
		var msg;
		msg = oscMaker.makeOscMessage(data, name);
		if (verbose) { msg.postln; };
		this.changed(\msg, [msg]);
	}

	addListener { | addr |
		addr.asSymbol.addNotifier(this, \msg, { | n, msg |
			// postln("Sending to addr" + addr);
			// postln("Message:", msg);
			addr.sendMsg(*msg);
		})
	}

	removeListener { | addr |
		addr.asSymbol.removeNotifier(this, \msg);
	}

	setJoint { | joint, dim, value |
		this.modJoint(joint, dim).set(value);
	}

	modJoint { | joint, dim |
		^modJoints.at(joint, dim);
	}

	modIndex { | joint, dim |
		^this.modJoint(joint, dim).index;
	}

	dataJoint { | joint, dim |
		^dataJoints.at(joint, dim);
	}

	getCtl { | joint, dim | ^ctlSynths.at(joint, dim); }
	putCtl { | joint, dim, synth | ctlSynths.put(joint, dim, synth); }

	addCtl { | joint, dim, func |
		this.stopCtl(joint, dim);
		this.putCtl(
			joint, dim, func.play(outbus: this.modIndex(joint, dim))
		);
	}

	stopCtl { | joint, dim |
		this.getCtl(joint, dim).free;
	}
	stopCtls { // stop all synths and set control busses to 0
		var nullSynth;
		this.freeCtls;
		// add synth to zero mod bus values:
		this.addCtl(\hip, \x, { \ctl.kr(0) ! 161});
		nullSynth = this.getCtl(\hip, \x);
		{ nullSynth.free; }.defer(0.5);
	}

	freeCtls { // stop all synths.  (Leave control busses unchanged.)
		ctlSynths leafDo: { | coords | ctlSynths.at(*coords).free; };
	}
	addConst { | joint, dim, val = 0 |
		this.addCtl(joint, dim, { joint.kr(val) });
	}

	getSound { | joint, dim | ^soundSynths.at(joint, dim); }
	putSound { | joint, dim, synth | soundSynths.put(joint, dim, synth); }
	addSound { | joint, dim, func |
		this.stopSound(joint, dim);
		current = this;
		this.putSound(
			joint, dim, func.play(args: [inchan: this.getCtl(joint, dim)])
		);
	}
	stopSound { | joint, dim |
		this.getSound(joint, dim).free;
	}
}