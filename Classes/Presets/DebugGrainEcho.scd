/* 29 Jul 2023 20:21
This version from GrainEcho_ does not play.  Need to look into parameters.
Sat 29 Jul 2023 21:07: Fix found: Source file was stereo TGrains only accepts mono files.
*/
//:broken version:
{
		var trate, dur, clk, pos, pan;
		var b;
		b = (~buf ? \default).buf ?? { \default.buf };
		trate = 12;
		// dur = MouseY.kr(0.2,24,1) / trate;
		dur = \dur.br(~dur ? 0.5).linexp(0.0, 1.0, 0.2, 24.0) / trate;
		clk = Impulse.kr(trate);
		// pos = MouseX.kr(0,BufDur.kr(b)) + TRand.kr(0, 0.01, clk);
		pos = Pos(b) + TRand.kr(0, 0.01, clk);
		pan = WhiteNoise.kr(0.6);
		TGrains.ar(2, clk, b, \rate.br(~rate ? 1), pos, dur, pan, 0.1) * \vol.br(~vol ? 1);
}.play;
//:fixing?:
{
		var trate, dur, clk, pos, pan;
		var b;
		b = (~buf ? \default).buf ?? { \default.buf };
		trate = 12;
		dur = MouseY.kr(0.2,24,1) / trate;
		// dur = \dur.br(~dur ? 0.5).linexp(0.0, 1.0, 0.2, 24.0) / trate;
		clk = Impulse.kr(trate);
		pos = MouseX.kr(0, BufDur.kr(b)) + TRand.kr(0, 0.01, clk);
		// pos = Pos(b) + TRand.kr(0, 0.01, clk);
		pan = WhiteNoise.kr(0.6);
		TGrains.ar(2, clk, b, \rate.br(~rate ? 1), pos, dur, pan, 0.1) * \vol.br(~vol ? 1);
}.play;
//:recopying from original and trying again from scratch
{
    var trate, dur, clk, pos, pan, buf;
	b = \default.buf;
    trate = 12;
    dur = MouseY.kr(0.2,24,1) / trate;
    clk = Impulse.kr(trate);
    pos = MouseX.kr(0,BufDur.kr(b)) + TRand.kr(0, 0.01, clk);
    pan = WhiteNoise.kr(0.6);
    TGrains.ar(2, clk, b, 1, pos, dur, pan, 0.1);
}.play;
//:reworking 2
{
    var trate, dur, clk, pos, pan, buf;
	b = (~buf ? \default).buf.postln;
    trate = 12;
    dur = MouseY.kr(0.2,24,1) / trate;
    clk = Impulse.kr(trate);
    pos = MouseX.kr(0,BufDur.kr(b)) + TRand.kr(0, 0.01, clk);
    pan = WhiteNoise.kr(0.6);
    TGrains.ar(2, clk, b, b.numChannels, pos, dur, pan, 0.1);
}.play;
//:
