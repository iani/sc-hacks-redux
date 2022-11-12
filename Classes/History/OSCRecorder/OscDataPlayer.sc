/* 24 Oct 2022 11:17


*/

OscDataPlayer {
	classvar <>enableCodeEvaluation = true;
	var <data, <>addr;
	var <times, <messages;

	*new { | data, addr |
		^super.newCopyArgs(data, addr).init;
	}

	init {
		addr ?? { addr = NetAddr.localAddr; };
		this.collectTimes;
	}

	collectTimes {
		#times, messages = data.flop;
		times = times.differentiate;
		times[0] = times[1];
		times = times rotate: -1;
	}

	play { | repeats = 1, player = \oscdata, envir = \oscdata, playEvent |
		if (enableCodeEvaluation) { OscGroups.enableCodeEvaluation; };
		/*
		(
			dur: times.pseq(repeats),
			message: messages.pseq(repeats),
			play: { addr.sendMsg(*~message) }
		).playInEnvir(player, envir);
		*/
		playEvent ?? { playEvent = this.makePlayEvent(nil, repeats) };
		playEvent.playInEnvir(player, envir)
	}

	playSelect { | selectMessages, repeats = 1, player = \oscdata, envir = \oscdata |
		this.play(repeats, player, envir,
			this.makePlayEvent({ | argAddr |
				{
					var theMessage;
					theMessage = ~message;
					if (selectMessages includes: theMessage[0]) {
						argAddr.sendMsg(*theMessage)
					}{
						// postln("filtered message: ------- " + theMessage[0]);
					}
				}
			}, repeats)
		);
	}

	playReject { | rejectMessages, repeats = 1, player = \oscdata, envir = \oscdata |
		this.play(repeats, player, envir,
			this.makePlayEvent({ | argAddr |
				{
					var theMessage;
					theMessage = ~message;
					if (rejectMessages includes: theMessage[0]) {
						// postln("filtered message: ------- " + theMessage[0]);
					}{
						argAddr.sendMsg(*theMessage)
					}
				}
			}, repeats)
		);
	}
	// customize event play function for playSelect, playReject
	makePlayEvent { | playFunc, repeats = 1 |
		^(
			dur: times.pseq(repeats),
			message: messages.pseq(repeats),
			play: this.makePlayFunc(playFunc)
		)
	}

	makePlayFunc { | playFunc |
		playFunc ?? { playFunc = { | argAddr | { argAddr.sendMsg(*~message) } } };
		^playFunc.(addr ?? { LocalAddr() }); // make addr known to playfunc
	}
}