
Bus2Osc : Singleton {
	var <name = \b2o;
	var <buses, <>oscmessage = '/rokoko/', <>pollrate = 30, <synth;
	var <>sendaddress;
	var <synth;

	*named { | name = \b2o, buses = #[0], oscmessage = '/rokoko/', pollrate = 30, sendaddress |
		^super.named(name, buses, oscmessage, pollrate, sendaddress);
	}

	init { | argName = \b2o, argBuses = #[0], argOscmessage = \rokoko,
		argPollrate = 0.03, argSendaddress |
		if (name.asString[0] != $/) {
			name = ("/" ++ name.asString).asSymbol;
		};
		buses = argBuses;
		oscmessage = argOscmessage;
		pollrate = argPollrate;
		sendaddress = argSendaddress ?? { OscGroups.defaultSendAddress };
		name.addAction({ | n, msg |
			sendaddress.sendMsg(oscmessage, *msg[3..]);
		}, name);
	}

	start { this.play }
	play {
		CmdPeriod add: this;
		synth = { | trate = 30 |
			var trig, sig;
			trig = Impulse.kr(trate);
			sig = buses collect: { | b | In.kr(b) };
			SendReply.kr(trig, name, sig);
			Out.ar(0, Silent.ar);
		}.play(args: [trate: pollrate]);
	}
	stop {
		synth.free;
		synth = nil;
	}

	doOnCmdPeriod { synth = nil }

}