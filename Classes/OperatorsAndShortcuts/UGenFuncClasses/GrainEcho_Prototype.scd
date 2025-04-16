/* 20 Jul 2023 00:08

*/
//
{
    var trate, dur, clk, pos, pan;
	var b;
	b = \default.buf;
    trate = 12;
    dur = MouseY.kr(0.2,24,1) / trate;
    clk = Impulse.kr(trate);
    pos = MouseX.kr(0,BufDur.kr(b)) + TRand.kr(0, 0.01, clk);
    pan = WhiteNoise.kr(0.6);
    TGrains.ar(2, clk, b, 1, pos, dur, pan, 0.1);
}.play;
