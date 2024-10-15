/*  3 Dec 2022 19:04
Some useful SynthDefs.

(bufnum: \g.bufnums, instrument: \playbuf2) +> \test;

(bufnum: \crickets.bufnums, instrument: \playbuf1) +> \test;

(bufnum: \crickets.bufnums, instrument: \playbuf1) +> \test;
*/

SynthDefs {
	*initClass { ServerBoot add: { this.loadSynthDefs } }
	*loadSynthDefs {
		SynthDef(\playbuf1, { | out = 0, bufnum = 0, rate = 1, trigger = 0, startPos = 0, loop = 0 |
			var src;
			src = PlayBuf.ar(1, bufnum, rate, trigger, startPos, loop, 2);
			src = Pan2.ar(src, \pos.kr(0));
			OffsetOut.ar(out, src * Fader());
		}).add;
		SynthDef(\playbuf2, { | out = 0, bufnum = 0, rate = 1, trigger = 0, startPos = 0, loop = 0 |
			var src;
			src = PlayBuf.ar(2, bufnum, rate, trigger, startPos, loop, 2);
			OffsetOut.ar(out, src * Fader());
		}).add
	}
}