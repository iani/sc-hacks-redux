//:日 22  6 2025 00:52
// Control the currently playing animation:
// Provide access to its busses for writing, reading etc.
// When an AnimatinPlayer starts playing, it stores itself
// in the default instance of AnimationController.
// Multiple named AnimationControllers can be created
// To provide control to multiple concurrently playing AnimationPlayers.

AnimationController : NamedSingleton2 {
	classvar <sendPort = 22245, <>sendAddress;
	classvar <>verbose = false;
	var <>player; // the player currently playing
	var <databus, <modbus, <dataJoints, <modJoints;
	var <oscMaker; // construct osc messages from polled number array
	var <synth, <pollRoutine;
	var <data; // polled from synth
	var <ctlSynths;

	*initClass {
		StartUp add: { this.makeSendAddress }
	}

	*sendPort_ { | argPort = 22245 |
		sendPort = argPort;
		this.makeSendAddress;
	}

	*makeSendAddress {
		sendAddress = NetAddr("127.0.0.1", sendPort);
	}

	init {
		// init only once
		oscMaker !? { ^this };
		ctlSynths = MultiLevelIdentityDictionary();
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
		sendAddress.sendMsg(*msg);
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
	stopCtls {
		var nullSynth;
		ctlSynths leafDo: { | coords | ctlSynths.at(*coords).free; };
		// add synth to zero mod bus values:
		this.addCtl(\hip, \x, { \ctl.kr(0) ! 161});
		nullSynth = this.getCtl(\hip, \x);
		{ nullSynth.free; }.defer(0.5);
	}

	addConst { | joint, dim, val = 0 |
		this.addCtl(joint, dim, { joint.kr(val) });
	}
}