// Exporting 108 snippets on 230918_233207
//Source: [ 230918_201457, 230918_201501, 230918_201517, 230918_201533, 230918_201549, 230918_201605, 230918_201621, 230918_201637, 230918_201653, 230918_201709, 230918_201725, 230918_201741, 230918_201757, 230918_201813, 230918_201829, 230918_201846, 230918_201902, 230918_201919, 230918_201935, 230918_201951, 230918_202007, 230918_202023, 230918_202039, 230918_202055, 230918_202112, 230918_202128, 230918_202144, 230918_202200, 230918_202216, 230918_202231, 230918_202247, 230918_202303, 230918_202320, 230918_202336, ...etc...
//:--[6654.515978]
[ '/code', "//:--[5] countdown and synchronization prompt
{
	{ SinOsc.ar(800, 0, 0.1).perc(0.01, 0.1).pan } +> \\ping;
	1.wait;
	{ SinOsc.ar(800, 0, 0.1).perc(0.01, 0.1).pan } +> \\ping;
	1.wait;
	{ SinOsc.ar(800, 0, 0.1).perc(0.01, 0.1).pan } +> \\ping;
}.fork;" ]
//:--[6659.508949667]
[ '/code', "//:--[40] start the sound with named busses
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
{ \\x7.gt.lag(0.5) * 0.5 } @>.jun \\amp;
{ \\z8.gt(0.3).lag(0.5) * 0.5 } @>.asayo \\amp;

//======= Set sensor values to off =======
0 >>! \\x7;
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
//:--[6699.493068459]
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
//:--[6739.493354167]
[ '/code', "//:--[150] start glissando
//180' gliss + 20'stable
{ XLine.kr(200, 800, 120) } @>.jun \\freq; // gliss duration: 180!
{ XLine.kr(800, 200, 120) } @>.asayo \\freq; // gliss duration: 180!" ]
//:--[6889.493236834]
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
//:--[6909.493016875]
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
//:--[6979.491971917]
[ '/code', "//:--[10] fadeout
{ XLine.kr(2, 0.0001, 10, doneAction: 2) - 0.0001 } @>.jun \\vol;
{ XLine.kr(2, 0.0001, 10, doneAction: 2) - 0.0001 } @>.asayo \\vol;
" ]
//:--[7002.776307375]
[ '/code', "\\jun.envir.stopSynths" ]
//:--[7002.776419709]
[ '/code', "'jun'.envir play: ( 'rate': [ 0.12300649892359, \"\\\\z6.sr.lin(0.25, 0.9).round(0.1);\", 'on' ], 'endframe': 2451380, 'buf': 'crickets', 'shift': [ 0.0, \"\\\\x7.sr.lin(-280, -120).lag(0.5).round(0.1);\", 'on' ], 
  'stretch': [ 1.0521223021583, \"\\\\x6.sr.lin(1.1, 1.5).lag(0.5).round(0.1);\", 'on' ], 'pan': [ 0.70503597122302, \"\", 'off' ], 'vol': [ 8.6798043579525, \"\", 'off' ], 'playfunc': 'grainmagshift', 'trate': [ 3.8314873847371, \"\\\\z7.sr\", 'off' ], 
  'pos': [ 0.21942446043165, \"\\\\x7.sr\", 'on' ], 'amp': [ 0.66674861549609, \"Xyz(7, 0.15, 0.05, 2.1) + Xyz(6, 0.15, 0.05, 2.1) + 0.5\", 'on' ], 'startframe': 0 )" ]
//:--[7002.7768965]
[ '/code', "{Xyz(7, 0.15, 0.05, 2.1) + Xyz(6, 0.15, 0.05, 2.1) + 0.5}@>.jun \\amp" ]
//:--[7002.783664375]
[ '/code', "{\\x7.sr}@>.jun \\pos" ]
//:--[7002.787273625]
[ '/code', "{\\z6.sr.lin(0.25, 0.9).round(0.1);}@>.jun \\rate" ]
//:--[7002.787340542]
[ '/code', "{\\x6.sr.lin(1.1, 1.5).lag(0.5).round(0.1);}@>.jun \\stretch" ]
//:--[7002.789199709]
[ '/code', "{\\x7.sr.lin(-280, -120).lag(0.5).round(0.1);}@>.jun \\shift" ]
//:--[7006.017953292]
[ '/code', "\\asayo.envir.stopSynths" ]
//:--[7006.021080209]
[ '/code', "'asayo'.envir play: ( 'vol': [ 3.356638951877, \"\", 'off' ], 'endframe': [ 1826955, nil, nil ], 'buf': 'crickets', 'amp': [ 1.0, \"Xyz(5, 0.2, 0.3, 1.5)\", 'on' ], 
  'startframe': [ 9906, nil, nil ], 'playfunc': 'grainfroth', 'trate': [ 0.17625899280576, \"\\\\z5.sr\", 'off' ], 'pos': [ 0.079185223249971, \"\\\\x5.sr\", 'on' ] )" ]
//:--[7006.031937875]
[ '/code', "{Xyz(5, 0.2, 0.3, 1.5)}@>.asayo \\amp" ]
//:--[7006.032039292]
[ '/code', "{\\x5.sr}@>.asayo \\pos" ]
//:--[7288.607925417]
[ '/code', "\\jun.envir.stopSynths" ]
//:--[7289.891028125]
[ '/code', "\\asayo.envir.stopSynths" ]
//:--[7370.052932209]
[ '/code', "\\jun.envir.stopSynths" ]
//:--[7370.054198209]
[ '/code', "'jun'.envir play: ( 'rate': [ 0.12300649892359, \"\\\\z6.sr.lin(0.15, 0.9).lag(3.0)\", 'on' ], 'endframe': [ 21861056, nil, nil ], 'buf': 'hanbaiki', 'shift': [ -45.323741007194, \"\\\\x7.sr.lin(-480, -220).lag(3.5)\", 'on' ], 
  'stretch': [ 2.058309352518, \"\\\\x6.sr.lin(0.5, 2.5).lag(2.5)\", 'on' ], 'pan': [ 0.68345323741007, \"\", 'off' ], 'vol': [ 8.6798043579525, \"\", 'off' ], 'playfunc': 'grainmagshift', 'trate': [ 4.8761198231362, \"\\\\z7.sr\", 'off' ], 
  'pos': [ 0.30215827338129, \"\\\\x7.sr\", 'on' ], 'amp': [ 0.70246105274054, \"Xyz(7, 0.15, 0.05, 2.1) + Xyz(6, 0.15, 0.05, 2.1) - 2.0\", 'on' ], 'startframe': [ 989188, nil, nil ] )" ]
//:--[7370.063655875]
[ '/code', "{Xyz(7, 0.15, 0.05, 2.1) + Xyz(6, 0.15, 0.05, 2.1) - 2.0}@>.jun \\amp" ]
//:--[7370.06785975]
[ '/code', "{\\x7.sr}@>.jun \\pos" ]
//:--[7370.068924875]
[ '/code', "{\\z6.sr.lin(0.15, 0.9).lag(3.0)}@>.jun \\rate" ]
//:--[7370.070258542]
[ '/code', "{\\x6.sr.lin(0.5, 2.5).lag(2.5)}@>.jun \\stretch" ]
//:--[7370.071608584]
[ '/code', "{\\x7.sr.lin(-480, -220).lag(3.5)}@>.jun \\shift" ]
//:--[7554.221563042]
[ '/code', "0.046581439884064 @>.jun \\amp" ]
//:--[7554.223667042]
[ '/code', "0.046581439884064 @>.jun \\amp" ]
//:--[7554.603021709]
[ '/code', "0.046581439884064 @>.jun \\amp" ]
//:--[7554.62260125]
[ '/code', "0.046581439884064 @>.jun \\amp" ]
//:--[7554.641265375]
[ '/code', "0.046581439884064 @>.jun \\amp" ]
//:--[7554.657351292]
[ '/code', "0.045041664510119 @>.jun \\amp" ]
//:--[7554.673165542]
[ '/code', "0.045041664510119 @>.jun \\amp" ]
//:--[7554.690611625]
[ '/code', "0.045041664510119 @>.jun \\amp" ]
//:--[7554.706681834]
[ '/code', "0.043527767713886 @>.jun \\amp" ]
//:--[7554.721759375]
[ '/code', "0.043527767713886 @>.jun \\amp" ]
//:--[7554.735274917]
[ '/code', "0.042039749495368 @>.jun \\amp" ]
//:--[7554.754956875]
[ '/code', "0.042039749495368 @>.jun \\amp" ]
//:--[7554.768874]
[ '/code', "0.040577609854562 @>.jun \\amp" ]
//:--[7554.786367834]
[ '/code', "0.040577609854562 @>.jun \\amp" ]
//:--[7554.805587375]
[ '/code', "0.040577609854562 @>.jun \\amp" ]
//:--[7554.905453834]
[ '/code', "0.040577609854562 @>.jun \\amp" ]
//:--[7554.921194]
[ '/code', "0.03914134879147 @>.jun \\amp" ]
//:--[7554.940372125]
[ '/code', "0.037730966306092 @>.jun \\amp" ]
//:--[7554.953603542]
[ '/code', "0.034987837068475 @>.jun \\amp" ]
//:--[7554.968814042]
[ '/code', "0.032348222141711 @>.jun \\amp" ]
//:--[7554.98867675]
[ '/code', "0.029812121525801 @>.jun \\amp" ]
//:--[7555.002794625]
[ '/code', "0.028582889084416 @>.jun \\amp" ]
//:--[7555.019622375]
[ '/code', "0.027379535220744 @>.jun \\amp" ]
//:--[7555.036530125]
[ '/code', "0.025050463226541 @>.jun \\amp" ]
//:--[7555.052947459]
[ '/code', "0.02392474509601 @>.jun \\amp" ]
//:--[7555.069608042]
[ '/code', "0.022824905543191 @>.jun \\amp" ]
//:--[7555.086101084]
[ '/code', "0.021750944568087 @>.jun \\amp" ]
//:--[7555.105790417]
[ '/code', "0.020702862170695 @>.jun \\amp" ]
//:--[7555.119759917]
[ '/code', "0.019680658351017 @>.jun \\amp" ]
//:--[7555.135588334]
[ '/code', "0.019680658351017 @>.jun \\amp" ]
//:--[7555.1523665]
[ '/code', "0.019680658351017 @>.jun \\amp" ]
//:--[7555.169399625]
[ '/code', "0.019680658351017 @>.jun \\amp" ]
//:--[7555.186471]
[ '/code', "0.018684333109052 @>.jun \\amp" ]
//:--[7555.220046709]
[ '/code', "0.018684333109052 @>.jun \\amp" ]
//:--[7555.236471042]
[ '/code', "0.018684333109052 @>.jun \\amp" ]
//:--[7555.254251875]
[ '/code', "0.018684333109052 @>.jun \\amp" ]
//:--[7555.27166425]
[ '/code', "0.018684333109052 @>.jun \\amp" ]
//:--[7555.288319667]
[ '/code', "0.017713886444801 @>.jun \\amp" ]
//:--[7555.304415875]
[ '/code', "0.017713886444801 @>.jun \\amp" ]
//:--[7555.323360125]
[ '/code', "0.017713886444801 @>.jun \\amp" ]
//:--[7555.337556334]
[ '/code', "0.017713886444801 @>.jun \\amp" ]
//:--[7555.352835417]
[ '/code', "0.016769318358263 @>.jun \\amp" ]
//:--[7555.369474959]
[ '/code', "0.016769318358263 @>.jun \\amp" ]
//:--[7555.388046042]
[ '/code', "0.016769318358263 @>.jun \\amp" ]
//:--[7555.403881]
[ '/code', "0.015850628849438 @>.jun \\amp" ]
//:--[7555.420626792]
[ '/code', "0.014957817918327 @>.jun \\amp" ]
//:--[7555.437870917]
[ '/code', "0.014090885564929 @>.jun \\amp" ]
//:--[7555.455439084]
[ '/code', "0.013249831789245 @>.jun \\amp" ]
//:--[7555.472039292]
[ '/code', "0.012434656591274 @>.jun \\amp" ]
//:--[7555.489104792]
[ '/code', "0.012434656591274 @>.jun \\amp" ]
//:--[7555.506153334]
[ '/code', "0.011645359971016 @>.jun \\amp" ]
//:--[7555.51961575]
[ '/code', "0.011645359971016 @>.jun \\amp" ]
//:--[7555.538674375]
[ '/code', "0.011645359971016 @>.jun \\amp" ]
//:--[7555.555029292]
[ '/code', "0.010881941928472 @>.jun \\amp" ]
//:--[7555.571305375]
[ '/code', "0.010881941928472 @>.jun \\amp" ]
//:--[7555.58701525]
[ '/code', "0.010881941928472 @>.jun \\amp" ]
//:--[7555.602869959]
[ '/code', "0.010144402463641 @>.jun \\amp" ]
//:--[7555.61890175]
[ '/code', "0.009432741576523 @>.jun \\amp" ]
//:--[7555.636280334]
[ '/code', "0.0087469592671187 @>.jun \\amp" ]
//:--[7555.654198375]
[ '/code', "0.0080870555354278 @>.jun \\amp" ]
//:--[7555.671905667]
[ '/code', "0.0074530303814502 @>.jun \\amp" ]
//:--[7555.691756792]
[ '/code', "0.0062626158066353 @>.jun \\amp" ]
//:--[7555.703138834]
[ '/code', "0.0057062263857978 @>.jun \\amp" ]
//:--[7555.719608917]
[ '/code', "0.0051757155426738 @>.jun \\amp" ]
//:--[7555.737802125]
[ '/code', "0.0051757155426738 @>.jun \\amp" ]
//:--[7555.754636125]
[ '/code', "0.0046710832772631 @>.jun \\amp" ]
//:--[7557.892377584]
[ '/code', "\\jun.envir.stopSynths" ]
//:--[7596.136962792]
[ '/code', "//:--[60] ===== ASAYO ======
// initialize all control parameters first
101 @>.asayo \\ls1;  // par 1
102 @>.asayo \\ls2;  // par 2
0.01 @>.asayo \\tempo; // par 3
10 @>.asayo \\idiv; // par 4
// 1.015 @>.asayo \\lin;
64 @>.asayo \\ma64;
200 @>.asayo \\base;
1 @>.asayo \\sweepdir; // par
// 0.1 @>.asayo \\wet;
10 @>.asayo \\wet;
0.9 @>.asayo \\soft;
{
	var p;
	p=Impulse;
	mean({|i|
		Pluck.ar(
			LFSaw.ar([
				// \\ls1.br(102),
				\\ls1.br, // par 1
				\\ls2.br  // par 2
				// \\ls2.br(101)
			]),
			x=p.ar(
				\\tempo.br, // \\tempo.br(1) // par 3
				i/
				\\idiv.br // \\idiv.br(10) // par 4
			)
			+p.ar(0) + \\start.tr(0),
			1,
			1/
			Latch.ar(
				// \\lin.br(1.015)  // par 5???
				1.015
				**Sweep.ar(
					0,
					\\sweepdir.br // \\sweepdir.br(1)
				)
				* \\ma64.br // 64
				% 1
				+ 1
				* \\base.br, // 200,
				x
			),
			\\wet.br, // \\wet.br(4),
			\\soft.br // \\soft.br(0.2)
		)
	}!10) * Env([0.1, 0.15, 1], [1.5, 2.01], 5).kr;
} +> \\asayo;
" ]
//:--[7612.90476975]
[ '/code', "{ XLine.kr(0.01, 1, 120) } @>.asayo \\tempo; // par 3" ]
//:--[7664.151726417]
[ '/code', "15 @>.asayo \\idiv;" ]
//:--[7714.667594542]
[ '/code', "{ Line.kr(20, 30, 60) } @>.asayo \\idiv;" ]
//:--[7736.091070459]
[ '/code', "5 @>.asayo \\wet;" ]
//:--[7763.941793084]
[ '/code', "-0.1 @>.asayo \\sweepdir;" ]
//:--[7777.719977125]
[ '/code', "-0.01 @>.asayo \\sweepdir;" ]
//:--[7806.203001125]
[ '/code', "1.1 @>.asayo \\tempo;" ]
//:--[7817.972985917]
[ '/code', "1.2 @>.asayo \\tempo;" ]
//:--[7831.528190042]
[ '/code', "10 @>.asayo \\idiv;" ]
//:--[7844.421914334]
[ '/code', "0.0 @>.asayo \\sweepdir;" ]
//:--[7850.344831167]
[ '/code', "0.01 @>.asayo \\sweepdir;" ]
//:--[7864.785584667]
[ '/code', "0.02 @>.asayo \\sweepdir;" ]
//:--[18363.422319417]
[ '/code', "var o, p;
o = [
	MultiLevelIdentityDictionary.prNew,  IdentityDictionary.prNew,  Array.prNew(32),  IdentityDictionary.prNew,  
	Array.prNew(32),  Event.prNew,  Array.prNew(28),  PathName.prNew,  
	\"/Users/iani/Music/SuperCollider Recordings/221102/\",  List.prNew,  Array.prNew(6),  Bookmarks.prNew,  
	PathName.prNew,  \"/Users/iani/Music/SuperCollider Recordings\",  PathName.prNew,  \"/Users/iani/Music/SuperCollider Recordings/221102/221102_145241/\",  
	List.prNew,  Array.prNew(7),  IdentityDictionary.prNew,  Array.prNew(32),  
	Event.prNew,  Array.prNew(28),  PathName.prNew,  \"/Users/iani/sc-projects/presets/scores/\",  
	List.prNew,  Array.prNew(6),  Bookmarks.prNew,  PathName.prNew,  
	\"/Users/iani/sc-projects/presets/\",  List.prNew,  Array.prNew(5),  PathName.prNew,  
	\"/Users/iani/sc-projects/\",  Event.prNew,  Array.prNew(28),  PathName.prNew,  
	\"/Users/iani/Music/SuperCollider Recordings/221115/\",  List.prNew,  Array.prNew(6),  Bookmarks.prNew,  
	PathName.prNew,  \"/Users/iani/Music/SuperCollider Recordings\",  PathName.prNew,  \"/Users/iani/Music/SuperCollider Recordings/221115/221115_103340/\",  
	List.prNew,  Array.prNew(7),  Event.prNew,  Array.prNew(4),  
	\"/Users/iani/Library/Application Support/SuperCollider/Preferences.scd\"
];
p = [
	// MultiLevelIdentityDictionary
	0, [ dictionary: o[1] ],  
	// IdentityDictionary
	1, [ array: o[2],  size: 3,  
		proto: nil,  parent: nil,  
		know: false ],  
	// Array
	2, [ nil,  nil,  nil,  nil,  
		'OscFileNavigator',  o[3],  nil,  

//the end

