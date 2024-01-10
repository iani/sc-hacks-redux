//onsetcode
//Exporting 38 code snippets on 231010_233429
//Source: [ 231010_152311, 231010_152322, 231010_152406, 231010_152420, 231010_152432, 231010_152446, 231010_152459, 231010_152511, 231010_152525, 231010_152538, 231010_152551, 231010_152606, 231010_152620, 231010_152634, 231010_152647, 231010_152700, 231010_152717, 231010_152732, 231010_152746, 231010_152800, 231010_152814, 231010_152827, 231010_152841, 231010_152856, 231010_152910, 231010_152923, 231010_152935, 231010_152948, 231010_153004, 231010_153039, 231010_153124 ]
//:--[0]
[
	\rate.asSpec,
	// \trigger.ps(0, 1, 0, \linear, 1),
	// trigger should not create gui controls.
	// \startpos.ps(0, 10000000),
	// \loop.ps(0, 1, 0, \linear, 1),
	\vol.asSpec,
	[0.01, 100, \lin, 1, 0, \trig].asSpec
]
// startframe and endframe are added by SfSelection
//:--[0.00051016699990214]
[
	\freeze.ps(0, 1, 0, \linear, 1),
	\pan.asSpec2
]
//:--[0.0034293749999961]
[ // ps lo hi default map step
	\pos.ps(0, 1, 0, \lin, 0),
	\rate.asSpec,
	\trate.ps(1, 120, 50, \exp, 0),
	\vol.asSpec
]
//:--[0.0038479999998344]
[ // ps lo hi default map step
	\pos.ps(0, 1, 0, \lin, 0),
	\rate.asSpec,
	\vol.asSpec
	// TODO: add more specs for new version of Thu 14 Sep 2023 14:24
]
//:--[0.0044915419998688]
[ // ps lo hi default map step
	\rate.asSpec,
	\vol.asSpec
]
//:--[0.0049352500000168]
[
	\wipe.ps(-0.9, 0.9, 0), // !
]
//:--[0.00567483299983]
/* 20 Jul 2023 00:27

*/
[
	\dur.ps(0, 1),
	\pos.ps(0, 1, 0, \lin, 0),
	\trate.ps(0, 1, 0, \lin, 0),
	\rate.asSpec,
	\vol.asSpec
]
//:--[0.0062507080001524]
/* 20 Jul 2023 00:27

*/
[
	// \dur.ps(0, 1),
	\pos.ps(0, 1, 0, \lin, 0),
	\trate.ps(0, 1, 0, \lin, 0),
	// \rate.asSpec,
	\vol.asSpec
]
//:--[0.0071745830000509]
/* 20 Jul 2023 00:27

*/
[
	\pos.ps(0, 1, 0, \lin, 0),
	\trate.ps(0, 1, 0, \lin, 0),
	\vol.asSpec
]
//:--[0.0073131250001097]
[
	\thresh.asSpec2,
	\pan.asSpec2
]
//:--[0.0074858329999188]
[
	\thresh.asSpec2.default_(80),
	\pan.asSpec2
]
//:--[0.0079458750001322]
[
	\stretch.ps(0.01, 10, 1),
	\shift.ps(-700, 700, 0),
	\pan.asSpec2
]
//:--[0.0082257919998483]
/* 20 Jul 2023 00:27

*/
[
	// \dur.ps(0, 1),
	\pos.ps(0, 1, 0, \lin, 0),
	\trate.ps(0, 1, 0, \lin, 0),
	\rate.asSpec,
	\vol.asSpec
]
//:--[0.0085743330000696]
[
	\rate.asSpec2,
	\trigger.ps(0, 1, 0, \linear, 1),
	// \startpos.ps(0, 10000000),
	\loop.ps(0, 1, 0, \linear, 1),
	\vol.ps(0, 1, 1)
]
//:--[0.0090287500001978]
/* 11 Sep 2023 00:47
Just a test - nil
*/

[\default, \default, \cick]
//:--[0.16203150000001]
// later substitute this with specs for players that translate sensor numbers?
[
	[\jun, [[[1, 3, 5, 9, 11], 1], [[2, 4, 7, 8, 10, 12], 2]]],
	[\asayo, [[[1, 3, 5, 9, 11], 3], [[2, 4, 7, 8, 10, 12], 4]]],
	[\justine, [[[1, 3, 5, 9, 11], 1], [[2, 4, 7, 8, 10, 12], 2]]],
	[\mary1, [[[1, 3, 5, 9, 11], 5], [[2, 4, 7, 8, 10, 12], 7]]],
	[\mary2, [[[1, 3, 5, 9, 11], 5], [[2, 4, 7, 8, 10, 12], 7]]],
	[\yoshimitsu1, [[[1, 3, 5, 9, 11], 9], [[2, 4, 7, 8, 10, 12], 10]]],
	[\yoshimitsu2, [[[1, 3, 5, 9, 11], 9], [[2, 4, 7, 8, 10, 12], 10]]],
	[\sanya, [[[1, 3, 5, 9, 11], 11], [[2, 4, 7, 8, 10, 12], 12]]]
] collect: PlayerIdConverter(*_);
//:--[142.927882333]
[12, 10, 9, 8, 4, 6, 8, 7, 10 * 16 / 15 / 2, 10 * 14 / 15 / 2] @> \freqs;
//:--[144.430028833]
{ Impulse.kr(2.9, pi) } @>.jun \trig;
//:--[146.625694958]
\crickets +>.jun \buf;
//:--[148.02675975]
//:GrainBuf setup + start
//buffer for demand ugens for frequency;
[12, 10, 9, 8, 4, 6, 8, 7, 10 * 16 / 15 / 2, 10 * 14 / 15 / 2] @> \freqs;
// trigger every 2.9 seconds
{ Impulse.kr(2.9, pi) } @>.jun \trig;
// use crickets buffer
\crickets +>.jun \buf;
// volume 0.1 (rather low)
0.1 @>.jun \vol;
{
	var src, out, freqs, amps, ringtimes;
	src = GrainBuf0_.ar;
	freqs = (5..8) / 2 * 100 @@>.1 \f;
	amps = 0.01.dup(4) @@>.1 \a;
	ringtimes = 1.dup(4) @@>.1 \r;
	// ringtimes
	out = DynKlank.ar(
		`[freqs, amps, ringtimes],
		src
	).pan;
	// out.postln;
} +> \jun;
// LFNoise2 -> pos
{ LFNoise2.kr(1/10).range(0, 1) } @>.jun \pos;
// LFNoise2 -> rate
{ LFNoise2.kr(1/10).range(0.8, 1.2) } @>.jun \rate;
//:--[152.086089375]
{ LFNoise2.kr(1/10).range(0, 1) } @>.jun \pos;
//:--[154.415365083]
{ LFNoise2.kr(1/10).range(0.8, 1.2) } @>.jun \rate;
//:--[156.552929542]
{ LFNoise2.kr(1/1).range(0.4, 1.2) } @>.jun \rate;
//:--[158.924710458]
1200 @>.jun \f1;  /// keep this
//:--[161.879123792]
1400 @>.jun \f1;  /// keep this
//:--[164.168129417]
1200 @>.jun \f1;  /// keep this
//:--[166.438375375]
1500 @>.jun \f2;  /// keep this
//:--[168.5400735]
1100 @>.jun \f1;  /// keep this
//:--[170.925559875]
1000 @>.jun \f1;  /// keep this
//:--[172.834513917]
1400 @>.jun \f1; /// keep this
//:--[174.867096458]
1000 @>.jun \f2; /// keep this
//:--[176.890909208]
250 @>.jun \f3;
//:--[179.035688583]
150 @>.jun \f3;
//:--[181.446248875]
100 @>.jun \f4;
//:--[195.269888375]
{ Xyz(1, 0.15, 0.05, 3.1, 0.005) * 0.05 } @>.jun \a3;
//:--[196.675958417]
{ Xyz(2, 0.15, 0.05, 3.1, 0.005) * 0.1 } @>.jun \a4;
//:--[197.962533875]
{ Xyz(5, 0.15, 0.05, 3.1, 0.005) * 1.5 } @>.jun \a1;
//:--[199.972539333]
{ Xyz(7, 0.15, 0.05, 3.1, 0.005) * 1.5 } @>.jun \a2;

//the end

