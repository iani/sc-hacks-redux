/* 14 Jun 2021 11:30
Get parameters and arguments from currentEnvironment.
*/

+ Synth {
	startInEnvir { | addAction = \addToHead, def |
		var synthMsg;
		def ?? { def = defName.asDef };
		if (server.serverRunning.not) {
			postf("% is not running. Cannot start %\n", server, this);
			^this;
		};
		/*
		synthMsg = this.newMsg(
			target,
			[\i_out, outbus, \out, outbus] ++ args,
			addAction
			
		*/
	}
}