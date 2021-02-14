/* 14 Feb 2021 22:31
Notifier using SimpleController to optimize speed of lookup.
The actual performance gain may be negligible.
This is just an exercise to prove the feasibility of the concept.

Interface and steps for implementation: 

listener.addNotifier(notifier, message, action):

	-> Notifier2(notifier, message, listener, action) [NEW INSTANCE !!!!!]
	-> Add the instance to the corresponding controller in controllers
	-> If a previous instance exists, replace it (this just replaces the action)
	-> Add the corresponding controller as dependant to notifier

*/

Notifier2 {
	classvar <controllers; /* a controller holds a dictionary of 
		messages and for each message a set of Notifier2 instances		
	*/
	var <notifier, <message, <listener, <action;

	*initClass { controllers = IdentityDictionary(); }

	*new { | notifier, message, listener, action |
		postf("debugging notifier2 new. action is: %\n", action);
		^super.newCopyArgs(notifier, message, listener, action);
	}

	// respond to changed messages. Note: 'this' contains notifier and listener
	update { | sender, argMessage ... args |
		postf("Debugging. Notifier2, action is: %\n", action);
		 action.valueArray(this, *args) 
	}

	add { // add self to actions of controller - replacing older instance if needed.
		// If needed, create NotificationController and adds it as dependent!
		var controller;
		controller = controllers[notifier];
		controller ?? { // make new controller if needed
			// this also adds the controller as dependant to the notifier
			controller = NotificationController(notifier);
			controllers[notifier] = controller;
		};
		controller.add(message, listener, this);
		
	}

	*get { | notifier, message, listener |
		^controllers.at(notifier).at(message, listener);
	}

	remove { this.class.remove(notifier, message, listener); }

	*remove { | notifier, message, listener |		
		controllers.removeAtPath([notifier, message, listener]);
	}

	*controllersOf { | object |
		// a notifier of object is a Notifier containing object as listener!
		^controllers.leaves.flat.select({ | n | n.listener === object })
	}

	*listenersOf { | object |
		// a listener of object is a Notifier containing object as notifier!
		^controllers.leaves.flat.select({ | n | n.notifier === object })
	}
	
	*removeNotifiersOf { | object | this.notifiersOf(object) do: _.remove; }

	*removeListenersOf { | object | this.listenersOf(object) do: _.remove; }
}