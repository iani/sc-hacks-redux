//火  6  5 2025 01:04
//A Stack that stores Actions
//When pushing, it merges earlier settings to the latest Action.

ActionStack : Stack {

	push { | action |
		var top;
		top = this.top;
		// top !? { action mergeEnvirs: top; };
		super push: action;
	}
}