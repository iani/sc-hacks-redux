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
	trig = Impulse.kr(10);
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
\trigger >>> { Chord(\cs, \e, \g, \bf).transpose(5 rrand: 10).play };
//:
Chord(\c).transpose(1);

Chord(\c).play;

b = Chord(\c).copy.notes.first;
b.inspect;
b.copy.init;
b.freq;
b.transpose(1).freq;
