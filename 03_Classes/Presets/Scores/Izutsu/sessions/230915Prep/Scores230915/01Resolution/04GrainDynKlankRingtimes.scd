//Basic crickets with DynKlank
//manual control
//Adding mul to slide entire chords
/* 15 Sep 2023 00:21
Adapt this to script for scene, with sensor controls.

*/
//:GrainBuf setup + start
//buffer for demand ugens for frequency;
[12, 10, 9, 8, 4, 6, 8, 7, 10 * 16 / 15 / 2,  10 * 14 / 15 / 2] @> \freqs;
// trigger every 2.9 seconds
{ Impulse.kr(2.9, pi) } @>.jun \trig;
// use crickets buffer
\crickets +>.jun \buf;
// volume 0.1 (rather low)
0.1 @>.jun \vol;
{
	var src, out, freqs, amps, ringtimes;
	src = GrainBuf0_.ar;
	freqs = (4..7) * 100 @@>.1 \f;
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
//:====== ADD Demand ctl of freqs
[12, 10, 9, 8, 4, 6, 8, 7, 10 * 16 / 15 / 2,  10 * 14 / 15 / 2] @> \freqs;
//:change fm!
1.5 @>.jun \fm;
//:change fm!
0.75 @>.jun \fm;
//:change fm!
1 @>.jun \fm;
//: NO! NO RANDOM SLIDE OF CHORD!
{ LFNoise0.kr(1).range(0.8, 1.2) } @>.jun \fm;
//:--- less interesting: Move through the file in 100 seconds
{ Line.kr(0, 1, 100) } @>.jun \pos;
//:Add Xyz control ??????????
{ Xyz(7, 0.15, 0.05, 2.1) + Xyz(6, 0.15, 0.05, 2.1) + 0.5 } @>.jun \amp;
//:asayo: higher rejister
//:1200 @>.jun \f1
1200 @>.jun \f1;
//:1000 @>.jun \f2
1000 @>.jun \f2;
//:900 @>.jun \f3
900 @>.jun \f3;
//:800 @>.jun \f4
800 @>.jun \f4;
//:Jun: ---- wider spaced lower register
//: @>.jun \f1
400 / 2 @>.jun \f1;
//:@>.jun \f2
600 / 2 @>.jun \f2;
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
