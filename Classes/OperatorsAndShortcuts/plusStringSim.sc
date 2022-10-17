/* 17 Oct 2022 19:21
Small temporary utility for testing activation / deactivation
of code evaluation in OscGroups.
*/

+ String {
	sim {
		NetAddr.localAddr.sendMsg(\code, this);
	}
}