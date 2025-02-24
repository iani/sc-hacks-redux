//  8 Sep 2023 12:43
// Trigger single grains with SendReply. - second step
//:trigger grain when \trig is set.
{
	var buf;
	buf = \crickets.buf;
	TGrains.ar(1, \trig.tr(0), buf.bufnum, 0.5, 0, 1);
} +> \ttest;
//: test the trigger
1 +>!.ttest \trig;
//:Trigger the trigger with sendReply
{
	var trig;
	trig = Impulse.kr(15);
	trig.sendReply();
	// WhiteNoise.ar(Decay.kr(trig)).amplify.pan;
} +> \test;
\trigger >>> { 1 +>!.ttest \trig; };
//:Add control of other parameters
{
	var buf;
	buf = \crickets.buf;
	TGrains.ar(
		2,
		\trig.tr(0), // Impulse.kr(2),
		buf.bufnum,
		\rate.br(1),
		\pos.br(0) * BufFrames.kr(buf.bufnum),
		\dur.br(0.25)
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
{ XLine.kr(0.01, 1.0, 60) } @>.ttest \pos;
//:
{ XLine.kr(0.1, 2.0, 60) } @>.ttest \rate;
//:
0.9 @>.test \pos;
//:
//:
bs