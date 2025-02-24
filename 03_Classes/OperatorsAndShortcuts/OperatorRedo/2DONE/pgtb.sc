//Fri 23 Feb 2024 09:27
//SymbolOperators
//EventOperators

+ Symbol {
	// Sat 11 Nov 2023 08:19  restore +> play in envir
	// See SymbolOperators.sc
	// +> { | player, envir |
	//  	^this.pushPlayInEnvir(player, envir);
    // }

	+>! { | player, envir |
		// osc message triggers play next event of an EventStream player
		this >>> {
			Mediator.wrap(
				{ currentEnvironment[player].playNext },
				envir ? \default
			)
		}
	}
}

+ Event {
	+>! { | player, envir | // do not start
		^this.playInEnvir(player, envir, false);
	}
}