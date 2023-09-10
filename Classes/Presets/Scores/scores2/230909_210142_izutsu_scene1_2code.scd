// Exporting 13 snippets on 230910_115416
//Source: [ 230909_210142, 230909_210149, 230909_210205, 230909_210221, 230909_210236, 230909_210253, 230909_210309, 230909_210324, 230909_210340, 230909_210356, 230909_210412, 230909_210428, 230909_210444, 230909_210500, 230909_210517, 230909_210533, 230909_210549, 230909_210605, 230909_210621, 230909_210637, 230909_210653, 230909_210710, 230909_210726, 230909_210742, 230909_210758, 230909_210814, 230909_210826, 230909_210838, 230909_210850, 230909_210902, 230909_210914, 230909_210926, 230909_210938, 230909_210951, ...etc...
//:--[11445.920813167]
[ '/code', "//:--[5] countdown and synchronization prompt
{
	{ SinOsc.ar(800, 0, 0.1).perc(0.01, 0.1).pan } +> \\ping;
	1.wait;
	{ SinOsc.ar(800, 0, 0.1).perc(0.01, 0.1).pan } +> \\ping;
	1.wait;
	{ SinOsc.ar(800, 0, 0.1).perc(0.01, 0.1).pan } +> \\ping;
}.fork;" ]
//:--[11450.900182917]
[ '/code', "//:--[10] start the sound with named busses
//======= stop synths from previous tests. =======
nil +>.jun \\freq;
nil +>.jun \\amp;
nil +>.jun \\freqmod;
nil +>.jun \\freqmodnoise;
nil +>.asayo \\freq;
nil +>.asayo \\amp;
nil +>.asayo \\freqmod;
nil +>.asayo \\freqmodnoise;
//======= initialize busses =======
200 @>.jun \\freq;
800 @>.asayo \\freq;
0.1 @>.jun \\amp;
0.1 @>.asayo \\amp;
1 @>.jun \\vol;
1 @>.asayo \\vol;
{
	50.do({ | i | (\\freq +++ i).bus.set(1)});
	Silent.ar.perc;
} +>.jun \\fmod;
{
	50.do({ | i | (\\freq +++ i).bus.set(1)});
	Silent.ar.perc;
} +>.asayo \\fmod;
//======= add sensor control =======
{ \\x5.gt.lag(0.5) * 0.5 } @>.jun \\amp;
{ \\z8.gt(0.3).lag(0.5) * 0.5 } @>.asayo \\amp;

//======= Set sensor values to off =======
0 >>! \\x5;
0 >>! \\z8;
//======= Start sine synth =======
{
	var fmod;
	fmod = \\freq.br(200) * \\freq.brdup(50, 1);
	DynKlang.ar(
		`[
			fmod,
			0.8 / 50,
			pi
		]
	).amplify.vol.pan(0);
} +> \\jun;
{
	var fmod;
	fmod = \\freq.br(800) * \\freq.brdup(50, 1);
	DynKlang.ar(
		`[
			fmod,
			0.8 / 50,
			pi
		]
	).amplify.vol.pan(0);
} +> \\asayo;" ]
//:--[11460.900724333]
[ '/code', "//:--[40] add softening cluster noise modulation
{ \\z5.sr.lag(0.5).lin(0, 0.01) } @>.jun \\noiseb;
{ \\y8.sr.lag(0.5).lin(0, 0.01) } @>.asayo \\noiseb;
1.1 @>.jun \\vol;
1.1 @>.asayo \\vol;

{
		50 do: { | i |
			LFNoise2.kr(1).range(
				1, 1 + \\noiseb.br(0.0001)).bout(\\freq +++ i)
		};
} +>.jun \\freqmodnoise;
{
		50 do: { | i |
			LFNoise2.kr(1).range(
				1, 1 + \\noiseb.br(0.0001)).bout(\\freq +++ i)
		};
} +>.asayo \\freqmodnoise;" ]
//:--[11500.899978417]
[ '/code', "//:--[200] start glissando
//180' gliss + 20'stable
{ XLine.kr(200, 800, 180) } @>.jun \\freq; // gliss duration: 180!
{ XLine.kr(800, 200, 180) } @>.asayo \\freq; // gliss duration: 180!" ]
//:--[11700.90125375]
[ '/code', "//:--[20] spread cluster (WITHOUT NOISE!) + cresc spread duration: 90'
// stop the previous noise modulation
nil +>.asayo \\freqmodnoise;
nil +>.jun \\freqmodnoise;

{	// jun cluster opens DOWNWARD
	(1..50).normalize(1, 0.5) do: { | v, i |
		XLine.kr(1, v, 90).bout(\\freq +++ i) // spread duration: 90!
	};
} +>.jun \\freqmod;
{	// asayo cluster opens UPWARD
	(1..50).normalize(1, 2) do: { | v, i |
		XLine.kr(1, v, 90).bout(\\freq +++ i) // spread duration: 90!
	};
} +>.asayo \\freqmod;
//:--[11720.900792708]
[ '/code', "//:--[70] add gradually spreading noise to the cluster lines. dur: 70'
{
		50 do: { | i |
			LFNoise2.kr(1).range(
				0, XLine.kr(0.001, 0.05, 70)).bout(\\freq +++ i)
		};
} +>.jun \\freqmodnoise;
{
		50 do: { | i |
			LFNoise2.kr(1).range(
				0, XLine.kr(0.001, 0.05, 70)).bout(\\freq +++ i)
		};
} +>.asayo \\freqmodnoise;" ]
//:--[11790.900684125]
[ '/code', "//:--[10] fadeout
{ XLine.kr(2, 0.0001, 10, doneAction: 2) - 0.0001 } @>.jun \\vol;
{ XLine.kr(2, 0.0001, 10, doneAction: 2) - 0.0001 } @>.asayo \\vol;
" ]
//:--[11806.407198417]
[ '/code', "\\jun.envir.stopSynths" ]
//:--[11806.408035417]
[ '/code', "'jun'.envir play: ( 'vol': [ 0.26202059934786, \"\", 'off' ], 'endframe': 2451380, 'buf': 'bird_crickets', 'amp': [ 1.0, \"Xyz(5, 0.2, 0.2, 1.5) + Xyz(6, 0.2, 0.2, 1.5) + Xyz(7, 0.2, 0.2, 1.5) + Xyz(8, 0.2, 0.2, 1.5)\", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfroth', 'trate': [ 0.65827338129496, \"\\\\z5.sr\", 'on' ], 'pos': [ 0.74460431654676, \"\\\\x5.sr\", 'on' ] )" ]
//:--[11806.412355375]
[ '/code', "{Xyz(5, 0.2, 0.2, 1.5) + Xyz(6, 0.2, 0.2, 1.5) + Xyz(7, 0.2, 0.2, 1.5) + Xyz(8, 0.2, 0.2, 1.5)}@>.jun \\amp" ]
//:--[11806.419121375]
[ '/code', "{\\x5.sr}@>.jun \\pos" ]
//:--[11806.420384167]
[ '/code', "{\\z5.sr}@>.jun \\trate" ]
//:--[12206.847522417]
[ '/code', "\\jun.envir.stopSynths" ]

//the end

