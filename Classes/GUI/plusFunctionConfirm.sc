/* 21 Jun 2023 23:49
Asc to confirm before evaluating a function.

{ "Yes I did this".postln; }.confirm;
{}.userInput;
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
					"Action canceled by user".postln;
					window.close;
				})
			)
		)
	}

	inputText {  | default = "something", prompt = "Enter a new string" |
		var window;
		window = this.vlayout(
			StaticText().string_(prompt),
			TextField().string_(default)
			.addNotifier(this, \ok, { | n |
				postln("the new string is" + n.listener.string.asCompileString);
				this.(n.listener.string)
			}),
			HLayout(
				Button().states_([["OK"]])
				.action_({
					// "I will really do this".postln;
					this.changed(\ok);
				}),
				Button().states_([["CANCEL"]])
				.action_({
					"Action canceled by user".postln;
					window.close;
				})
			)
		)
	}
}