/* 30 Jan 2023 07:29

*/

PollRoutine {
	var <model, <message, <>rate = 0.1;
	var <routine;

	*new { | model = \poll, message = \changed, rate = 0.1 |
		^Registry(model, message, { this.newCopyArgs(model, message) }).rate_(rate);
	}

	doOnCmdPeriod { { this.makeRoutine }.defer(0.1) }

	start {
		if (this.isRunning) { ^postln("Already running:" + this) };
		this.makeRoutine;
		CmdPeriod add: this;
	}

	isRunning { ^routine.notNil }

	makeRoutine {
		routine.stop; // Safety: prevent runaway double routine
		routine = {
			loop {
				model.changed(message);
				rate.wait;
			};
		}.fork;
	}

	stop {
		CmdPeriod remove: this;
		routine.stop;
		routine = nil;
	}

}