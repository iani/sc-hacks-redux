// 水 16  4 2025 16:12
// Read data from a recorded Osc-Rokoko session.
// Store the data internally for processing in various ways.

RokokoData : Singleton {
	var <name; // name of Rokoko instance
	var <oscdata; // oscdata instance
	var <routine; // playback routine
	*atDate { | postfix = \rokoko, date |
		date ?? { date = Date.localtime.format("%y%m%d"); };
		^this.named((date ++ postfix).asSymbol);
	}

	init {
		Paths.doGetPath({ | p, paths |
			oscdata = OscData(paths);
		}, name)
	}

	gui { oscdata.gui; }
	data { ^oscdata.parsedEntries }
	times { ^this.data.flop.first }
	entries { ^this.data.flop[1] }
	controlValues {
		^this.entries collect: { | e |
			Rokoko.getControlValues(e.interpret)
		};
	}

	splay { | outbus = 0 |
		this.play(outbus, true);
	}

	play { | outbus = 0, scope = false |
		CmdPeriod.add(this);
		if (routine.notNil) { ^"Already playing".postln; };
		if (scope) { Server.default.scope(20, rate: \control) };
		routine = {
			var bus;
			bus = Bus(\control, outbus, Actor.numControls, Server.default);
			this.controlValues do: { | cv |
				// cv.postln;
				// bus.postln;
				bus.setn(cv);
				30.reciprocal.wait;
			};
		}.fork;
	}

	stop {
		routine.stop;
		routine = nil;
	}

	doOnCmdPeriod { this.stop; }
}