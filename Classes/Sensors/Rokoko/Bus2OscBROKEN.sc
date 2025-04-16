//: Play back an array of control busses by polling

Bus2OscBROKEN : Singleton {
	var <name = \b2o;
	var <buses, <>oscmessage = '/rokoko/', <>pollrate = 30, <synth;
	var <>sendaddress;
	// var <id; // no need to use this?

	*named { | name = \b2o, buses = #[0], oscmessage = '/rokoko/', pollrate = 30, sendaddress |
		^super.named(name, buses, oscmessage, pollrate);
	}

	init { | argName = \b2o, argBuses = #[0], argOscmessage = \rokoko, argPollrate = 0.03, argSendaddress |
		name = argName.asSymbol;
		buses = argBuses;
		oscmessage = argOscmessage;
		pollrate = argPollrate;
		sendaddress = argSendaddress ?? { OscGroups.defaultSendAddress };
		this.makeResponder;
		SynthDef("help-SendTrig",{ | trate = 3 |
			var trig, src;
			// src = SinOsc.kr([1, 1, 1], 0, 0.1);
			src = [In.kr(0), In.kr(1), In.kr(2)];
			trig = Impulse.kr(trate);
			// SendTrig.kr(Impulse.kr(2), 0, src);
			SendReply.kr(trig, \tretet1231231, src, -1);
		}).add;
	}

	makeResponder {
		\testing123.addAction({ (degree: (0..10).choose).play }, \blah);
	}
	makeResponder2 {
		\tretet1231231.addAction({ | n, msg |
			(degree: (0..7).choose, dur: 0.1).play;
			sendaddress.sendMsg(oscmessage, *msg[3..]);
		}, name);

	}

	start { this.play; }
	play {
		NetAddr.localAddr.sendMsg(\testing123);
	}
	play2 {
		CmdPeriod add: this;
		if (synth.notNil) { ^postln("poller" + name + "is playing"); };
		synth = { | trate = 3 |
			var trig, src;
			// src = SinOsc.kr([1, 1, 1], 0, 0.1);
			src = [In.kr(0), In.kr(1), In.kr(2)];
			trig = Impulse.kr(trate);
			// SendTrig.kr(Impulse.kr(2), 0, src);
			SendReply.kr(trig, \tretet1231231, src, -1);
		};
		// synth = Synth("help-SendTrig");
		// synth = { | trate = 30 |
		// 	var trig, sig;
		// 	trig = Impulse.kr(trate);
		// 	sig = buses collect: { | b | In.kr(b) };
		// 	SendReply.kr(trig, name, sig, -1)
		// }.play(args: [trate: pollrate]);
	}

	stop {
		synth.free;
		synth = nil;
	}

	doOnCmdPeriod { synth = nil }
}