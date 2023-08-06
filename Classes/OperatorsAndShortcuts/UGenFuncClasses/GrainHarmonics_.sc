/*  4 Jul 2023 08:40
For use to write code for playfuncs for SynthFileEvent.
See examples in ~/projects-sc/AudioSampleEvents/default/playfuncs/
and comments in superclass UGenFunc.

Calculation of duration offset and duration multiplier based on ~startframe and ~endframe
Objective: input is pos, ranging from 0 to 1.
This must be mapped to duration points in seconds corresponding to
~startframe and ~endframe:
pos = 0 -> ~startframe / buf.sampleRate
pos = 1 -> ~endframe / buf.sampleRate
*/

GrainHarmonics_ : UGenFunc {
	*ar {
		var trate, dur, b;
		b = (~buf ? \default).buf ?? { \default.buf };
		// trate = MouseY.kr(2,120,1);
		trate = \trate.br(~trate ? 0.5).linexp(0, 1, 2, 120);

		dur = 1.2 / trate;
		^TGrains.ar(b.numChannels, Impulse.ar(trate), b,
			(1.2 ** WhiteNoise.kr(3).round(1)),
			Pos(b), dur, WhiteNoise.kr(0.6), 0.1)
		* \vol.br(1);
	}
}