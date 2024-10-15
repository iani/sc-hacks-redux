/*  7 Jul 2023 12:52
Experimental.
*/
[PlayBuf_, MagAbove_];
//:
{   // provides PlayBuf.ar ugen with br controls
	PlayBuf_.ar() +> MagBelow_ * \amp.br(1)
}
