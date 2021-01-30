/* 27 Jan 2021 12:31
Holds an OSCfunc that responds to \tr messages and sends \trig 
messages to one or more listening objects.

Can use: 
- more than 1 osctrig for a single listener
- more than 1 listener per single osctrig
- Several synths sending trigs to one OscTrig

*/

OscTrig {
	var <id, <oscfunc, <envir;

	*new { | id |
		^this.newCopyArgs.init(id);
	}

	init { | id |
		id ?? { id = UniqueID.next };
		oscFunc = this.makeOscFunc;
		envir = Mediator(); // .put(\id, id);
	}

	makeOscFunc {
		oscFunc = OSCFunc({ | msg |
			this.changed(\trig, id, msg[2])
		}, '/tr', argTemplate: [nil, id])
	}

	addSynth { | synthFunc, key = \default |
		envir[key] = synthFunc.play(args: [id: id]);
	}

	free { // remove all listeners and deactivate OSCFunc
		oscfunc.free;
		this.objectClosed;
	}
}