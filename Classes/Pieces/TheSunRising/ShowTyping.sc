// 金 16  5 2025 01:06
//: Show typing received from TypingListener
ShowTyping {
	classvar <tv, <txt;
	*new { this.makeWindow }
	*makeWindow {
		\tsr.hlayout(
			tv = TextView();
		);
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