// 250616 Playback an Animation from a multichannel buffer
// The output bus for playback is obtained from controller.
// TODO: To play several figures concurrently, create
// a controller with a custom name for each AnimationPlayer corresponding
// to a different figure.

AnimationPlayer : NamedSingleton2 {
	var <animation, <buffer, <synth, <controller;

	init { | argAnimation |
		animation = argAnimation;
		buffer = animation.converter.buffer;
		controller = AnimationController(\default); // different names?
		CmdPeriod add: this;
	}

	doOnCmdPeriod { // mark as stopped. Do not restart.
		synth = nil;
		this.changed(\stopped);
	}

	play { | from = 0, to, loop = 1 |
		this doWhenBufferLoaded: {
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

	makeSynth { | from = 0, to, loop = 1 |
		postln("Playing buffer:" + buffer);
		postln("Buffer filename:" + buffer.path.fileName);
		synth = { | rate = 1 |
			var src;
			src = PlayBuf.kr(buffer.numChannels,
				buffer.bufnum,
				BufRateScale.kr(buffer.bufnum) * 0.04 * rate,
				loop: loop,
				doneAction: Done.freeSelf);
			Out.kr(controller.databus.index, src);
		}.play;
		synth onStart: {
			this.synthStarted; // start polling
		};
		synth onEnd: {
			this.synthEnded; // stop polling
		}
	}

	move { synth.set(\rate, 1) }
	freeze { synth.set(\rate, 0) }

	makeController {
		controller = AnimationController(\default);
		controller.player = this;
	}

	synthStarted {
		postln("Synth started:" + this);
	}


	synthEnded {
		postln("Synth ended" + this);
	}

	stop {
		if (synth.isPlaying) {
			synth.free;
			synth = nil;
		};
	}
}