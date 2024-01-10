/*  4 Dec 2022 12:34
Shortcuts for adding responses to ping pong messages.
*/


Pings {
	*addSound { | soundFunc |
		\myping.addNotifier(\pingpong, \pongIsOn, soundFunc)
	}
}

Pongs {
	*addSound { | soundFunc |
		\mypong.addNotifier(\pingpong, \pingIsOn, soundFunc)
	}
}