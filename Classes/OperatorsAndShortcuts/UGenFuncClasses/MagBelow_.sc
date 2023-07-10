/*  4 Jul 2023 20:07
tested
*/

MagBelow_ : UGenFunc {
	*ar { | in |
		var chain;
		chain = FFT(LocalBuf(2048), in);
		chain = PV_MagBelow(chain, \thresh.br(~thresh ? 100));
		^IFFT(chain);
	}
}