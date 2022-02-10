Warn {
	*new { | text |
		text.warn;
		this.window({ | w |
			w.bounds = Rect(100, 100, 600, 400);
			w.name = "WARNING";
			w.view.layout = VLayout(
				StaticText()
				.backColor_(Color.red)
				.string_(text)
				.addNotifier(this, \warn, { | n, warning |
					n.listener.string = warning;
					warning.postln;
				})
			)
		});
		this.changed(\warn, text);
	}
}