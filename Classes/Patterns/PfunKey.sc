/*  4 Dec 2022 09:26
apply a function to the key accessed, and return the result
*/


PfunKey : Pkey  {

	asStream {
		var	keystream = key.asStream;
		var stream = FuncStream({ |inevent|
			postln("Pkey stream inevent is:" + inevent);
			inevent !? { inevent[keystream.next(inevent)] } });
		^if(repeats.isNil) { stream } { stream.fin(repeats) }
	}
}
