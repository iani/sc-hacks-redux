/* 20 Feb 2022 15:43
Simple way to post / stop posting update messages emitted by any object
*/

Trace {
	classvar <>excludedMessages;

	*exclude { | ... argMessages |
		excludedMessages = excludedMessages ++ argMessages;
	}
	*initClass {
		excludedMessages = [
			'/cbmon', '/status.reply', '/done', '/n_end',
			'/recordingDuration', '/n_go', '/d_removed', '/synced'
		]
	}

	*update { | changer ... args |
		if (excludedMessages includes: args[0]) {

		}{
			postln("changed:" + changer + "args:" + args);
		}
	}
}


+ Object {
	trace { this addDependant: Trace }
	untrace { this removeDependant: Trace }
}