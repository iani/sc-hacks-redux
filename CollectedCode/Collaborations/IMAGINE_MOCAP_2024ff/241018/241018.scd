//:
{ PosePerson.kx(0, 0) } +> \x1;
//: simple test WITHOUT threshold
{ SinOsc.ar(PosePerson.kx(0, 0).linlin(0, 1, 400, 4000), 0, 0.1).dup } +> \x1;
//: simple test WITH threshold
{
	SinOsc.ar(PosePerson.kx(0, 0).linlin(0, 1, 400, 4000), 0, 0.1).dup
	* (PosePerson.kc(0, 0) > 0.8) /// .lag(0.001)
	// * (PosePerson.kgc(0, 0) > 0.8)
} +> \x1;
//:
{ SinOsc.ar(PosePerson.kx(0, 0).lag(2).linlin(0, 1, 400, 4000), 0, 0.1).dup } +> \x1;
//: amp control
{
	var freq, amp;
	freq = PosePerson.kx(0, 0).lag(2).linlin(0, 1, 400, 4000);
	amp =  PosePerson.ky(0, 0).lag(0.1).linexp(0, 1, 0.001, 0.5);
	SinOsc.ar(freq, 0, amp).dup
} +> \x1;
//: 2 points
{
	var nfreq, namp; // nose
	var freq2, amp2; // which point?
	nfreq = PosePerson.kx(0, 0).lag(2).linlin(0, 1, 400, 4000);
	namp =  PosePerson.ky(0, 0).lag(0.1).linexp(0, 1, 0.001, 0.5);
	freq2 = PosePerson.kx(0, 1).lag(2).linlin(0, 1, 400 * 0.2, 4000);
	amp2 =  PosePerson.ky(0, 1).lag(0.1).linexp(0, 1, 0.001, 0.5);
	[
		SinOsc.ar(nfreq, 0, namp),
		SinOsc.ar(freq2, 0, amp2),
	]
} +> \x1;
//: 2 points - left and right eye
{
	var nfreq, namp; // leye
	var freq2, amp2; // reye?
	nfreq = PosePerson.kx(0, 2).lag(0.02).linlin(0, 1, 400, 4000) * 0.8;
	namp =  PosePerson.ky(0, 2).lag(0.1).linexp(0, 1, 0.001, 0.5);
	freq2 = PosePerson.kx(0, 1).lag(0.02).linlin(0, 1, 400, 4000) * 1.2;
	amp2 =  PosePerson.ky(0, 1).lag(0.1).linexp(0, 1, 0.001, 0.5);
	[
		SinOsc.ar(nfreq, 0, namp) * (PosePerson.kc(0, 0) > 0.8),
		LFTri.ar(freq2, 0, amp2) * (PosePerson.kc(0, 2) > 0.8)
	]
} +> \x1;
//: 2 points - left and right ear
{
	var nfreq, namp; // nose
	var freq2, amp2; // which point?
	nfreq = PosePerson.kx(0, 4).lag(0.02).linlin(0, 1, 400, 4000) * 0.8;
	namp =  PosePerson.ky(0, 4).lag(0.1).linexp(0, 1, 0.001, 0.5);
	freq2 = PosePerson.kx(0, 3).lag(0.02).linlin(0, 1, 400, 4000) * 1.2;
	amp2 =  PosePerson.ky(0, 3).lag(0.1).linexp(0, 1, 0.001, 0.5);
	[
		SinOsc.ar(nfreq, 0, namp) * (PosePerson.kc(0, 4) > 0.8),
		LFTri.ar(freq2, 0, amp2) * (PosePerson.kc(0, 3) > 0.8)
	]
} +> \x1;
//: 2 points - left and right wrists
{
	var nfreq, namp; // nose
	var freq2, amp2; // which point?
	nfreq = PosePerson.kx(0, 10).lag(0.02).linlin(0, 1, 400, 4000) * 0.8;
	namp =  PosePerson.ky(0, 10).lag(0.1).linexp(0, 1, 0.001, 0.5);
	freq2 = PosePerson.kx(0, 9).lag(0.02).linlin(0, 1, 400, 4000) * 1.2;
	amp2 =  PosePerson.ky(0, 9).lag(0.1).linexp(0, 1, 0.001, 0.5);
	[
		SinOsc.ar(nfreq, 0, namp) * (PosePerson.kc(0, 10) > 0.8),
		LFTri.ar(freq2, 0, amp2) * (PosePerson.kc(0, 9) > 0.8)
	]
} +> \x1;
//: 2 points - left and right wrists
{
	var nfreq, namp; // nose
	var freq2, amp2; // which point?
	nfreq = PosePerson.kx(0, 10).lag(0.02).linlin(0, 1, 400, 4000) * 0.8;
	namp =  PosePerson.ky(0, 10).lag(0.1).linexp(0, 1, 0.1, 0.5);
	freq2 = PosePerson.kx(0, 9).lag(0.02).linlin(0, 1, 400, 4000) * 1.2;
	amp2 =  PosePerson.ky(0, 9).lag(0.1).linexp(0, 1, 0.1, 0.5);
	[
		SinOsc.ar(nfreq, 0, namp) * (PosePerson.kc(0, 10) > 0.8),
		LFTri.ar(freq2, 0, amp2) * (PosePerson.kc(0, 9) > 0.8)
	]
} +> \x1;
//: 2 points - left and right wrists - more silence
{
	var nfreq, namp; // nose
	var freq2, amp2; // which point?
	nfreq = PosePerson.kx(0, 10).lag(0.02).linlin(0, 1, 400, 4000) * 0.8;
	namp =  PosePerson.ky(0, 10).lag(0.1).linexp(0, 1, 0.001, 0.5);
	freq2 = PosePerson.kx(0, 9).lag(0.02).linlin(0, 1, 400, 4000) * 1.2;
	amp2 =  PosePerson.ky(0, 9).lag(0.1).linexp(0, 1, 0.001, 0.5);
	[
		SinOsc.ar(nfreq, 0, namp) * (PosePerson.kc(0, 10) > 0.8),
		LFTri.ar(freq2, 0, amp2) * (PosePerson.kc(0, 9) > 0.8)
	]
} +> \x1;
//: 4 points - left and right wrists + knees
{
	var lwfreq, lwamp; // left wrist
	var rwfreq, rwamp; // right wrist
	lwfreq = PosePerson.kx(0, 10).lag(0.02).linlin(0, 1, 400, 4000) * 0.8;
	lwamp =  PosePerson.ky(0, 10).lag(0.1).linexp(0, 1, 0.1, 0.5);
	rwfreq = PosePerson.kx(0, 9).lag(0.02).linlin(0, 1, 400, 4000) * 1.2;
	rwamp =  PosePerson.ky(0, 9).lag(0.1).linexp(0, 1, 0.1, 0.5);
	lkfreq = PosePerson.kx(0, 14).lag(0.02).linlin(0, 1, 400, 4000) * 0.8;
	lkamp =  PosePerson.ky(0, 14).lag(0.1).linexp(0, 1, 0.1, 0.5);
	rkfreq = PosePerson.kx(0, 13).lag(0.02).linlin(0, 1, 400, 4000) * 1.2;
	rkamp =  PosePerson.ky(0, 13).lag(0.1).linexp(0, 1, 0.1, 0.5);
	[
		SinOsc.ar(lwfreq, 0, lwamp) * (PosePerson.kc(0, 10) > 0.8)
		+ SinOsc.ar(rwfreq, 0, rwamp) * (PosePerson.kc(0, 14) > 0.8),
		LFTri.ar(rkfreq, 0, rkamp) * (PosePerson.kc(0, 9) > 0.8)
		+ LFTri.ar(lkfreq, 0, rkamp) * (PosePerson.kc(0, 13) > 0.8)
	]
} +> \x1;
//:
{
	var freq, amp;
	freq = PosePerson.kx(0, 0).lag(2).linlin(0, 1, 400, 4000);
	amp =  1 - PosePerson.ky(0, 0).lag(2);
	SinOsc.ar(freq, 0, amp).dup
} +> \x1;
//: