/* 24 May 2021 15:59
For using OSCGroups:
Make Function:play work even if server is not running.
If server not running, return dummy synth to store in Mediator.

For sc-hacks-redux: playInEnvir.  Create synth, providing arguments from currentEnvironment.

*/

+ Function {
	play { arg target, outbus = 0, fadeTime = 0.02, addAction=\addToHead, args;
		var def, synth, server, bytes, synthMsg;
		target = target.asTarget;
		server = target.server;
		if(server.serverRunning.not) {
			("server '" ++ server.name ++ "' not running.").warn;
			// make this work with sc-hacks-redux
			^Synth.basicNew(def.name, server);
			// ^nil
		};
		def = this.asSynthDef(
			fadeTime:fadeTime,
			name: SystemSynthDefs.generateTempName
		);
		synth = Synth.basicNew(def.name, server);
		// if notifications are enabled on the server,
		// use the n_end signal to remove the temp synthdef
		if (server.notified) {
			OSCFunc({
				server.sendMsg(\d_free, def.name);
			}, '/n_end', server.addr, argTemplate: [synth.nodeID]).oneShot;
		};
		synthMsg = synth.newMsg(target,
			[\i_out, outbus, \out, outbus] ++ args, addAction
		);
		def.doSend(server, synthMsg);
		^synth
	}

	playInEnvir { | name |
		^this.asSynthDef(fadeTime: ~fadeTime, name: name).synthFromEnvir;
	}
}