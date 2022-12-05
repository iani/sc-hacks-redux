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
	alloc { | dur = 1, numChannels = 2 |
		^this.allocFrames(
			Server.default.options.sampleRate * (dur * 60),
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
	// play buffer
	pb { | target, outbus = 0, player, envir |
		// var theEnvir;
		// envir = envir ? player ? this;
		// theEnvir = envir.envir;
		// out !? { theEnvir[\outbus] = out.ab };
		// target !? { theEnvir[\target] = target.asTarget };
		this.playbuf((), player, envir, target, outbus);
	}

	// record buffer
	// rb { | | }
	//
	// granulate buffer
	// playbuf { ^this.buffer.play }
	playbuf { | params, player, envir, target, outbus = 0 |
		var buf, theParams;
		envir = envir ? this;
		player = player ? this;

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
	bufnum { ^this.buf.bufnum }
	storebuf { | buffer |
		Library.put(Buffer, this, buffer);
	}
}