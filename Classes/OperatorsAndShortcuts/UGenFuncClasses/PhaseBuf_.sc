/* 14 Jul 2023 09:02
Playbuf - looping with Phasor.

Control begin and end of loop independently of rate.
(Which is not possible with Playbuf).
*/

// ===============
// THIS IS A PROTOTYPE
// variables must be added to permit changing
// rate, startpoint, endpoint and other stuff.
// ===============
PhaseBuf_ : UGenFunc {
	*ar {
		var buf, env, trig;
		buf = (~buf ?? { Buffer.first }).buf;
		^BufRd.ar(
			buf.numChannels,
			buf.bufnum,
			Phasor.ar(
				\trig.br(0),
				BufRateScale.kr(buf.bufnum) * \rate.br(~rate ? 1),
				// startpos * buf.sampleRate,
				// dur  * buf.sampleRate min: BufFrames.kr(buf.bufnum);
				\startframe.br(~startframe ? 0),
				\endframe.br(~endframe ?? { buf.numFrames })
			)
		)
	}
}
