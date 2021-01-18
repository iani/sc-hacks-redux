//: 18 Jan 2021 18:40 Redo of NEvent as NamedSingleton.
/* 
Experimental.
To be used with Player2.

Note: To test Event2 updating mechanism start like this: 

Event2.push;

currentEnvironment addDependant: { | ... args | postf ("args are: %\n", args) };

~a = 10;

*/

Event2 : NamedSingleton {
	var <event;

	prInit {
		event = EnvironmentRedirect();
		event.dispatch = Dispatch.newCopyArgs(
			event,
			// this, 
			() putPairs: [
				Integer, { | key, object |
					[key, object]
				},
				Float, { | key, object |
					[key, object]
				},
				Bus, { | key, object |
					[\mapBus, key, object]
				}
			]
		)
	}
	
	push {
		if (currentEnvironment === event) {} {
			// let listeners switch listening from old to new environment:
			event.changed(\oldEnvir, currentEnvironment); // GUIs remove old envir
			event.push;
			event.changed(\newEnvir, event); // dependent GUIs start listening to
			                                 // new envir updates
		}
	}

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << name << "[ " ;
		event.envir.printItemsOn(stream);
		stream << " ]" ;
	}
}
