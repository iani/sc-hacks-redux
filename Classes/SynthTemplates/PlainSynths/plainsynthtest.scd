/* 18 Aug 2023 09:04
First test of a plain synth.
*/
[\freq];
//:
{
	SinOsc.ar(\freq.br(400)).dup // * \amp.br(1) // amp control is now provided by SynthTemplate
}
