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

GrainEcho_ : UGenFunc {
	*ar {
		var trate, dur, clk, pos, pan;
		var buf, numChans;
		\trate.bus.set((~trate ?? { 50 }));
		// ~trate.br(50);
		buf = ~buf.buf;
		numChans = buf.numChannels;
		// trate = MouseY.kr(8,120,1);
		trate = \trate.br(50);
		dur = 12 / trate;
		clk = Impulse.kr(trate);
		// pos = MouseX.kr(0,BufDur.kr(buf.bufnum)) + TRand.kr(0, 0.01, clk);
		// pos = \pos.br(0) * BufDur.kr(buf.bufnum) + TRand.kr(0, 0.01, clk);
		pos = \pos.br(0).linlin(0, 1,
				\startframe.br(~startframe) / buf.sampleRate,
				\endframe.br(~endframe) / buf.sampleRate)
				+ TRand.kr(0, 0.01, clk);
		pan = WhiteNoise.kr(0.6);
		^TGrains.ar(numChans, clk, buf, \rate.br(~rate ? 1), pos, dur, pan, 0.1) * \vol.br(1);
	}
}