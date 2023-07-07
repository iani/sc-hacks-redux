/*  4 Jul 2023 20:07
Experimental - for testing
*/

MagAbove_ : UGenFunc {
	*ar { | in |
		var chain;
		chain = FFT(LocalBuf(2048), in);
		chain = PV_MagAbove(chain, \thresh.br(310));
		^0.1 * IFFT(chain);
	}
}