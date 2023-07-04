/*  4 Jul 2023 08:40
For use to write code for playfuncs for SynthFileEvent.
See examples in ~/projects-sc/AudioSampleEvents/default/playfuncs/
and comments in superclass UGenFunc.
*/

PlayBuf_ : UGenFunc {
	*ar {
		var buf;
		buf = ~buf.buf;
		^PlayBuf.ar(
			buf.numChannels,
			buf,
			// 1,
			\rate.br(~rate ? 1),
			// 1,
			\trigger.br(1),
			// 0,
			\startpos.br(~startpos ? 0),
			// 0,
			\loop.br(~loop ? 0),
			Done.freeSelf
		)
	}
}