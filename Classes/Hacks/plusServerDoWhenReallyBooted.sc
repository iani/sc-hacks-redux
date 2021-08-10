//: 10 Aug 2021 13:28
/*
	Server.default.doWhenBooted only works for the first boot.
	It does not run again after the server is quit and booted again.

	ServerBoot works every time, but it cannot load buffers,
	it says server not running.

	Here we provide a workaround by nesting doWhenBooted inside ServerBoot.

	NOTE: A better mechanism for Server boot/quit actions should be coded.

*/

+ Server {
	doWhenReallyBooted { | action |
		ServerBoot.add({ this.doWhenBooted(action);}, this);
	}
}