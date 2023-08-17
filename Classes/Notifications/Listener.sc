/* 23 Feb 2022 07:45
Add an action that runs at when the model runs \changed, independently of
the message appended to change.

You can add many Listeners to one model.

Listener(1);
Listener(1, { "yyyyxxxxx".postln; });
Listener(1, { "another action".postln; }, \anotherAction);
Listener remove: 1;
Listener.remove(1, \anotherAction);

1.changed(\something, 1, 2, 3);
1.dependants;

*/

Listener {
	var <model, <actions;

	*new { | model, action, key = \action |
		var new;
		new = model.dependants.detect({ | d | d.class === this });
		new ?? { new = this.newCopyArgs(model, IdentityDictionary()).init };
		^new.addAction(action, key);
	}

	init { model addDependant: this }

	addAction { | action, key = \action |
		actions[key] = action ?? {{ | ... args | args.postln }}
	}
	*remove { | model, key = \action |
		var remove;
		remove = model.dependants.detect({ | d | d isKindOf: this });
		remove !? {
			remove.actions[key] = nil;
			if (remove.actions.size == 0) { model removeDependant: remove };
		};
	}

	update { | ... args |
		actions do: { | action | action.(*args); }
	}
}