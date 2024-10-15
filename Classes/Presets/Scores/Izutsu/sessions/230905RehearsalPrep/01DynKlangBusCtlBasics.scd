//Tue  5 Sep 2023 DynKlang: control of voices through busses.
//:starting situation. kr
{
	DynKlang.ar(
		`[
			[\a.kr(400), \b.kr(500)],
			0.01,
			pi
		]
	)
} +> \test;
//:set a kr control
600 +>!.test \a;
//:starting situation. busses. PREP busses
a = Bus.control;
b = Bus.control;
a.set(400);
b.set(500);
//:busses - TEST synth with busses
{
	DynKlang.ar(
		`[
			[In.kr(a.index), In.kr(b.index)],
			0.01,
			pi
		]
	)
} +> \test;
//:set the busses
a.set(600);
//:add one control signal to one bus
{ Out.kr(a.index, SinOsc.kr(5).range(390, 410)) } +> \x;
//:add a second control signal ugens in the busses
{ Out.kr(a.index, XLine.kr(1, 500, 3)) } +> \y;
//:replace the sine with noise
{ Out.kr(a.index, LFNoise2.kr(15).range(350, 450)) } +> \x;
//:================== using named busses
//:start the sound with named busses
{
	DynKlang.ar(
		`[
			[\a.br(400), \b.br(500)],
			0.01,
			pi
		]
	)
} +> \test;
//:modulate bus a
{ XLine.kr(400, 600, 5) } @>.test \a;
//:add noise modulation to bus a.
{ LFNoise1.kr(10).range(-50, 50).bout(\a) } +>.test \mod;
//:replace the xline modulator on a
{ XLine.kr(600, 200, 2) } @>.test \a;
//:replace the xline modulator on a, again
{ XLine.kr(200, 1000, 2) } @>.test \a;
//:================= prepare for more busses
//:start the sound with named busses
{
	var fmod;
	fmod = [\a.br(200), \b.br(600)];
	DynKlang.ar(
		`[
			fmod,
			0.01,
			pi
		]
	)
} +> \test;

{ XLine.kr(200, 600, 5) } @>.test \a;
{ XLine.kr(600, 200, 10) } @>.test \b;
//:================= add amp
//:start the sound with named busses
{
	var fmod;
	fmod = [\a.br(200), \b.br(600)];
	DynKlang.ar(
		`[
			fmod,
			0.5,
			pi
		]
	).amplify.pan;
} +> \test;
0.1 @>.test \amp; // initialize amp
//:add glissandi
{ XLine.kr(200, 600, 5) } @>.test \a;
{ XLine.kr(600, 200, 10) } @>.test \b;
//:fadeout
{ XLine.kr(0.1, 0.0001, 2, doneAction: 2) - 0.0001 } @>.test \amp;
//:================= add multiply modulator
//:start the sound with named busses
{
	var fmod;
	fmod = [\a.br(200), \b.br(600)] * [\c.br(1), \d.br(1)];
	DynKlang.ar(
		`[
			fmod,
			0.5,
			pi
		]
	).amplify.pan;
} +> \test;
0.1 @>.test \amp; // initialize amp
//:add glissandi
{ XLine.kr(200, 600, 5) } @>.test \a;
{ XLine.kr(600, 200, 10) } @>.test \b;
//:add noise modulation
{ LFNoise2.kr(10).range(*0.1.bi) } @>.test \c;
{ LFNoise0.kr(5).range(*0.1.bi) } @>.test \d;
//:add noise modulation with graduall increasing range
{ LFNoise2.kr(10).range(*0.5.bi.collect{ | r | XLine.kr(1, r, 10) }) } @>.test \c;
{ LFNoise0.kr(15).range(*0.5.bi.collect{ | r | XLine.kr(1, r, 10) }) } @>.test \d;
//:fadeout
{ XLine.kr(0.1, 0.0001, 2, doneAction: 2) - 0.0001 } @>.test \amp;
