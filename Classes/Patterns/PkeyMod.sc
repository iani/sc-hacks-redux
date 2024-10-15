/*  4 Dec 2022 11:22
Remove this when new PkeyFunc class is done
*/
/*
+ Pkey {
	asStream {
		var	keystream = key.asStream;
		// avoid creating a routine
		var stream = FuncStream({ |inevent|
			postln("Pkey stream inevent is:" + inevent);
			inevent !? { inevent[keystream.next(inevent)] } });
		^if(repeats.isNil) { stream } { stream.fin(repeats) }
	}
}

*/