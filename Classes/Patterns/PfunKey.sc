/*  4 Dec 2022 09:26
apply a function to the key accessed, and return the result
*/



PfunKey : Pfunc  {

	asStream {
		^FunKeyStream.new(nextFunc, resetFunc)
	}
}

FunKeyStream : FuncStream {
	next { arg inval;
		postln("FunKeyStream inval:" + inval);
		^envir.use({ nextFunc.value(inval) })
	}
}