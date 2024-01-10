/* 13 Feb 2022 12:14
Simple dialog prompt to input a string from the user.
*/

Ask {
	*new { | listener, prompt, message = \userInput |
		prompt ?? { prompt = format("Input for %\n", listener) };
		prompt.warn;
		this.window({ | w |
			w.bounds = Rect(100, 100, 400, 100);
			w.name = "Input:";
			w.view.layout = VLayout(
				StaticText().string_(prompt),
				TextField()
				.string_(prompt ++ " here")
				.addNotifier(w, \input, { | n |
					n.listener.string.postln;
					listener.perform(message, n.listener.string)
				}),
				Button().states_([["Submit"]])
				.action_({
					w.changed(\input)
				})
			)
		});
		// this.changed(\warn, text);
	}
}