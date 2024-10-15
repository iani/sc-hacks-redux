//:FIXING: Inline the frequency modulating bus writers
//in one single kr sythfunc.
//:source
0 +>.test \fadeTime;
{ SinOsc.ar(\freq.br(400)).amplify.pan } +> \test;
//:modulator;
{ LFNoise0.kr(10).range(500, 1000).bout(\freq)} +>.test \freqmod;
//:source with mod freqs
{ SinOsc.ar(\freq.br(400) * \freq.brdup(2)).amix.amplify.pan } +> \test;
//:modfreq kr
{ LFNoise1.kr(10).range(0.4, 1.4).bout(\freq0)} +>.test \freqmod;
//:modfreq kr with iter generated names
{ SinOsc.ar(\freq.br(400) * \freq.brdup(2)).amix.amplify.pan } +> \test;
{
	(1..2).normalize + 1.2 do: { | v, i |
		postln(\freq +++ i);
		XLine.kr(1, v, 3).bout(\freq +++ i)
	}
} +>.test \freqmod;
//:applying to the sine->noise design
{ SinOsc.ar(\freq.br(400) * \freq.brdup(50, 1)).amix.amplify(0.3).pan } +> \test;
{
	(1..50).normalize + 1.2 do: { | v, i |
		postln(\freq +++ i);
		XLine.kr(1, v, 3).bout(\freq +++ i)
	}
} +>.test \freqmod;
//:applying to the sine->noise design UPWARD
{
{ PinkNoise.ar.perc } +> \perc;
{ SinOsc.ar(\freq.br(400) * \freq.brdup(50, 1)).amix.amplify(0.3).pan } +> \test;
{
	(1..50).normalize + 1 + Array.rand(50, -0.01, 0.01) do: { | v, i |
		XLine.kr(1, v, 10).bout(\freq +++ i)
	};
} +>.test \freqmod;
10.wait;
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