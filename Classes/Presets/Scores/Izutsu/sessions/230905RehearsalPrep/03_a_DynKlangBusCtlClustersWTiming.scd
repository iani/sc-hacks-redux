//code
//Tue  5 Sep 2023 DynClang SineClusters with bus control
//Score with fixed onsets for each stage.
//:--[5] start the sound with named busses
//stop synths from previous tests.
nil +>.test \freq;
nil +>.test \amp;
nil +>.test \freqmod;
nil +>.test \freqmodnoise;
//initialize busses
200 @>.test \freq;
0.1 @>.test \amp;
{
	50.do({ | i | (\freq +++ i).bus.set(1)});
	Silent.ar.perc;
} +>.test \fmod;
//add sensor control
{ \x1.gt.lag(0.5) * 0.1 } @>.test \amp;
//Reset sensor value
0 >>! \x1;
//Start sine synth
{
	var fmod;
	fmod = \freq.br(200) * \freq.brdup(50, 1);
	DynKlang.ar(
		`[
			fmod,
			0.8 / 50,
			pi
		]
	).amplify.pan(0);
} +> \test;
//:--[40] start glissando
{ XLine.kr(200, 600, 80) } @>.test \freq; // gliss duration: 80!
//:--[40] spread cluster downwards
{	// cluster opens DOWNWARD
	(1..50).normalize(1, 0.5) do: { | v, i |
		XLine.kr(1, v, 40).bout(\freq +++ i) // spread duration: 40!
	};
} +>.test \freqmod;
//add gradually spreading noise to the cluster lines
{
		50 do: { | i |
			LFNoise2.kr(1).range(
				0, XLine.kr(0.001, 0.05, 40)).bout(\freq +++ i)
		};
} +>.test \freqmodnoise;
//:--[10] fadeout
{ XLine.kr(0.1, 0.0001, 10, doneAction: 2) - 0.0001 } @>.test \amp;
