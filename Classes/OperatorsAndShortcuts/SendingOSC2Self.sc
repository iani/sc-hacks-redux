/* 17 Oct 2022 19:21
Small temporary utility for testing activation / deactivation
of code evaluation in OscGroups.
*/

+ String {
	sim {
		LocalAddr().sendMsg(\code, this);
	}

	stamp {
		LocalAddr().sendMsg(\stamp, this + Date.localtime.stamp)
	}
}

+ Array {
	sim {
		LocalAddr().sendMsg(*this);
	}
}

+ Symbol {
	sim {
		LocalAddr().sendMsg(this);
	}
}
