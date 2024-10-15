//:Scene 1: Start with sines and transition gradually to noise,
//using cluster of freq modulated sines
//:Starting Point: Sines.

//:==================== ADDING freq * mods
//:test multiplier generation!!!
(1 - ((0..199).normalize / 8)) do: _.postln;
//:V1c EXPERIMENTAL- inline freqn xlines in one playfunc
//:applying to the sine->noise design UPWARD
{
	var dur1 = 10, dur = 30;
	{ PinkNoise.ar.perc } +> \perc;
	{ SinOsc.ar(\freq.br(800) * \freq.brdup(50, 1)).amix.amplify(0.3).pan } +> \test;
	dur1.wait;
	{ PinkNoise.ar.perc } +> \perc;
	{ XLine.kr(800, 200, dur) } @>.test \freq;
	{
		(1..50).normalize + 1 + Array.rand(50, -0.01, 0.01) do: { | v, i |
			XLine.kr(1, v, 10).bout(\freq +++ i)
		};
	} +>.test \freqmod;
	dur.wait;
	{ PinkNoise.ar.perc } +> \perc;
}.fork;
//:applying to the sine->noise design DOWNWARD
{
{ PinkNoise.ar.perc } +> \perc;
{ SinOsc.ar(\freq.br(400) * \freq.brdup(50, 1)).amix.amplify(0.3).pan } +> \test;
{
	(1..50).normalize + 1 + Array.rand(50, -0.01, 0.01) do: { | v, i |
		XLine.kr(1, v.reciprocal, 10).bout(\freq +++ i)
	};
} +>.test \freqmod;
10.wait;
{ PinkNoise.ar.perc } +> \perc;
}.fork;
//:V1b GOOD! fix v generation, greater range
"Starting".postln;
{
	{ PinkNoise.ar.perc(0.001, 0.25) } +> \perc;
	5 +>.test \fadeTime;
	{ SinOsc.ar(\freq.br(400) * \freq.brdup(50, 1)).amix.amplify(0.3).pan } +> \test;
	5.wait;
	{ PinkNoise.ar.perc(0.001, 0.25) } +> \perc;
	(1 - ((0..49).normalize / 2)) + Array.rand(50, -0.01, 0.01)
	do: { | v, i |
		{
			XLine.kr(1, v, 30)
		}  @>.test (\freq +++ i);
	};
	30.wait;
	{ PinkNoise.ar.perc(0.001, 0.25) } +> \perc;
}.fork;
//:V1 TESTED Target sound CORRECTION! USE V
"Starting".postln;
{
	5 +>.test \fadeTime;
	{ SinOsc.ar(\freq.br(400) * \freq.brdup(200, 1)).amix.amplify(0.3).pan } +> \test;
	5.wait;
	{ PinkNoise.ar.perc(0.001, 0.25) } +> \perc;
	(1 - ((0..199).normalize / 8))
	do: { | v, i |
		{
			XLine.kr(1, -0.001 rrand: 0.001 + v, 30)
		}  @>.test (\freq +++ i);
	};
	30.wait;
	{ PinkNoise.ar.perc(0.001, 0.25) } +> \perc;
}.fork;
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
//:V4 + main freq glissando 800 -> 200
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
//:V5 + main freq glissando 200 -> 800
{
	1 +>.test \fadeTime;
	{ SinOsc.ar(\freq.br(200) * \freq.brdup(200, 1)).amix.amplify(0.3).pan } +> \test;
	15.wait;
	{ PinkNoise.ar.perc(0.001, 0.25) } +> \perc;
	{ XLine.kr(200, 800, 180) } @>.test \freq;
	(1 - ((0..199).normalize / 4))
	do: { | v, i |
		{
			XLine.kr(1, 1 - (i + -0.1.rrand(0.1) / 600), 180);
		}  @>.test (\freq +++ i);
	}
}.fork;
//:V6 crossing glissandi + spread
{
	1 +>.test \fadeTime;
	{ SinOsc.ar(\freq.br(200) * \freq.brdup(200, 1)).amix.amplify(0.3).pan } +> \up;
	15.wait;
	{ PinkNoise.ar.perc(0.001, 0.25) } +> \perc;
	{ XLine.kr(200, 800, 180) } @>.up \freq;
	(1 - ((0..199).normalize / 4))
	do: { | v, i |
		{
			XLine.kr(1, 1 - (i + -0.1.rrand(0.1) / 600), 180);
		}  @>.up (\freq +++ i);
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
