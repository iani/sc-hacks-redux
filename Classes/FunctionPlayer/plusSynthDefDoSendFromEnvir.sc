/*  1 Jun 2021 09:56
Get arguments for synth message from currentEnvironment.
*/

+ SynthDef {
	synthFromEnvir {
		var server, target, synth;
		"hullo".postln;
		target = ~target.asTarget;
		server = target.server;
		postf("the server is: %\n", server);
		synth = Synth.basicNew(name, server);
		postf("the synth is: %\n", synth);
		if (server.serverRunning.not) {
			("Server '" ++ server.name ++ "' not running. Cannot start synth").warn;
		};
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