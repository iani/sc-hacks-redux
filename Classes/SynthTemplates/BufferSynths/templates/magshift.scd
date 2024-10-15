/* Fri  7 Jul 2023 21:18
Experimental.
*/
[PhaseBuf_, MagShift_];
//:
{   // provides PlayBuf.ar ugen with br controls
	PhaseBuf_.ar() +> MagShift_ * \amp.br(1)
}
