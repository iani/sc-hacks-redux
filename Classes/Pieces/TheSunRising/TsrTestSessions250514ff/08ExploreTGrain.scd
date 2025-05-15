// 木 15  5 2025 20:06 TGrain on verse voice samples
{
    var trate, dur, rate, b;
	b = ~i1;
    trate = MouseY.kr(2,200,1);
    dur = 4 / trate;
    rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    TGrains.ar(2, Impulse.ar(trate), b, rate, MouseX.kr(0,BufDur.kr(b)), dur, Dseq([-1, 1], inf), 0.1, 2);
}.play;
//:
// 木 15  5 2025 20:06 TGrain on verse voice samples
a = { | trate = 4, duratio = 4, rate = 1, pos = 0.5 |
    var dur, b;
	b = ~i1;
    dur = duratio / trate;
    TGrains.ar(2, Impulse.ar(trate), b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
}.play;
//:
a.set(\trate, 10);
a.set(\duratio, 10);
a.set(\rate, 0.4);
a.set(\pos, 0.99);
//:
\b.bus;