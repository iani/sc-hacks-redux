/* 24 Oct 2022 11:17

Returned by OscDataReader:play.

(Could become a method of OscDataReader.)

*/

OscDataPlayer {
	var <>data, <>addr;
	var times, snippets;

	*new { | data, addr |
		^super.new.init(data, addr);
	}

	init { | argData, argAddr |
		data = argData;
		addr = argAddr ?? { NetAddr.localAddr; };
	}

	play { // building this step-by-step
		this.collectTimes.postln;
	}

	collectTimes {
		times, snippets = data.flop;
		times.postln;
		"============================================================".postln;
		times.differentiate.postln;
	}

	playPrototype { | player = \oscdata, envir = \oscdata |
		// finish this later.
		/*
		(
			dur: this.collectTimes.pseq(repeats),
			snippet: snippets.pseq(repeats),
			play: { var snippet;
				snippet = ~snippet;
				envir use: {  snippet.interpretAndShare }
			}
		).playInEnvir(player, envir);
		*/
	}
}