// 250616 Playback an Animation from a multichannel buffer
//

AnimationPlayer : NamedSingleton2 {
	classvar <>verbose = false;
	var animation;
	var <pollRoutine, <synth, <buffer, <bus;
	var <sendPort = 22245, <>sendAddress;
	var oscMaker; // construct osc messages from polled number array

	init { | argAnimation |
		animation = argAnimation;
		buffer = animation.converter.buffer;
		this.makeBus;
		this.makeSendAddress;
		this.makeOscMaker;
		CmdPeriod add: this;
	}

	doOnCmdPeriod { // mark as stopped. Do not restart.
		synth = nil;
		pollRoutine = nil;
	}

	makeBus {
		fork {
			while { buffer.numChannels.isNil }
			{
				"Waiting for buffer to load".postln;
				0.1.wait;
			};
			// buffer.numChannels.postln;
			bus = Bus.control(Server.default, buffer.numChannels);
			bus.postln;
		}
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
		synth.free;
		synth = this.makeSynth(from, to, loop);
	}

	stop {
		synth.free; // also stops routine trhough onEnd!
	}

	makeSynth { | from = 0, to, loop = 1 |
		synth = {
			PlayBuf.kr(buffer.numChannels,
				buffer.bufnum,
				BufRateScale.kr(buffer.bufnum) * 0.04,
				loop: loop,
				doneAction: Done.freeSelf)
		}.play;
		synth onStart: {
			this.synthStarted; // start polling
		};
		synth onEnd: {
			this.synthEnded; // stop polling
		}
	}

	synthStarted {
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
		bus !? {
			bus.getn(bus.numChannels, { | nums |
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

	// '/rokoko/', 2.8176906108856, 'Baubo'
	num2osc { | nums |
		// build osc message from numeric array
		// Currently limited to Rokoko format
		var header, joints;
		header = ['/rokoko/', Clock.seconds, name];
	}
}