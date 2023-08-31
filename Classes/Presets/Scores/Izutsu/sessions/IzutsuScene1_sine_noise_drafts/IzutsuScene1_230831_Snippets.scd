//:Scene 1: Start with sines and transition gradually to noise,
//Sliding sides gradually opening to noise

//:==================== ADDING freq * mods
//:Asayo: 800->200 applying to the sine->noise design
{
	var dur1 = 3, dur = 30;
	{ PinkNoise.ar.perc } +> \perc;
	{ SinOsc.ar(\freq.br(800) * \freq.brdup(50, 1)).amix.amplify(0.3).pan } +> \asayo;
	dur1.wait;
	{ PinkNoise.ar.perc } +> \perc;
	{ XLine.kr(800, 200, dur) } @>.asayo \freq; // main freq slides DOWN
	{	// cluster opens UPWARD
		(1..50).normalize + 1 + Array.rand(50, -0.01, 0.01) do: { | v, i |
			XLine.kr(1, v, 10).bout(\freq +++ i)
		};
	} +>.asayo \freqmod;
	dur.wait;
	{ PinkNoise.ar.perc } +> \perc;
}.fork;
//:Jun: 200->800 applying to the sine->noise design
{
	var dur1 = 3, dur = 30;
	{ PinkNoise.ar.perc } +> \perc;
	{ SinOsc.ar(\freq.br(200) * \freq.brdup(50, 1)).amix.amplify(0.3).pan } +> \jun;
	dur1.wait;
	{ PinkNoise.ar.perc } +> \perc;
	{ XLine.kr(200, 800, dur) } @>.jun \freq; // main freq slides UP
	{ 	// cluster opens DOWNWARD
		(1..50).normalize + 1 + Array.rand(50, -0.01, 0.01) do: { | v, i |
			XLine.kr(1, v.reciprocal, 10).bout(\freq +++ i)
		};
	} +>.jun \freqmod;
	dur.wait;
	{ PinkNoise.ar.perc } +> \perc;
}.fork;
//:JUN+ASAYO
{
	var dur1 = 7, dur = 30;
	5 +>.asayo \fadeTime;
	5 +>.jun \fadeTime;
	{ PinkNoise.ar.perc } +> \perc;
	{ SinOsc.ar(\freq.br(800) * \freq.brdup(50, 1)).amix.amplify(0.15).pan } +> \asayo;
	{ SinOsc.ar(\freq.br(200) * \freq.brdup(50, 1)).amix.amplify(0.15).pan } +> \jun;
	dur1.wait;
	{ PinkNoise.ar.perc } +> \perc;
	{ XLine.kr(800, 200, dur) } @>.asayo \freq; // main freq slides DOWN
	{	// cluster opens UPWARD
		(1..50).normalize + 1 + Array.rand(50, -0.01, 0.01) do: { | v, i |
			XLine.kr(1, v, 10).bout(\freq +++ i)
		};
	} +>.asayo \freqmod;
	{ XLine.kr(200, 800, dur) } @>.jun \freq; // main freq slides UP
	{ 	// cluster opens DOWNWARD
		(1..50).normalize + 1 + Array.rand(50, -0.01, 0.01) do: { | v, i |
			XLine.kr(1, v.reciprocal, 10).bout(\freq +++ i)
		};
	} +>.jun \freqmod;
	dur.wait;
	{ PinkNoise.ar.perc } +> \perc;
}.fork;

//:JUN+ASAYO+LFNoise2 mod to eliminate cycles
{
	var dur1 = 7, dur = 30;
	5 +>.asayo \fadeTime;
	5 +>.jun \fadeTime;
	{ PinkNoise.ar.perc } +> \perc;
	{ SinOsc.ar(\freq.br(800) * \freq.brdup(50, 1)).amix.amplify(0.15).pan } +> \asayo;
	{ SinOsc.ar(\freq.br(200) * \freq.brdup(50, 1)).amix.amplify(0.15).pan } +> \jun;
	dur1.wait;
	{ PinkNoise.ar.perc } +> \perc;
	{ XLine.kr(800, 200, dur) } @>.asayo \freq; // main freq slides DOWN
	{	// cluster opens UPWARD
		(1..50).normalize + 1 do: { | v, i |
			(LFNoise2.kr(1).range(-0.005, 0.005) + XLine.kr(1, v, 10))
			.bout(\freq +++ i)
		};
	} +>.asayo \freqmod;
	{ XLine.kr(200, 800, dur) } @>.jun \freq; // main freq slides UP
	{ 	// cluster opens DOWNWARD
		(1..50).normalize + 1 do: { | v, i |
			(LFNoise2.kr(1).range(-0.005, 0.005) + XLine.kr(1, v.reciprocal, 10))
			.bout(\freq +++ i)
		};
	} +>.jun \freqmod;
	dur.wait;
	{ PinkNoise.ar.perc } +> \perc;
}.fork;
