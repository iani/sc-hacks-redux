//Fri 23 Feb 2024 12:04
//SymbolOperators

+ Symbol {
	// Operate in the environment named by me:
	!!* { | funcName |
		^this evalLocalFunc: funcName;
	}
	evalLocalFunc { | funcName |
		^this use: { currentEnvironment[funcName].value };
	}
}