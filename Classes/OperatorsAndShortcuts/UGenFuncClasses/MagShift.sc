/* Fri  7 Jul 2023 21:15

0 < stretch: < (10?)

shift by frequency cycles - or bins????
Tried out vaoues: -400, +400
*/

MagShift_ : UGenFunc {
	*ar { | in |
		var chain;
		chain = FFT(LocalBuf(2048), in);
		chain = PV_MagShift(chain, \stretch.br(~stretch ? 1), \shift.br(~shift ? 0));
		^IFFT(chain);
	}
}