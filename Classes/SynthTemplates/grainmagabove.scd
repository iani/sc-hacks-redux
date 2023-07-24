/*  7 Jul 2023 12:52
Experimental.
*/
[Grain_, MagAbove_];
//:
{   // provides PlayBuf.ar ugen with br controls
	Grain_.ar() +> MagAbove_ * \amp.br(1)
}
