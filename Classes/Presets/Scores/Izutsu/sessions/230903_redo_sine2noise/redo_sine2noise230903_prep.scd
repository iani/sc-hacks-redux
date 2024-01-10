//redoing to debug loss of control, refactor code, and tweak details
//:starting point
{
	var dur1 = 3, dur = 30;
	// { PinkNoise.ar.perc } +> \perc;
	{ SinOsc.ar(\freq.br(800) * \freq.brdup(50, 1)).amix.amplify(0.3).pan } +> \asayo;
	dur1.wait;
	// { PinkNoise.ar.perc } +> \perc;
	{ XLine.kr(800, 200, dur) } @>.asayo \freq; // main freq slides DOWN
	{	// cluster opens UPWARD
		(1..50).normalize + 1 + Array.rand(50, -0.01, 0.01) do: { | v, i |
			XLine.kr(1, v, 10).bout(\freq +++ i)
		};
	} +>.asayo \freqmod;
	dur.wait;
	// { PinkNoise.ar.perc } +> \perc;
}.fork;
//:========== rewriting as snippets =====
//reset freq controls
nil +>.test \freqmod; // stop xline freq mul output to busses
nil +>.test \freq; // stop glissando output
{ // reset freq multiplers
	{ | i | (\freq +++ i).bus.set(1) } ! 50;
	Silent.ar;
} +>.test \freqmod;
// start sine
{ SinOsc.ar(\freq.br(200) * \freq.brdup(50, 1)).amix.amplify(0.3).pan } +> \test;
//:glissando xline
{ XLine.kr(200, 800, 1) } @>.test \freq; // main freq slides UP
//:refactor open up cluster
{	// cluster opens DOWNWARD
	(1..50).normalize(1, 0.5) + Array.rand(50, -0.01, 0.01) do: { | v, i |
		XLine.kr(1, v, 5).bout(\freq +++ i)
	};
} +>.test \freqmod;
//:add noise variation instead of constant variation
{	// cluster opens DOWNWARD
	(1..50).normalize(1, 0.5) do: { | v, i |
		(XLine.kr(1, v, 50) + LFNoise2.kr(1).range(0, 0.05))
		.bout(\freq +++ i)
	};
} +>.test \freqmod;
{
	50.wait;
	"CLUSTER REACHED".postln;
	{ SinOsc.ar(400, 0, 0.1).perc.pan }.play;
}.fork;
//:
{ SinOsc.ar(400, 0.0, 0.01).perc }.play;