/*  Tue  4 Jul 2023 08:58
Experimental: Testing customization of duration of playbuf from within a playfunc script.
PlayBuf_, PV_Magabove_ etc.  See folder
*/
nil; // PlayFunc provides default
//:
{   // provides PlayBuf.ar ugen with br controls
	~dur = 0.5;
	PlayBuf_.ar()
}
