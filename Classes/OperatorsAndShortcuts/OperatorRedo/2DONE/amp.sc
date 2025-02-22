//Fri 23 Feb 2024 12:09
//SymbolOperators

+ Symbol {
	@ { | envir | ^this.at(envir) }
	at { | envir | // has same effect as Symbol:player below?
		^Mediator.at(envir ? currentEnvironment.name).at(this);
	}
}