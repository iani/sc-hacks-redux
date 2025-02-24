/*  7 Jul 2023 21:04
Utility for testing various PV UGens

in is the audio source to process.
pvfunc is the ugen producing function and takes the form:
{ | chain | <PV_UGEN_HERE>(chain, CONTROLS HERE) }
*/

PV_Plug {
	*ar { | in, pvfunc |
		var chain;
		chain = FFT(LocalBuf(2048), in);
		chain = pvfunc.(chain);
		^IFFT(chain);
	}
}