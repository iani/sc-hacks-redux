/* 10 Nov 2022 14:30
A scope for viewing any named bus in the system.
*/

ControlBusScope {
	var <busnum = 0, <busname, <envirname, <xzoom = 1, <yzoom = 1;
	init {
		// "ControlBusScope init does nothing".postln;
	}

	*gui { | key = \default |
		var self;
		self =this.fromLib(key);
		self.hlayout(
			[VLayout(
				[ListView(), stretch: 1],
				[ListView(), stretch: 6]
			), stretch: 1],
			[
				ScopeView()
				.server_(Server.default)
				.bufnum_(0)
				.addNotifier(self, \setbus, { | n, bufnum |
					n.listener.bufnum = bufnum;
				}),
				stretch: 5
			]
		)
	}
}