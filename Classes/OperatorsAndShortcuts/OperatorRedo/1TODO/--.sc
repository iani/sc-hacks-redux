//Fri 23 Feb 2024 12:10
//SymbolOperators

+ Symbol {
	-- { | envir, time = 1 |
		// Stop player from specified envir
		Mediator.at(envir).at(this).stop(time);
	}
}