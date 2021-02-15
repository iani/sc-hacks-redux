/* 14 Feb 2021 14:00
Inheriting from SimpleController. 
Adding methods for adding/removing messages and actions.

Initialize actions at creation. This avoids having to check for nil
later each time when actions are used.

More work is needed: 
- The values of actions should be Sets (or Arrays) of Notifier.
- update should iterate over actions. 


*/

NotificationController : SimpleController {

	init {
		super.init;
		actions = IdentityDictionary.new(4);
		model.addDependant(this);
	}

	add { | message, listener, action |
		this.remove(message, listener); // remove previous action if present
		actions[message] = actions[message] add: action;
	}

	remove { | message, listener |
		(actions[message] ? []) remove: this.get(message, listener);
	}

	get { | message, listener |
		^actions[message] detect: { | n | n.listener === listener }
	}
	
	update { arg theChanger, what ... moreArgs;
		actions.at(what) /* .copy */ do: { | n |
			n.update(theChanger, what, *moreArgs);
		}
	}
}
