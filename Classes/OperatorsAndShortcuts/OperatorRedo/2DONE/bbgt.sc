//Fri 23 Feb 2024 12:02
//SymbolOperators

+ Symbol {
	!!> { | value, variable |
		this use: {
			currentEnvironment[variable] = value;
		}
	}
}