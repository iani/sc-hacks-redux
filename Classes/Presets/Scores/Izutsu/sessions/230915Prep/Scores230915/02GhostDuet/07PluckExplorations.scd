/* 17 Sep 2023 08:57

*/
//:soft pluck tremolo
{
	Pluck.ar(
		WhiteNoise.ar(0.1),
		Impulse.kr(20), 1, LFNoise0.kr(5).range(300, 600).reciprocal, 10,
        coef: LFNoise2.kr(1).range(0.6, 0.9999))
} +> \pluck;
//: