// 木 15  5 2025 10:23
// Do an action when receiving an input different from the latest previously received input.

OnChange {
	var <>action, <previous;

	*new { | action | ^this.newCopyArgs(action) }

	// check if new input is not equal to previous
	// if yes, then perform action and store new input
	check { | input |
		postln("OnChange compares previous" + previous + "with new input" + input);
		postln("input != previous???" + (input != previous));
		if (input != previous) {
			postln("OnChange runs action with input:" + input);
			action.(input);
			previous = input;
		}
	}
}