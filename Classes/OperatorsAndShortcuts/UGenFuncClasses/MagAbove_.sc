/*  4 Jul 2023 20:07
tested
*/

MagAbove_ : UGenFunc {
	*ar { | in |
		var chain;
		chain = FFT(LocalBuf(2048), in);
		chain = PV_MagAbove(chain, \thresh.br(~thresh ? 1));
		^IFFT(chain);
	}
}