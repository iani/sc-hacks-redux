//Fri 23 Feb 2024 12:05
//SymbolOperators

+ Symbol {

	!!! { | func | ^this use: func }

	use { | func | // evaluate func in this Mediator
		// var envir, result;
		// postln("debugging symbol use");
		// envir = Mediator.at(this);
		// postln("Environment before eval func is:");
		// envir.postln;
		// result = Mediator.at(this) use: func;
		// postln("Environment after eval func is:");
		// postln(result);
		// ^result;
		^Mediator.at(this) use: func;
	}
}