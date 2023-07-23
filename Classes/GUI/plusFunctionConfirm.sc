/* 21 Jun 2023 23:49
Asc to confirm before evaluating a function.

{ "Yes I did this".postln; }.confirm;
{}.inputText;
*/

+ Function {
	confirm { | message = "Do you really want to do this?", ok = "OK" |
		var window;
		window = this.vlayout(
			TextView().string_(message),
			HLayout(
				Button().states_([[ok]])
				.action_({
					// "I will really do this".postln;
					this.(window);
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

	getPath { | object, message = "Choose a path for" |
		// TODO: Complete this method!
		message = message + object;

	}

	inputText {  | default = "something", prompt = "Enter a new string" |
		var window;
		window = this.vlayout(
			StaticText().string_(prompt),
			TextField().string_(default)
			.addNotifier(this, \ok, { | n |
				postln("the new string is" + n.listener.string.asCompileString);
				this.(n.listener.string);
				window.close;
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
		).bounds_(Rect(400, 400, 500, 300))
	}
}