/* 18 Aug 2023 09:04
First test of a plain synth.
*/
[\freq, \pan];
//:
{
	SinOsc.ar(\freq.br(~freq ? 400)).pan // * \amp.br(1) // amp control is now provided by SynthTemplate
}
