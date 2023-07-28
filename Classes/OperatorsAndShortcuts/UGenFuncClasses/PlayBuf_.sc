/*  4 Jul 2023 08:40
For use to write code for playfuncs for SynthFileEvent.
See examples in ~/projects-sc/AudioSampleEvents/default/playfuncs/
and comments in superclass UGenFunc.
*/

PlayBuf_ : UGenFunc {
	*ar {
		var buf, env, trig;
		buf = (~buf ?? { Buffer.first }).buf;
		env = Env([0, 1, 1, 0], [0.02, 0.98, 0.02]);
		"Debugging: Thisis PlayBuf_".postln;
		^PlayBuf.ar(
			buf.numChannels,
			buf,
			// 1,
			\rate.br(~rate ? 1),
			// 1,
			\trigger.br(1),
			// 0,
			\startpos.br(~startpos ? 0) * buf.sampleRate,
			// 0,
			\loop.br(~loop ? 1),
			Done.freeSelf
		)
		* DurEnv.kr(~dur ? buf.duration) * \vol.br(~vol ? 1);
	}
}