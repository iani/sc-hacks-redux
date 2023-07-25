/* 21 Jun 2023 23:49
Asc to confirm before evaluating a function.

{ "Yes I did this".postln; }.confirm;
{}.inputText;
*/

+ Function {
	confirm { | message = "Do you really want to do this?", ok = "OK" |
		var window;
		window = this.vlayout(
			StaticText().font_(Font("Arial", 20)).string_(message)
			.background_(Color(1.0, 0.7, 0.6)),
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
		).name_("PLEASE CONFIRM!")
		.bounds_(Rect(400, 400, 500, 300));
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
					this.changed(\ok);
				}),
				Button().states_([["CANCEL"]])
				.action_({
					"Action canceled by user".postln;
					window.close;
				})
			)
		).bounds_(Rect(400, 400, 600, 200))
	}

	editTexts {  | default, prompt = "Customize your code:" |
		var window, texts, stuff;
		default ?? { default = ["some extra", "texts for", "you to enter"]; };
		stuff = [StaticText().string_(prompt)] ++
		(texts = default collect: { | t | TextView().string_(t) })
		++ [HLayout(
				Button().states_([["OK"]])
				.action_({
					this.changed(\ok);
					postln("sending texts to caller");
					this.(texts collect: _.string);
					window.close;
				}),
				Button().states_([["CANCEL"]])
				.action_({
					"Edits canceled".postln;
					window.close;
				})
			)];
		window = this.vlayout(*stuff).bounds_(Rect(400, 400, 600, 200))
	}

}