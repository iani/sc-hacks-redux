// 250616 Playback an Animation from a multichannel buffer
//

AnimationPlayer : NamedSingleton2 {
	classvar <>verbose = false;
	var animation;
	var <pollRoutine, <synth, <buffer, <databus, <modbus;
	var <sendPort = 22245, <>sendAddress;
	var oscMaker; // construct osc messages from polled number array
	var <>playerName = \default; // each named player has own busses

	init { | argAnimation |
		animation = argAnimation;
		buffer = animation.converter.buffer;
		this.makeSendAddress;
		this.makeOscMaker;
		CmdPeriod add: this;
	}

	doOnCmdPeriod { // mark as stopped. Do not restart.
		synth = nil;
		pollRoutine = nil;
	}


	sendPort_ { | argPort = 22245 |
		sendPort = argPort;
		this.makeSendAddress;
	}

	makeSendAddress {
		sendAddress = NetAddr("127.0.0.1", sendPort);
	}

	makeOscMaker {
		oscMaker = RokokoJoints();
		oscMaker.activeJoints = RokokoJoints.joints;
	}

	play { | from = 0, to, loop = 1 |
		this doWhenBufferLoaded: {
			this.makeBuses;
			this.makeSynth(from, to, loop);
		}
	}

	doWhenBufferLoaded { | func |
		fork {
			while { buffer.numChannels.isNil }
			{
				"Waiting for buffer to load".postln;
				0.1.wait;
			};
			func.value;
		}
	}

	makeBuses {
		databus = AnimationBus(name, \data, buffer.numChannels);
		modbus = AnimationBus(name, \mod, buffer.numChannels)
	}

	makeSynth { | from = 0, to, loop = 1 |
		synth = {
			var src, mod;
			src = PlayBuf.kr(buffer.numChannels,
				buffer.bufnum,
				BufRateScale.kr(buffer.bufnum) * 0.04,
				loop: loop,
				doneAction: Done.freeSelf);
			mod = In.kr(modbus.index, modbus.numChannels);
			Out.kr(databus.index, mod + src);
		}.play;
		synth onStart: {
			this.synthStarted; // start polling
		};
		synth onEnd: {
			this.synthEnded; // stop polling
		}
	}

	synthStarted {
		"synth started. will start poll routine".postln;
		pollRoutine ?? { this.startPolling }
	}

	startPolling {
		pollRoutine = {
			loop {
				if (verbose) { postln("Polling bus for" + this); };
				this.poll1;
				30.reciprocal.wait;
			}
		}.fork
	}

	poll1 {
		databus !? {
			databus.getn(databus.numChannels, { | nums |
				var msg;
				msg = oscMaker.makeOscMessage(nums, name);
				if (verbose) { msg.postln; };
				sendAddress.sendMsg(*msg);
			})
		}
	}

	synthEnded {
		pollRoutine.stop;
		pollRoutine = nil;
	}

	stop {
		synth.free; // also stops routine trhough onEnd!
	}

	// '/rokoko/', 2.8176906108856, 'Baubo'
	num2osc { | nums |
		// build osc message from numeric array
		// Currently limited to Rokoko format
		var header, joints;
		header = ['/rokoko/', Clock.seconds, name];
	}
}