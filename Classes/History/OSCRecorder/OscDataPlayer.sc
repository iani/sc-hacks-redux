/* 24 Oct 2022 11:17


*/

OscDataPlayer {
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

	play { | repeats = 1, player = \oscdata, envir = \oscdata,  enableCodeEvaluation = true |
		if (enableCodeEvaluation) { OscGroups.enableCodeEvaluation; };
		(
			dur: times.pseq(repeats),
			message: messages.pseq(repeats),
			play: { addr.sendMsg(*~message) }
		).playInEnvir(player, envir);
	}
}