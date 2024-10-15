/*  Tue  4 Jul 2023 08:58
Experimental: play for very long time, looping.
PlayBuf_, PV_Magabove_ etc.  See folder
*/
[PlayBuf_];
//:
{   // provides PlayBuf.ar ugen with br controls
	~dur = 2 ** 100;
	~loop = 1;
	// ~rate = 0.25;
	PlayBuf_.ar() * \amp.br(1)
}
