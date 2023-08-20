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

!!!!!! TGrains only plays mono buffers. Do not use stereo buffrs with this function.
*/

GrainEcho_ : UGenFunc {
	*ar {
		var trate, dur, clk, pos, pan, buf; // , buf;
		buf = \buf kr: ((~buf ? \default).buf ?? { \default.buf }).bufnum;
		trate = \trate.br(~trate ? 0.5).lin(5, 20);
		// dur = MouseY.kr(0.2,24,1) / trate;
		dur = \dur.br(~dur ? 0.5).linexp(0.0, 1.0, 0.2, 24.0) / trate;
		clk = Impulse.kr(trate);
		// pos = MouseX.kr(0,BufDur.kr(b)) + TRand.kr(0, 0.01, clk);
		pos = Pos(buf) + TRand.kr(0, 0.01, clk);
		pan = WhiteNoise.kr(0.6);
		^TGrains.ar(2, clk, buf,
			\rate.br(~rate ? 1) * BufRateScale.kr(buf),
			pos, dur, pan, 0.1) * \vol.br(~vol ? 1);
	}
}