//Fri 23 Feb 2024 09:28
//SymbolOperators

+ Symbol {
	<+ { | value, envir | envir.envir.put(this, value); }
}