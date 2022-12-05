/* 24 May 2021 15:59
For using OSCGroups:
Make Function:play work even if server is not running.
If server not running, return dummy synth to store in Mediator.

For sc-hacks-redux: playInEnvir.  Create synth, providing arguments from currentEnvironment.

*/

+ Function {
	play { arg target, outbus = 0, fadeTime = 0.02, addAction=\addToHead, args, player, envir;
		var def, synth, server, bytes, synthMsg;
		// TODO: get outbus + targed from envir if provided!
		// currentEnvironment.postln;
		// postln("Function:play current environment is target:" + currentEnvironment);
		// postln("Function:play  target is:" + ~target);

		target ?? {  target = ~target; };
		target = target.asTarget;
		~outbus !? { outbus = ~outbus };
		outbus = outbus.asAudioBus;
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
		Function.changed(\player, envir, player, Main.elapsedTime,
			format("% +>.% %", this.def.sourceCode, envir, player.asCompileString),
			[def.allControlNames collect: _.name]
		);

		synth = Synth.basicNew(def.name, server);
		// if notifications are enabled on the server,
		// use the n_end signal to remove the temp synthdef
		if (server.notified) {
			OSCFunc({
				server.sendMsg(\d_free, def.name);
			}, '/n_end', server.addr, argTemplate: [synth.nodeID]).oneShot;
		};
		// postln("Function:play will use this as target:" + target);
		synthMsg = synth.newMsg(target,
			[\i_out, outbus, \out, outbus] ++ args, addAction
		);
		def.doSend(server, synthMsg);
		^synth
	}
}