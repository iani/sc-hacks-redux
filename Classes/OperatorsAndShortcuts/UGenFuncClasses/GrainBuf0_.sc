/* Thu 14 Sep 2023 14:25
Most basic OVERLAPPING grain func.
All parameters are set through busses, including triggering.

GrainBuf.ar(numChannels: 1, trigger: 0, dur: 1,
	sndbuf, rate: 1, pos: 0, interp: 2, pan: 0,
	envbufnum: -1, maxGrains: 512, mul: 1, add: 0)

==== interp	====
the interpolation method used for pitchshifting grains:

1 = no interpolation
2 = linear
4 = cubic interpolation (more computationally intensive)

==== envbufnum ====
the buffer number containing a signal to use for the grain envelope.
	-1 uses a built-in Hann envelope.
*/

GrainBuf0_ : UGenFunc {
	*ar { | numChannels = 1 |
		var trig, dur, buf, rate, pos, pan;
		trig = \trig.br(0);
		// trig = \trig.tr(0);
		dur = \dur.br(~dur ? 1);
		buf = (~buf ? \default).buf ?? { \default.buf };
		rate = \rate.br(~rate ? 1);
		// NOTE: GrainBuf takes pos in 0-1 where 1 is end of buffer!
		// pos = \pos.br(~pos ? 0); // TRY THIS LATER!
		// pos = \pos.kr(0);
		pos = \pos.br(~pos ? 0);
		pan = \gpan.br(~gpan ? 0); // pan can be added by other parts!
		^GrainBuf.ar(
			1,
			trig,
			dur,
			buf,
			rate,
			pos,
			4, // see notes on interp above
			pan,
			-1, // Hann envelope - see notes on envbufnum above
			512 // maxGrains
		) * \vol.br(~vol ? 1).amplify(0.2);
	}
}
