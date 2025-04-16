/* Fri  7 Jul 2023 21:18
Experimental.
Grain + MagShift.
No limiter, no amp limitation: This synthfunc will clip easily!
Only safe for low amplitudes.
*/
[Grain_, MagShift_];
//:
{   // provides PlayBuf.ar ugen with br controls
	Grain_.ar() +> MagShift_ * \amp.br(1)
}
