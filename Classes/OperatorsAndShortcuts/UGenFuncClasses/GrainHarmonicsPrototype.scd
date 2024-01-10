/* 20 Jul 2023 01:48

*/
{
    var trate, dur, b;
	b = (~buf ? \default).buf ?? { \default.buf };
    // trate = MouseY.kr(2,120,1);
	trate = \trate.br(~trate ? 0.5).linexp(0, 1, 2, 120);

    dur = 1.2 / trate;
    TGrains.ar(b.numChannels, Impulse.ar(trate), b,
		(1.2 ** WhiteNoise.kr(3).round(1)),
		Pos(b), dur, WhiteNoise.kr(0.6), 0.1);
}.play