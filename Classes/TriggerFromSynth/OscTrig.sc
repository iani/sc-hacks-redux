/* 27 Jan 2021 12:31
Holds an OSCfunc that responds to \tr messages and sends \trig 
messages to one or more listening objects.

TODO: Figure out how to handle both:
- more than 1 osctrig for a single listener
- more than 1 listener per single osctrig

Probably implement this using some extended technique along
new methods putLib / atLib, which allows putting/managing a set objects in lib
under path: ClassOfObjectStored, objectThatStores.  

*/

OscTrig {
	var id, oscfunc, listeners;

	*new {
		
		
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
}