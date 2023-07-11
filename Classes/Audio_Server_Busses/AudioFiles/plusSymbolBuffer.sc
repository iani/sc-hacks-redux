/*  9 Aug 2021 17:34
	Shortcuts for getting a buffer from a symbol.
	When using method Hacks.loadBuffers, each
	audio file found in ~/sc-hacks-config/audiofiles/ is loaded
	into a buffer, and stored in the default Library under Buffer class key,
	under a name generated from the its filename.

	Thus, an file placed here:
	~/sc-hacks-config/audiofiles/awlk10.wav
	will be loaded into a buffer and the buffer can be accessed like this:

	Library.at(Buffer, \awlk10);

	The shortcuts here allow one to access the buffers just from the
	symbol which is the name under which they are stored under the sceme
	explained above.

*/

+ SimpleNumber {
	secs { ^this * 60 } // convert minutes to seconds
}

+ Symbol {
	malloc { | dur = 1, numChannels = 2 | // alloc in minutes duration
		^this.alloc(dur * 60, numChannels);
	}
	alloc { | dur = 1, numChannels = 2 | // alloc in seconds duration
		^this.allocFrames(
			Server.default.options.sampleRate * dur,
			numChannels
			);
	}

	allocFrames { | numFrames = 512, numChannels = 1 |
		var buffer;
		buffer = this.buffer;
		buffer !? {
			postln("alloc" + this + ": keeping alreading existing buffer" + buffer);
			^buffer;
		};
		buffer = Buffer.alloc(Server.default, numFrames, numChannels);
		Library.put(Buffer, this, buffer);
		^buffer
	}

	buffer { ^Library.at(Buffer, this); }
	// synonyms:
	b { ^this.buffer; }
	buf { ^this.buffer; }
	numChannels { ^this.buffer.numChannels }
	// play buffer (20230708 version)
	** { | event, mediatorname |
		// postln("debugging ** event:" + event);
		EditSoundPlayer(mediatorname ? \s).play(event[\buf] = this);
	}
	// Tue 11 Jul 2023 22:40 - even shorter version:
	@@ { | event, playfunc = \playbuf |
		event[\buf] = this;
		event[\playfunc] = playfunc;
		EditSoundPlayer(this).play(event)
	}
	// for embedding as ar source in playfuncs:
	pbar {
		var buf;
		buf = this.buf;
		^PlayBuf.ar(buf.numChannels, buf.bufnum, 1, 1, 0, 1, 2)
	}
	// play buffer (2022 version)
 	pb { | target, outbus = 0, player, envir, params |
		this.playbuf(params ? (), player, envir, target, outbus);
	}

	playbuf { | params, player, envir, target, outbus = 0 |
		var buf, theParams;
		player = player ? this;
		envir = envir ? player;

		theParams = (rate: 1, trigger: 1, startpos: 0, loop: 0);
		params ? () keysValuesDo: { | key, value |
			theParams.put(key, value)
		};
		buf = this.buf;
		{
			PlayBuf.ar(
				buf.numChannels,
				buf.bufnum,
				\rate.kr(theParams[\rate]),
				\trigger.kr(theParams[\trigger]),
				\startPos.kr(theParams[\startPos]),
				\loop.kr(theParams[\loop]),
				2
			).fader //　* Env.adsr().kr(\gate.kr(1))
		}.playInEnvir(player, envir, target, outbus);
		^buf;
	}
	//  6 Dec 2022 09:05 UNTESTED:
	rb { | target, inbus = 0, player, envir, params |
		this.recordbuf(params ? (), player, envir, target, inbus);
	}

	//  6 Dec 2022 09:05 UNTESTED:
	recordbuf { | params, player, envir, target, inbus = 0 |
		var buf, theParams;
		player = player ? this;
		envir = envir ? player;
	// *ar { arg inputArray, bufnum=0, offset=0.0, recLevel=1.0, preLevel=0.0,
	// 		run=1.0, loop=1.0, trigger=1.0, doneAction=0;
		theParams = (offset: 0, recLevel: 1, preLevel: 0,
			run: 1, loop: 0, trigger: 1, doneAction: 0);
		params ? () keysValuesDo: { | key, value |
			theParams.put(key, value)
		};
		buf = this.buf;
		{
			RecordBuf.ar(
				In.ar(inbus), // inputArray
				buf.bufnum,   // bufnum
				\rate.kr(theParams[\rate]), // offset
				\trigger.kr(theParams[\recLevel]), // recLevel
				\startPos.kr(theParams[\preLevel]), // preLevel
				\run.kr(theParams[\run]), // run
				\loop.kr(theParams[\loop]), // loop
				\trigger.kr(theParams[\trigger]), // trigger
				theParams[\doneAction] // doneAction
			).fader //　* Env.adsr().kr(\gate.kr(1))
		}.playInEnvir(player, envir, target);
		^buf;
	}
	bufnum { ^this.buf.bufnum }
	storebuf { | buffer |
		Library.put(Buffer, this, buffer);
	}
	// ci {... this.copyInput ... } //
	//	copyInput {} // copy input to another bus
}