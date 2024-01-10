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
		buf = \buf.brbuf;
		^BufRd.ar(
			1, // BufChannels.kr(buf),
			buf,
			Phasor.ar(
				\trig.br(0), // triggered by external functions. Preset value not applicable
				BufRateScale.kr(buf) * \rate.br(~rate ? 1),
				\startframe.br(~startframe ? 0),
				\endframe.br(~endframe ?? { BufFrames.kr(buf) })
				// \endframe.br(~endframe ?? { 44100 })
			)
		) * \vol.br(~vol ? 1)
	}
}
