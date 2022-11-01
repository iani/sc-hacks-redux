// 25 Jan 2021 10:29. Some useful event methods

+ Event {
	asEventStream { // 21 Jun 2022 09:21 : suggested by T.M.
		^EventStream(this)
	}

	osc { | ... keys |
		// construct a play function that sends osc
		this[\addr] ?? { this[\addr] = LocalAddr() };
		this[\msg] ?? { this[\msg] = \osc; };
		this[\play] = {
			~addr.sendMsg(~msg, *(keys.collect({ | f | currentEnvironment[f].value })))
		};
	}
}