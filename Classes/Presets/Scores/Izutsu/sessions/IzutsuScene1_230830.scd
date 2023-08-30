//:Scene 1: Start with sines and transition gradually to noise,
//using cluster of freq modulated sines
//:Starting Point: Sines.


//:==================== ADDING freq * mods
//:V1Target sound - diverging gliss cluster LFNoise2 100+- 20+- sec
{
	5 +>.test \fadeTime;
	{ SinOsc.ar(\freq.br(400) * \freq.brdup(200, 1)).amix.amplify(0.3).pan } +> \test;
	5.wait;
	(1 - ((0..199).normalize / 8))
	do: { | v, i |
		{
			XLine.kr(1, 1 - (i + -0.1.rrand(0.1) / 600).postln, 30)
		}  @>.test (\freq +++ i);
	}
}.fork;
//:V2 perc mark
{
	5 +>.test \fadeTime;
	{ SinOsc.ar(\freq.br(400) * \freq.brdup(200, 1)).amix.amplify(0.3).pan } +> \test;
	5.wait;
	{ PinkNoise.ar.perc(0.001, 0.25) } +> \perc;
	(1 - ((0..199).normalize / 8))
	do: { | v, i |
		{
			XLine.kr(1, 1 - (i + -0.1.rrand(0.1) / 600), 60)
		}  @>.test (\freq +++ i);
	}
}.fork;
//:V3 larger spread
{
	5 +>.test \fadeTime;
	{ SinOsc.ar(\freq.br(400) * \freq.brdup(200, 1)).amix.amplify(0.3).pan } +> \test;
	5.wait;
	{ PinkNoise.ar.perc(0.001, 0.25) } +> \perc;
	(1 - ((0..199).normalize / 4))
	do: { | v, i |
		{
			XLine.kr(1, 1 - (i + -0.1.rrand(0.1) / 600), 60)
		}  @>.test (\freq +++ i);
	}
}.fork;
//:V4 + main freq glissando
{
	1 +>.test \fadeTime;
	{ SinOsc.ar(\freq.br(800) * \freq.brdup(200, 1)).amix.amplify(0.3).pan } +> \test;
	15.wait;
	{ PinkNoise.ar.perc(0.001, 0.25) } +> \perc;
	{ XLine.kr(800, 200, 180) } @>.test \freq;
	(1 - ((0..199).normalize / 4))
	do: { | v, i |
		{
			XLine.kr(1, 1 - (i + -0.1.rrand(0.1) / 600), 180);
		}  @>.test (\freq +++ i);
	}
}.fork;
//:V1 Target sound - diverging gliss cluster LFNoise2 100+- 20+- sec
25 +>.test \fadeTime;
{ SinOsc.ar(\freq.br(400) * \freq.brdup(200, 1)).amix.amplify(0.3).pan } +> \test;
(1 - ((0..199).normalize / 8))
do: { | v, i |
	{
		XLine.kr(1, 1 - (i + -0.1.rrand(0.1) / 600).postln, 30)
	}  @>.test (\freq +++ i);
}
//:FIRST GOOD EXAMPLE
200 do: { | i |
	{
		XLine.kr(1, 1 - (i + -0.1.rrand(0.1) / 600).postln, 10)
	}  @>.test (\freq +++ i);
}
//:
200 do: { | i |
	{
		LFNoise2.kr(10).range(XLine.kr(1, ((1 - i + -0.1.rrand(0.1) / 600)), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
//:
200 do: { | i |
	{
		LFNoise2.kr(10).range(XLine.kr((1 - i + -0.1.rrand(0.1), 1), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
//:V2Above cluster with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(200, 800)).amix.amplify.pan } +> \test;
200 do: { | i |
	{
		LFNoise2.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(300), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
//:V3 Above cluster 50 sines with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(50, 800)).amix.amplify.pan } +> \test;
50 do: { | i |
	{
		LFNoise2.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(300), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
//:V4 Above cluster 50 sines with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(50, 800)).amix.amplify.pan } +> \test;
50 do: { | i |
	{
		LFNoise0.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(300), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
//:V5 Above cluster 50 sines with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(50, 800)).amix.amplify.pan } +> \test;
50 do: { | i |
	{
		LFNoise0.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(500), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}

//:=================== ORIGINAL TRIES
//:============= Variations sine -> noise
//:V1 Target sound - diverging gliss cluster LFNoise2 100+- 20+- sec
25 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(200, 800)).amix.amplify.pan } +> \test;
200 do: { | i |
	{
		LFNoise2.kr(10).range(800, XLine.kr(800, 800 - i + -0.1.rrand(0.1), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
//:V2Above cluster with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(200, 800)).amix.amplify.pan } +> \test;
200 do: { | i |
	{
		LFNoise2.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(300), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
//:V3 Above cluster 50 sines with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(50, 800)).amix.amplify.pan } +> \test;
50 do: { | i |
	{
		LFNoise2.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(300), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
//:V4 Above cluster 50 sines with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(50, 800)).amix.amplify.pan } +> \test;
50 do: { | i |
	{
		LFNoise0.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(300), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
//:V5 Above cluster 50 sines with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(50, 800)).amix.amplify.pan } +> \test;
50 do: { | i |
	{
		LFNoise0.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(500), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
