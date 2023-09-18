//code
//Tue  5 Sep 2023 DynClang SineClusters with bus control
//Score with fixed onsets for each stage.
//With sounds and players for Jun and Asayo.
//:--[5] countdown and synchronization prompt
{
	{ SinOsc.ar(800, 0, 0.1).perc(0.01, 0.1).pan } +> \ping;
	1.wait;
	{ SinOsc.ar(800, 0, 0.1).perc(0.01, 0.1).pan } +> \ping;
	1.wait;
	{ SinOsc.ar(800, 0, 0.1).perc(0.01, 0.1).pan } +> \ping;
}.fork;
//:--[40] start the sound with named busses
//======= stop synths from previous tests. =======
nil +>.jun \freq;
nil +>.jun \amp;
nil +>.jun \freqmod;
nil +>.jun \freqmodnoise;
nil +>.asayo \freq;
nil +>.asayo \amp;
nil +>.asayo \freqmod;
nil +>.asayo \freqmodnoise;
//======= initialize busses =======
200 @>.jun \freq;
800 @>.asayo \freq;
0.1 @>.jun \amp;
0.1 @>.asayo \amp;
1 @>.jun \vol;
1 @>.asayo \vol;
{
	50.do({ | i | (\freq +++ i).bus.set(1)});
	Silent.ar.perc;
} +>.jun \fmod;
{
	50.do({ | i | (\freq +++ i).bus.set(1)});
	Silent.ar.perc;
} +>.asayo \fmod;
//======= add sensor control =======
{ \x7.gt.lag(0.5) * 0.5 } @>.jun \amp;
{ \z8.gt(0.3).lag(0.5) * 0.5 } @>.asayo \amp;

//======= Set sensor values to off =======
0 >>! \x7;
0 >>! \z8;
//======= Start sine synth =======
{
	var fmod;
	fmod = \freq.br(200) * \freq.brdup(50, 1);
	DynKlang.ar(
		`[
			fmod,
			0.8 / 50,
			pi
		]
	).amplify.vol.pan(0);
} +> \jun;
{
	var fmod;
	fmod = \freq.br(800) * \freq.brdup(50, 1);
	DynKlang.ar(
		`[
			fmod,
			0.8 / 50,
			pi
		]
	).amplify.vol.pan(0);
} +> \asayo;
//:--[40] add softening cluster noise modulation
{ \z5.sr.lag(0.5).lin(0, 0.01) } @>.jun \noiseb;
{ \y8.sr.lag(0.5).lin(0, 0.01) } @>.asayo \noiseb;
1.1 @>.jun \vol;
1.1 @>.asayo \vol;

{
		50 do: { | i |
			LFNoise2.kr(1).range(
				1, 1 + \noiseb.br(0.0001)).bout(\freq +++ i)
		};
} +>.jun \freqmodnoise;
{
		50 do: { | i |
			LFNoise2.kr(1).range(
				1, 1 + \noiseb.br(0.0001)).bout(\freq +++ i)
		};
} +>.asayo \freqmodnoise;
//:--[150] start glissando
//180' gliss + 20'stable
{ XLine.kr(200, 800, 120) } @>.jun \freq; // gliss duration: 180!
{ XLine.kr(800, 200, 120) } @>.asayo \freq; // gliss duration: 180!
//:--[20] spread cluster (WITHOUT NOISE!) + cresc spread duration: 90'
// stop the previous noise modulation
nil +>.asayo \freqmodnoise;
nil +>.jun \freqmodnoise;

{	// jun cluster opens DOWNWARD
	(1..50).normalize(1, 0.5) do: { | v, i |
		XLine.kr(1, v, 90).bout(\freq +++ i) // spread duration: 90!
	};
} +>.jun \freqmod;
{	// asayo cluster opens UPWARD
	(1..50).normalize(1, 2) do: { | v, i |
		XLine.kr(1, v, 90).bout(\freq +++ i) // spread duration: 90!
	};
} +>.asayo \freqmod;
//:crescendo:
{ Line.kr(1, 2, 50) } @>.jun \vol;
{ Line.kr(1, 2, 50) } @>.asayo \vol;

//:--[70] add gradually spreading noise to the cluster lines. dur: 70'
{
		50 do: { | i |
			LFNoise2.kr(1).range(
				0, XLine.kr(0.001, 0.05, 70)).bout(\freq +++ i)
		};
} +>.jun \freqmodnoise;
{
		50 do: { | i |
			LFNoise2.kr(1).range(
				0, XLine.kr(0.001, 0.05, 70)).bout(\freq +++ i)
		};
} +>.asayo \freqmodnoise;
//:--[10] fadeout
{ XLine.kr(2, 0.0001, 10, doneAction: 2) - 0.0001 } @>.jun \vol;
{ XLine.kr(2, 0.0001, 10, doneAction: 2) - 0.0001 } @>.asayo \vol;
