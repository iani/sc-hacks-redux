/* 14 Feb 2021 14:00
Slightly changed version of SimpleController.
Initialize actions at creation. This avoids having to check for nil
later each time when actions are used.
*/

SimpleController2 {
	var model, actions;
	// responds to updates of a model

	*new { arg model;
		^super.newCopyArgs(model).init
	}
	init {
		actions = IdentityDictionary.new(4);
		model.addDependant(this);
	}
	put { arg what, action;  actions.put(what, action); }

	update { arg theChanger, what ... moreArgs;
		var action;
		action = actions.at(what);
		if (action.notNil, {
			action.valueArray(theChanger, what, moreArgs);
		});
	}

	remove { model.removeDependant(this); }
	removeAt{ |what|  actions.removeAt(what); }
}
