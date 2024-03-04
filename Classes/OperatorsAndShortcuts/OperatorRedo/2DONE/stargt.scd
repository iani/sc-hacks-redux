//Fri 23 Feb 2024 11:35
//ArrayOperators
//FunctionOperators
//SymbolOperators

+ Array {
	*> { | param, envir | // store in param of envir
		envir.envir.put(param, this);
	}
}

+ Function {
	*> { | player, envir | // play as routine
		this.routineInEnvir(player, envir);
	}
}

+ Symbol {
	// Pdefn
	*> { | value | ^this.pd(value) }
	pd { | value | ^Pdefn(this, value) }
}