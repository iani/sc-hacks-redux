//V2 revisit on Tue 25 Feb 2025 14:52

// Event !> should restart
// Event +> should modify.
// Note: In the case of Event and EventStream:
// If an EventStream is already playing, it should *not*
// be restarted.  It should be modified instead.
// This is to avoid inadvertently restarting EventStreams
// when copy-pasting a line previously addressing the
// same player in order to modify it.


+ Event {
	!> { | player, envir |
		// force start playing new Event as EventStream
		// transferred here from +> on Wed 26 Feb 2025 10:03
		^this.pushPlayInEnvir(player, envir ? player, true)
	}
}

+ EventStream {
	!> { | player, envir |
		^this.pushPlayInEnvir(player, envir ? player, true) }
}

/*
+ Symbol {
	// Sat 11 Nov 2023 08:18 - cancel !+> --- too cumbersome.
	// Mon 13 Nov 2023 22:29: Substitute for earlier !+> or +>
	++> { | param, envir |
		envir.envir.put(param, this);
	}
}
*/