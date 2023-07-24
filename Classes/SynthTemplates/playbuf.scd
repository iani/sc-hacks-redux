/*  Tue  4 Jul 2023 08:58
Testing Basic playfunc from shourtcut class.
Early prototype for settings:
The settings could be names of Classes that contain specs, such as:
PlayBuf_, PV_Magabove_ etc.  See folder
*/
[PlayBuf_]
//:
{   // provides PlayBuf.ar ugen with br controls
	PlayBuf_.ar() * \amp.br(1)
}
