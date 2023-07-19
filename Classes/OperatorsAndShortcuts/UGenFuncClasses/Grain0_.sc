/* 19 Jul 2023 21:09
Most basic grain func. Move inside the sample with 2 control variables:
position (0-1 relative to range defined by startframe-endframe).
rate : buf playback rate for grains.
*/

Grain0_ : UGenFunc {
	*ar {
		var trate, dur, clk, pos, pan, buf;
		buf = (~buf ? \default).buf ?? { \default.buf };
		trate = 40; // 100;
		dur = 8 / trate;
		clk = Impulse.kr(trate);
		// pos = \pos.br(0) * BufDur.kr(buf) ;
		pos = \pos.br(~pos ? 0).mapdur(buf);
		pan = WhiteNoise.kr(0.6);
		^TGrains.ar(2, clk, buf, \rate.br(~rate ? 1), pos, dur, pan, 0.1)
		* \vol.br(~vol ? 1);
	}
}
