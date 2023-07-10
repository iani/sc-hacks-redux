/*  4 Jul 2023 08:40
For use to write code for playfuncs for SynthFileEvent.
See examples in ~/projects-sc/AudioSampleEvents/default/playfuncs/
and comments in superclass UGenFunc.
*/

LoopBuf_ : UGenFunc {
	*ar {
		var buf, env, trig;
		buf = (~buf ?? { Buffer.first }).buf;
		env = Env([0, 1, 1, 0], [0.02, 0.98, 0.02]);
		^PlayBuf.ar(
			numChannels: buf.numChannels,
			bufnum: buf,
			// 1,
			rate: \rate.br(~rate ? 1),
			// 1,
			// trigger: Impulse.kr(\loopdur.br(~loopdur ? 1).reciprocal),
			trigger: Impulse.kr(\loopdur.br(~loopdur ?? { 1 })),
			// Impulse.kr(\loopdur.br(~loopdur ? 1).reciprocal),
			// 0,
			startPos: \startpos.br(~startpos ? { 0 }) * buf.sampleRate,
			// 0,
			loop: \loop.br(~loop ? 1),
			doneAction: Done.freeSelf
		)
		* \vol.br(~vol ? 1);
	}
}