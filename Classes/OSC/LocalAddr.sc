/*  1 Nov 2022 12:00
Caching NetAddr.localAddr.
Unsure if this saves computation time.
*/

LocalAddr {
	classvar <localAddr;

	*new {
		if (localAddr.isNil) {
			^localAddr = NetAddr.localAddr;
		}{
			^localAddr
		}
	}
}