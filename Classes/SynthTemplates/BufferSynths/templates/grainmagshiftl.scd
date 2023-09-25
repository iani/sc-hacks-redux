/*
Grain + Magshift, with guards against clipping.

This here is now identical to grainmagshift.
The old version is kept in grainmagshift0.

1. amp limited to 0.5
2. Limiter limited to level 0.5

*/
[Grain_, MagShift_];
//:
{   // provides PlayBuf.ar ugen with br controls
	var src, amp;
	amp = \amp.br(1) min: 0.5; // limit amplitude to 0.5
	src = Grain_.ar() +> MagShift_ * amp;
	Limiter.ar(src, level: 0.5); // dur: default: 0.01
}
