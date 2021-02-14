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
	}

	// TODO: add notifier!
	put { arg what, action;  actions.put(what, action); }

	update { arg theChanger, what ... moreArgs;
		actions.at(what) do: { | n |
			n.update(theChanger, what, moreArgs); // *moreArgs ?????
		}
	}
	
	remove { model.removeDependant(this); }

	// TODO: remove notifier!
	removeAt{ | what |  actions.removeAt(what); }
}
