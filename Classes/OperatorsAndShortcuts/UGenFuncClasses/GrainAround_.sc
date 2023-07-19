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

GrainAround_ : UGenFunc {
	*ar {
		var trate, dur, clk, pos, pan, buf;
		buf = (~buf ? \default).buf ?? { \default.buf };
		trate = 40; // 100;
		dur = 8 / trate;
		clk = Impulse.kr(trate);
		pos = Integrator.kr(BrownNoise.kr(0.001)).abs.mapdur(buf);
		pan = WhiteNoise.kr(0.6);
		^TGrains.ar(2, clk, buf, \rate.br(~rate ? 1), pos, dur, pan, 0.1)
		* \vol.br(~vol ? 1);
	}
}