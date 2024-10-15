/*  7 Jul 2023 12:52
Experimental.
*/
[PhaseBuf_, MagAbove_];
//:
{   // provides PlayBuf.ar ugen with br controls
	PhaseBuf_.ar() +> MagAbove_ * \amp.br(1)
}
