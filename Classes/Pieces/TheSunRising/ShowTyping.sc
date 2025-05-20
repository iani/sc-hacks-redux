// 金 16  5 2025 01:06
//: Show typing received from TypingListener
ShowTyping {
	classvar <tv, <txt;
	*new { { this.makeWindow }.defer }
	*makeWindow {
		var window;
		window = \tsr.hlayout(
			tv = TextView();
		);
		tv = window.view.children.first;
		window.alwaysOnTop = true;
		tv.string = "";
		window.bounds = Rect(0, 0, 500, 900);
		Tsr.doOnType({ | char |
			{
				txt = tv.string;
				char = char.asAscii;
				(char.isAlpha or: { char.isPunct } or: { char.isSpace }).if {
					tv.string = txt ++ (char.asString);
				}
			}.defer;
		}, \textwindow)
	}
}
//: