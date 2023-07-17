/* 18 Jul 2023 00:29
Shortcut for playing events saved as code by SfSelections

This is added by emacs through command sclang-snippet-bufplay, bound to C-M-.
*/

Bufplay {
	*play { | event |
		event.at(\buf).envir.play(event);
	}
}