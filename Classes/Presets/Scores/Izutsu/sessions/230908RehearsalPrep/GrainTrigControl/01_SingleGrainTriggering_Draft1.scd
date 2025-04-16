/*  8 Sep 2023 12:43
Trigger single grains with SendReply.
*/
//:preparing - simple test of SendReply, with audio feedback
{
	var trig;
	trig = Impulse.kr(1);
	trig.sendReply();
	WhiteNoise.ar(Decay.kr(trig)).amplify.pan;
} +> \test;
//:preparing - simple test of SendReply, silent
{
	var trig;
	trig = Impulse.kr(1);
	trig.sendReply();
	// WhiteNoise.ar(Decay.kr(trig)).amplify.pan;
} +> \test;
//:preparing: reaction to trigger
\trigger >>> { "trigger received".postln; };
//:preparing: audible reaction to trigger
\trigger >>> { (degree: -10 rrand: 20).play; };
//:
\trigger >>> { Chord(\c).play };
//:
\trigger >>> { Chord(\cs, \e, \g, \bf).transpose([0, 2, 5].choose).play };
//:
\trigger >>> {
	{
		{
			(1..10).normalize(0, 5) do: { | t |
				// t.postln;
				Chord(\cs, \e, \g, \bf).transpose(t).play;
				0.1.wait;
			};
		}.fork;
	}.value;
}
//:Trigger a grain:
//TGrains.ar(numChannels, trigger: 0, bufnum: 0, rate: 1, centerPos: 0, dur: 0.1, pan: 0, amp: 0.1, interp: 4)
{
	var buf;
	buf = \crickets.buf;
	TGrains.ar(1, Impulse.kr(5), buf.bufnum)
} +> \ttest;
//:
{
	var buf;
	buf = \crickets.buf;
	TGrains.ar(1, Impulse.kr(2),
		buf.bufnum,
		\rate.br(1),
		\pos.br(0),
		\dur.br(0.5)
	)
} +> \ttest;
//:
0.5 @>.ttest \pos;
//:
0.9 @>.ttest \dur;
//:
0.5 @>.ttest \rate;
//
//:
(0.2 rrand: 2.5) @>.ttest \rate;
0.05 @>.ttest \dur;
//:
//:
bs