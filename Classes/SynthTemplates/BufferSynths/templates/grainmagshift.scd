/* Fri  7 Jul 2023 21:18
Grain + Magshift, with guards against clipping.
1. amp limited to 0.5
2. Limiter to level 0.5


*/
[Grain_, MagShift_];
//:
{   // provides PlayBuf.ar ugen with br controls
	var src, amp;
	amp = \amp.br(1) min: 0.5; // limit amplitude to 0.5
	src = Grain_.ar() +> MagShift_ * amp;
	Limiter.ar(src, level: 0.5); // dur: default: 0.01
}
