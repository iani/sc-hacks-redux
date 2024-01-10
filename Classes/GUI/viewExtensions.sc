/* 14 Oct 2022 00:46
Shortcut for action_({ | me | somebody.changed(message, me) })
*/

+ View {
	notify { | notifier, message, argsFunc |
		this.action = { | me |
			notifier.changed(message, argsFunc.(me))
		}
	}
}

+ ListView {
	item {
		^this.items[this.value]
	}
}