/* 27 Jan 2021 12:31
Holds an OSCfunc that responds to \tr messages and sends \trig 
messages to one or more listening objects.

Can use: 
- more than 1 osctrig for a single listener
- more than 1 listener per single osctrig
- Several synths sending trigs to one OscTrig

*/

OscTrig {
	var <id, <oscfunc, <listeners, <envir;

	*new { | id = 0 |
		^this.newCopyArgs.init;
	}

	init {
		oscFunc = this.makeOscFunc(id);
		listeners = Set();
		envir = Mediator().put(\id, id);
	}

	add { | listener |

	}

	remove { | listener |

	}

	free { // remove all listeners and deactivate OSCFunc
		oscfunc.free;
		listeners do: { | l | l.removeDependant(this) };
		listeners = nil;
	}

	addSource { | key, source |
		/*  Note 30 Jan 2021 17:59
			We need a new class that converts the source to a synth,
			using envir as Mediator-environment to use id as parameter.
		*/
	}

	removeSource { | key |
			
	}

	
}