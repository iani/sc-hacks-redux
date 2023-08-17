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
		// "Debugging. THis is PhaseBuf_".postln;
		^BufRd.ar(
			buf.numChannels,
			buf.bufnum,
			Phasor.ar(
				\trig.br(0), // triggered by external functions. Preset value not applicable
				BufRateScale.kr(buf.bufnum) * \rate.br(~rate ? 1),
				\startframe.br(~startframe ? 0),
				\endframe.br(~endframe ?? { buf.numFrames })
			)
		) * \vol.br(~vol ? 1)
	}
}
