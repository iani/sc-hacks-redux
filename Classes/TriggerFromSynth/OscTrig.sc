/* 27 Jan 2021 12:31
Holds an OSCfunc that responds to \tr messages and sends \trig 
messages to one or more listening objects.

Can use: 
- more than 1 osctrig for a single listener
- more than 1 listener per single osctrig
- Several synths sending trigs to one OscTrig

*/

OscTrig {
	var <id, <oscFunc, <envir;

	*new { | id |
		^this.newCopyArgs.init(id);
	}

	init { | argId |
		id = argId ?? { UniqueID.next };
		oscFunc = this.makeOscFunc;
		envir = Mediator().put(\release, 0);
	}

	makeOscFunc {
		oscFunc = OSCFunc({ | msg |
			"FIXME: MyLibrary is now broken after OscTrig:makeOscFunc triggers".postln;
			"TO START: INSERT DEBUG MESSAGES TO MultiLevelIdentityDictionary:put".postln;
			this.changed(\trig, *msg[2..])
		}, '/tr', argTemplate: [nil, id])
	}

	addListener { | listener, action |
		listener.addNotifier(this, \trig, action ?? { | ... args |		
			{ postf("% received % from %\n", listener, args, this) }
		});
	}

	addSynth { | source, key = \default |
		// FIXME: THIS INVALIDATES THE LIBRARY
		// AND BREAKS addTrig, via fromLib !!!!
		//		envir[key] = source.makeTrig.play(args: [id: id]);
		"ADD SYNTH NEEDS DEBUGGING !!!!!!!".postln;
		"THE TRIGGER EMPTIES OscTrig's path in MyLibrary".postln;
		envir.postln;
		envir[key] = source.makeTrig.play(args: [id: id]);
		envir.post; "  !!!!!!".postln;
	}

	free { // remove all listeners and deactivate OSCFunc
		oscFunc.free;
		this.objectClosed;
	}
}