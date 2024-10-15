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
		ServerBoot.add({ | server |
			// postf("server boot server is: %\n", server);
			server.doWhenBooted({
				// postf("dowhenbooted server is: %\n", server);
			action.(server)
			});
		}, this);
	}
	notifyWhenReallyBooted {
		this doWhenReallyBooted: { | server |
			postln("server" + server + "will notify \booted");
			server.changed(\booted)
		}
	}

	addBootAction { | func |
		// postln("adding notifier" + this + "to" + this);
		// this.addNotifier(this, \booted, {
		// 	postln("Yes. I booted. I will run:" + func);
		// })
		// this.addNotifier(this, \booted, func);
		// OscGroups.addNotifier(OscGroups, \cmdperiod, func);
		ServerTree.add(func, this);
		if (this.serverRunning) { func.value };
	}
}