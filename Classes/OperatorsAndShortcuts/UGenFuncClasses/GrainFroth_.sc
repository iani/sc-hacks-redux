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

GrainFroth_ : UGenFunc {
	*ar {
		var trate, dur, clk, pos, pan, b;
		b = (~buf ? \default).buf ?? { \default.buf };
		// trate = MouseY.kr(1,400,1);
		trate = \trate.br(~trate ? 0.5).linexp(0, 1, 1, 400);
		dur = 8 / trate;
		clk = Impulse.kr(trate);
		// pos = MouseX.kr(0,BufDur.kr(b));
		pos = Pos(b);
		pan = WhiteNoise.kr(0.8);
		^TGrains.ar(2, clk, b, 2 ** WhiteNoise.kr(2), pos, dur, pan, 0.1)
		* \vol.br(~vol ? 1);
	}
}