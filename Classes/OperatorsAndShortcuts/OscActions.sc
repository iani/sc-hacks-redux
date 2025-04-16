/* 23 Feb 2022 12:26
Shortcuts for actions to be added to osc with |>| or similar.
*/

TestAction {
	valueArray { | n, msg, time, addr, port |
		postln("running valueArray. msg " + msg + "\n time " + time + " addr " + addr + " port " + port);
	}
}

SetBus {
	var index, busname, bus;

	*new { | index = 0, busname = \bus |
		^this.newCopyArgs(index + 1, busname).init;
	}

	init { bus = busname.bus; }

	valueArray { | n, msg, time, addr, port |
		bus.set(msg[index]);
	}
}