/* 14 Feb 2021 14:00
*/

NotificationController : SimpleController {

	init {
		super.init;
		actions = IdentityDictionary.new(4);
		model.addDependant(this);
	}

	model { ^model }
	actions { ^actions }
	messages { ^actions.keys }
	notifications { ^actions.values.flat }
	listeners { ^actions.values.flat collect: _.listener }
	
	add { | message, listener, action |
		this.remove(message, listener); // remove previous action if present
		actions[message] = actions[message] add: action;
		// This is needed if model.releaseDependants has been called:
		model.addDependant(this);
	}

	remove { | message, listener |
		(actions[message] ? []) remove: this.get(message, listener);
	}

	get { | message, listener |
		^actions[message] detect: { | n | n.listener === listener }
	}
	
	update { arg theChanger, what ... moreArgs;
		actions.at(what).copy do: { | n | // copy: works when deleting
			n.update(theChanger, what, *moreArgs);
		}
	}

	removeListener { | listener |
		actions.copy keysValuesDo: { | mes, lis |
			if (lis === listener) { this.remove(mes, lis) }
		}
	}

	free {
		model.removeDependant(this);
		model = nil;
		actions = nil;
	}
}
