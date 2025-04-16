/*  6 Sep 2023 08:57
Palette with lighter color for selections.
*/

+ ListView {
	lightPalette {
		this.palette_(QPalette.light
			.highlight_(Color(1.0, 0.9, 0.7))
			.highlightText_(Color(0.0, 0.0, 0.0))
		)
	}
}