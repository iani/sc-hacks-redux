/* Fri  7 Jul 2023 21:18
Experimental. for testing non-buffersynth ugenfuncs
*/
[PhaseBuf_, MagShift_];
//:
{   // provides PlayBuf.ar ugen with br controls
	PhaseBuf_.ar() +> MagShift_ * \amp.br(1);
	SinOsc.ar(400, 0, 0.1).dup;
}
