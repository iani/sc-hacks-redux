/* Fri  8 Sep 2023 15:38

wipe ranges????
? < wipe: < ??

*/

BrickWall_ : UGenFunc {
	*ar { | in |
		var chain;
		chain = FFT(LocalBuf(2048), in);
		chain = PV_BrickWall(chain, \wipe.br(~wipe ? 1));
		^IFFT(chain).pan;
	}
}