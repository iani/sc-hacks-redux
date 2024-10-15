//:
{
    var trate, dur, clk, pos, pan;
    trate = 100;
    dur = 8 / trate;
    clk = Impulse.kr(trate);
    pos = Integrator.kr(BrownNoise.kr(0.001));
    pan = WhiteNoise.kr(0.6);
    TGrains.ar(2, clk, \default.buf, \rate.br(1), pos, dur, pan, 0.1);
}.scope(zoom: 4);
//:
0.4 @> \rate;

{ LFNoise1.kr(0.2).range(0.5, 4) } @> \rate;
{ LFNoise1.kr(0.2).range(0.5, 4).round(0.25) } @> \rate;
{ LFNoise1.kr(2).range(0.5, 4).round(0.25) } @> \rate;

{  Integrator.kr(BrownNoise.kr(0.001)); }.play;
//:

{
    var trate, dur, clk, pos, pan, buf;
	buf = \default.buf;
    trate = 100;
    dur = 8 / trate;
    clk = Impulse.kr(trate);
    // pos = \pos.br(0) * BufDur.kr(buf) ;
    pos = \pos.br(0).mapdur(buf);
    pan = WhiteNoise.kr(0.6);
    TGrains.ar(2, clk, buf, \rate.br(1), pos, dur, pan, 0.1);
}.scope(zoom: 4);
//:
{ LFSaw.kr(0.1, pi).range(0, 1) } @> \pos;
{ LFSaw.kr(0.25, pi).range(0, 1) } @> \pos;
{ LFSaw.kr(0.5, pi).range(0, 1) } @> \pos;
{ LFSaw.kr(1, pi).range(0, 1) } @> \pos;
{ LFSaw.kr(2, pi).range(0, 1) } @> \pos;
{ LFNoise1.kr(0.1, pi).range(0, 1) } @> \pos;
{ LFNoise0.kr(5, pi).range(0, 1) } @> \pos;
//:
40000 @> \startframe;
49000 @> \endframe;
{ LFSaw.kr(2, pi).range(0, 1) } @> \pos;