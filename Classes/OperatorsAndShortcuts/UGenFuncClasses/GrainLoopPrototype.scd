/* 20 Jul 2023 00:07
Set aside for the moment
*/
{
    var trate, dur, clk, pos, pan, b;
	b = \default.buf;
    trate = LinExp.kr(LFTri.kr(MouseY.kr(0.1,2,1)),-1,1,8,120);
    dur = 12 / trate;
    clk = Impulse.ar(trate);
    pos = MouseX.kr(0,BufDur.kr(b));
    pan = WhiteNoise.kr(0.6);
    TGrains.ar(2, clk, b, 1, pos, dur, pan, 0.1);
}.play;
