//Basic crickets with DynKlank
//manual control
//Adding mul to slide entire chords
/* 15 Sep 2023 00:21
Adapt this to script for scene, with sensor controls.

*/
//:GrainBuf setup + start
//buffer for demand ugens for frequency;
// [12, 10, 9, 8, 4, 6, 8, 7, 10 * 16 / 15 / 2, 10 * 14 / 15 / 2] @> \freqs;
// trigger every 2.9 seconds
{ Impulse.kr(2.9, pi) } @>.jun \trig;
// use crickets buffer
\crickets +>.jun \buf;
// volume 0.1 (rather low)
0.1 @>.jun \vol;
{
	var src, out, freqs, amps, ringtimes;
	src = GrainBuf0_.ar;
	// freqs = (5..8) / 2 * 100 @@>.1 \f; // original was too low
	// starting musume with higher frequencies
	freqs = (5..8) / 2 * 400 @@>.1 \f;
	amps = 0.01.dup(4) @@>.1 \a;
	ringtimes = 1.dup(4) @@>.1 \r;
	// ringtimes
	out = DynKlank.ar(
		`[freqs, amps, ringtimes],
		src
	);
	out = Limiter.ar(out, 0.5);

	out.pan;
	// out.postln;
} +> \jun;
//:
// LFNoise2 -> pos
{ LFNoise2.kr(1/10).range(0, 1) } @>.jun \pos;
// LFNoise2 -> rate
{ LFNoise2.kr(1/10).range(0.8, 1.2) } @>.jun \rate;
//:noise rate variations
{ LFNoise2.kr(1/1).range(0.4, 1.2) } @>.jun \rate;
//:first play a little adjusting the notes without control from dancers
//: ===! asayo: high register, f1, f2
//:1200 @>.jun \f1
1200 @>.jun \f1;  /// keep this
//:1200 @>.jun \f1
1400 @>.jun \f1;  /// keep this
//:1200 @>.jun \f1
500 @>.jun \f2;  /// keep this
//:1200 @>.jun \f1
1100 @>.jun \f1;  /// keep this
//:1200 @>.jun \f1
1000 @>.jun \f1;  /// keep this
//:1200 @>.jun \f1
1400 @>.jun \f1; /// keep this
//:1000 @>.jun \f2
1000 @>.jun \f2; /// keep this
//:900 @>.jun \f3
1250 @>.jun \f3;
//:900 @>.jun \f3 // U150
750 @>.jun \f3;
//:900 @>.jun \f3 use 100
800 @>.jun \f4;

//:1000 @>.jun \f2
// 900 @>.jun \f2;
////:900 @>.jun \f3
//900 @>.jun \f3;
//:800 @>.jun \f4
800 @>.jun \f4;
//:Jun: -- wider spaced lower register f3, f4
//: @>.jun \f1
400 / 2 @>.jun \f3; // keep this
//:@>.jun \f2
600 / 2 @>.jun \f4; // keep this
//:------------- now introduce individual hand control
1200 @>.jun \f1;  /// keep this
1000 @>.jun \f2; /// keep this
400 / 2 @>.jun \f3; // keep this
600 / 2 @>.jun \f4; // keep this

//: xyz control jun f3 amp LH ---- DO USE THIS YES YES YES
{ Xyz(5, 0.15, 0.05, 3.1, 0.005) * 0.5 } @>.jun \a3; // asayo L
// xyz control jun f3 amp LH DO USE THIS YES YES YES
{ Xyz(6, 0.15, 0.05, 3.1, 0.005) * 0.5 } @>.jun \a4;
// xyz(control ASAYO f1 amp LH ----- DO USE THIS YES YES YES
{ Xyz(8, 0.15, 0.05, 3.1, 0.005) * 0.5 } @>.jun \a1; // asayo R
// xyz control ASAYO f2 amp LH ------ DO USE THIS YES YES YES
{ Xyz(7, 0.15, 0.05, 3.1, 0.005) * 0.5 } @>.jun \a2;
//:========= individual control code section ends here
//: @>.jun \f3
800 / 2 @>.jun \f3;
//: @>.jun \f3
700 / 2 @>.jun \f3;
//: @>.jun \f4
1000 / 2 @>.jun \f4;
//: @>.jun \f4
900 / 2 @>.jun \f4;
//: 16/15 @>.jun \f4
1000 * 16 / 15 / 2 @>.jun \f4;
//: 14/15 @>.jun \f4
1000 * 14 / 15 / 2 @>.jun \f4;
//:=================== trying to add controls
//:zero amplitudes of individual frequencies:
0 @>.jun \a1;
0 @>.jun \a2;
0 @>.jun \a3;
0 @>.jun \a4;
//: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11
// WE WILL ONLY USE INDIVIDUAL HAND TO INDIVIDUAL NOTE AMP CONTROL
//:
//:trigger with Xyz. Jun LH
{
	(Xyz(7, 0.15, 0.05, 1.1, 0.01) > 0.2).sendReply(\junlon)
};
//:monitor jun's trigger
\junlon >>> { "Jun left hand on".postln; (dur: 0.1, amp: 0.04, freq: 400).play; };
//:
{
	\x5.gt.sendReply(\asayolon)
} +>.jun \asayolon;
//:
\asayolon >>> { (degree: 10.rrand(20), dur: 0.2).play; };
//:
{
	SendReply.kr(\x5.sr > 0.5, \asyolon)
} +>.jun \asayolon;

//:trigger with Xyz. Asayo LH
{
	WhiteNoise.ar(Decay.kr(Xyz(8, 0.15, 0.05, 0.1, 0.01) > 0.02)) * 0.001
	//.sendReply(\asayolon)
} +>.jun \asayoron;
//:trigger with Xyz. Asayo LH
{
	SinOsc.ar(400, 0, Decay.kr(Xyz(5, 0.15, 0.05, 0.1, 0.01) > 0.02)) * 0.001
	//.sendReply(\asayolon)
} +>.jun \asayolon;
//:monitor asayos's trigger
\junlon >>> { "Asayo left hand on".postln; (dur: 0.1, amp: 0.04, freq: 400).play; };
//:
(dur: 0.1, amp: 0.04, freq: 100).play;
//: stop amp control
nil @>.jun \amp;
//: xyz control asayo amp
{ Xyz(5, 0.15, 0.05, 1.1, 0.01) + Xyz(8, 0.15, 0.05, 1.1, 0.01) } @>.jun \amp;
//:
{
	Xyz(7, 0.15, 0.05, 1.1, 0.01) + Xyz(6, 0.15, 0.05, 1.1, 0.01) +
	Xyz(5, 0.15, 0.05, 1.1, 0.01) + Xyz(8, 0.15, 0.05, 1.1, 0.01)
} @>.jun \amp;
//: xyz control jun+asayo amp
{
	Xyz(5, 0.15, 0.05, 2.1) + Xyz(8, 0.15, 0.05, 2.1)
	+ Xyz(6, 0.15, 0.05, 2.1) + Xyz(7, 0.15, 0.05, 2.1)
	+ 0.5
} @>.jun \amp;
//: trig for pitch change asayo
//
//
//:0.5 @>.jun \rate
0.5 @>.jun \rate;
//:1.5 @>.jun \rate
1.5 @>.jun \rate;
//:0.15 @>.jun \rate
0.15 @>.jun \rate;
//:2 @>.jun \rate
2 @>.jun \rate;
//:amp tests a1
0.001 @>.jun \a1;
//:amp tests a2
0.001 @>.jun \a2;
//:amp tests a3
0.001 @>.jun \a3;
//:amp tests a4
0.001 @>.jun \a4;
//:amp test amp !!!
1 @>.jun \amp;
//:======================================== controls
//:amp test amp Xyz
{ Xyz(7, 0.15, 0.05, 2.1) + Xyz(6, 0.15, 0.05, 2.1) + 0.01 } @>.jun \amp;
//:
//:==== ==============
//:====================REJECTED FREQUENCIES! ====================
//:---- the start, an octave lower:
//:1200 / 2 @>.jun \f1
1200 / 2 @>.jun \f1;
//:1000 / 2@>.jun \f2
1000 / 2 @>.jun \f2;
//:900 / 2 @>.jun \f3
900 / 2 @>.jun \f3;
//:800 / 2 @>.jun \f4
800 / 2 @>.jun \f4;
//: ALSO REJECTED bass range
//:600 / 2 @>.jun \f3
600 / 2 @>.jun \f3;
//:700 / 2 @>.jun \f4
700 / 2 @>.jun \f4;
