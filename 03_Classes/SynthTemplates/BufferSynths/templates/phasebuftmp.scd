/*  Tue  4 Jul 2023 08:58
	Thu 17 Aug 2023 13:14 - test provision of amp by SynthTemplate
Testing Basic playfunc from shourtcut class.
Early prototype for settings:
The settings could be names of Classes that contain specs, such as:
PlayBuf_, PV_Magabove_ etc.  See folder
*/
[PhaseBuf_];
//:
{   // provides PlayBuf.ar ugen with br controls
	// "DEBUGGING: This is PhaseBuf_".postln;
	PhaseBuf_.ar() // * \amp.br(1) // amp control is now provided by SynthTemplate
}
