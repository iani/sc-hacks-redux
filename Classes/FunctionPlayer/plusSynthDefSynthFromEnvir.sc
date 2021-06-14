/*  1 Jun 2021 09:56
Get arguments for synth message from currentEnvironment.
*/

+ SynthDef {
	synthInEnvir { | addAction = \addToHead |
		/* Send the synthdef to the server.
			Wait for the synthdef to be loaded on the server, then start the synth.
		*/
		var synth, target, server;
		"Hullo. This is synthInEnvir".postln;
		target = currentEnvironment[\target].asTarget;
		server = target.asServer;
		synth = Synth.newCopyArgs.defName_(name).target_(target).server_(server);
		postf("the synth is: %\n", synth);
		if (server.serverRunning.not) {
			postf("% is not running. Cannot send synthdef %\n", server, this);
			^synth;
		};
		// TODO: sync async send stuff 
		synth.startInEnvir(addAction, this);
		^synth;
	}

	
}
