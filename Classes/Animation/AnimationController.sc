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
		this.makeDataJoints;
		this.makeModJoints;
	}


	makeDataBus {
		databus = Bus.control(Server.default, oscMaker.numChannels);
	}

	makeModBus {
		modbus = Bus.control(Server.default, oscMaker.numChannels);
	}

	makeDataJoints {

	}

	makeModJoints {

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

	set { | bus, dim |

	}
}