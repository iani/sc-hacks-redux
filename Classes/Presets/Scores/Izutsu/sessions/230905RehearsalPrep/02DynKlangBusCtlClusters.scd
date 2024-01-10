//Tue  5 Sep 2023 DynClang SineClusters with bus control
//:start the sound with named busses
{
	var fmod;
	fmod = \freq.br(200) * \freq.brdup(50, 1);
	DynKlang.ar(
		`[
			fmod,
			0.8 / 50,
			pi
		]
	).amplify.pan;
} +> \test;
0.1 @>.test \amp; // initialize amp
//:add glissandi
{ XLine.kr(200, 600, 5) } @>.test \freq;
//:spread cluster downwards
{
	{	// cluster opens DOWNWARD
		(1..50).normalize(1, 0.5) do: { | v, i |
			XLine.kr(1, v, 50).bout(\freq +++ i)
		};
	} +>.test \freqmod;
	50.wait;
	" finished opening cluster".postln;
}.fork;
//:
{
		50 do: { | i |
			LFNoise2.kr(1).range(*(0.05.bi - 1)).bout(\freq +++ i)
		};
} +>.test \freqmodnoise;
//:add gradually spreading noise to the cluster lines
{
		50 do: { | i |
			LFNoise2.kr(1).range(
				0, XLine.kr(0.001, 0.05, 50)).bout(\freq +++ i)
		};
} +>.test \freqmodnoise;
//:fadeout
{ XLine.kr(0.1, 0.0001, 2, doneAction: 2) - 0.0001 } @>.test \amp;
//:o