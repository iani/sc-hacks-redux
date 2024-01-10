/*  3 Dec 2022 10:18
Returns the proper instrument for playing back a mono or stereo bus.


Could not implement this.
Aborted.
*/

// Returns the proper instrument for playing back a mono or stereo bus.
Ppb { // = Pplaybuf
	*initClass { ServerBoot add: { this.loadSynthDefs } }
	*loadSynthDefs {
		SynthDef(\playbuf1, { | out = 0, bufnum = 0, rate = 1, trigger = 0, startPos = 0, loop = 0 |
			var src;
			src = PlayBuf.ar(1, bufnum, rate, trigger, startPos, loop, 2);
			OffsetOut.ar(out, src * Fader());
		}).add;
		SynthDef(\playbuf2, { | out = 0, bufnum = 0, rate = 1, trigger = 0, startPos = 0, loop = 0 |
			var src;
			src = PlayBuf.ar(2, bufnum, rate, trigger, startPos, loop, 2);
			OffsetOut.ar(out, src * Fader());
		}).add
	}

	asStream {}
	next { | x | // fails if ~buf is not a buffer or buffer name.
		var nc;
		nc = ~bufnum.next.numChannels;
		postln("bufnum:" + ~bufnum + "numchans:" + nc);
		postln("defname:" + [nil, \playbuf1, \playbuf2][nc]);
		// ~test = "this is a test";
		// [nil, \playbuf1, \playbuf2][~bufnum.numChannels]
		^[nil, \playbuf1, \playbuf2][nc];
	}
}





/*
a = Pcollect({ | a | a.bufnum}, \g);
a = Pcollect({ | a | a.bufnum}, [\g, \crickets].pseq);

b = a.asStream;
b.next;

a = \g.bufnums.asStream;
a.next;
*/