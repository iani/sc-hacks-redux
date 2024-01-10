//:
{
	Out.ar(0,
		WhiteNoise.ar(0.1) * Decay.kr(Impulse.kr(1))
	);
} +> \test;
//:
{
	var src, out;
	src = WhiteNoise.ar(0.1) * Decay.kr(Impulse.kr(1));
	out = PanAz.ar(8, src, LFSaw.kr(10.reciprocal));
	out;
} +> \test;
//: