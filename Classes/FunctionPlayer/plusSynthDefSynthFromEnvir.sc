/*  1 Jun 2021 09:56
Get arguments for synth message from currentEnvironment.
*/

+ SynthDef {
	synthFromEnvir {
		var synth;
		"Hullo. This is synthFromEnvir".postln;
		synth = Synth.newCopyArgs.defName_(name);
		postf("the synth is: %\n", synth);
		//Now get the synth arg names from the desc of the def
		//	First get the desc
		postf("Checking the desc of the def: %\n", this.desc);
		synth.startInEnvir;
		^synth
	}
	/*
		server =  ~target.asTarget.server;
		if(server.serverRunning.not) {
			("server '" ++ server.name ++ "' not running.").warn;
		^Synth.basicNew(name, server);
		};
	*/
	
	/*
		desc = this.asSynthDesc(\global, keepDef: true);
		synth = Synth.basicNew(def.name, server);
		synthMsg = synth.newMsg(
			target,
			[\i_out, outbus, \out, outbus] ++ args,
			addAction
		);
	*/

}

