/*  7 Jul 2023 12:52
GrainFroth with guards against clipping:
1. amp limited to 0.5
2. Limiter to level 0.5
	to prevent clipping
*/
[GrainFroth_];
//:
{   // provides PlayBuf.ar ugen with br controls
	var src, amp, stereoout, l, r;
	amp = \amp.br(1) min: 0.5; // tried: 0.7
	src = GrainFroth_.ar() * amp; // tried: level 0.7:
	stereoout = Limiter.ar(in: src, level: 0.5); // dur: 0.01 i.e. default
	// see: https://doc.sccode.org/Classes/Limiter.html
	stereoout.size.postln;
	#l, r = stereoout;
	l = PanAz.ar(4, l, \lpos.br(0));
	r = PanAz.ar(4, r, \rpos.br(0));
	[l, r].flat;
}
