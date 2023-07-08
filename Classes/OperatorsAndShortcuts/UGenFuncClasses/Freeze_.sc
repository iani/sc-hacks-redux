/* Fri  7 Jul 2023 21:15

freeze > 0 : freeze bins

*/

Freeze_ : UGenFunc {
	*ar { | in |
		var chain;
		chain = FFT(LocalBuf(2048), in);
		chain = PV_Freeze(chain, \freeze.br(0));
		^IFFT(chain);
	}
}