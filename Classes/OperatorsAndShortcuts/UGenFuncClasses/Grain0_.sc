/* 19 Jul 2023 21:09
Most basic grain func.
All parameters are set through busses, including triggering.
		// question: what is this?????????
	// we can try it also externally as control func writing to the pos bus.
	pos = Integrator.kr(BrownNoise.kr(0.001)).abs.mapdur(buf);
*/

Grain0_ : UGenFunc {
	*ar {
		var trig, buf, rate, pos, dur, pan;
		trig = \trig.br(0);
		buf = (~buf ? \default).buf ?? { \default.buf };
		rate = \rate.br(~rate ? 1);
		// NOTE: TGrains takes pos in seconds, not frames!
		// pos = \pos.br(~pos ? 0).mapdur(buf);
		pos = \pos.kr(0);
		dur = \dur.br(~dur ? 1);
		pan = \pan.br(~pan ? 0);
		^TGrains.ar(
			2, trig, buf, rate, pos, dur, pan
		) * \vol.br(~vol ? 1).amplify(1);
	}
}
