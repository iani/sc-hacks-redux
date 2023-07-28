/* Fri  7 Jul 2023 21:18
Experimental.
*/
[PhaseBuf_, Freeze_];
//:
{   // provides PlayBuf.ar ugen with br controls
	"DEBUGGING: This is phasebuf - Freeze".postln;
	PhaseBuf_.ar() +> Freeze_ * \amp.br(1)
}
