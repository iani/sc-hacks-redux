/* 21 Jun 2023 23:49
Asc to confirm before evaluating a function.

{ "Yes I did this".postln; }.confirm;
*/

+ Function {
	confirm { | string = "Do you really want to do this?" |
		var window;
		window = this.vlayout(
			TextView().string_(string),
			HLayout(
				Button().states_([["OK"]])
				.action_({
					// "I will really do this".postln;
					this.value;
					window.close;
				}),
				Button().states_([["CANCEL"]])
				.action_({
					"Action cancelled by user".postln;
					window.close;
				})
			)
		)
	}
}