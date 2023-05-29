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
			'/recordingDuration', '/n_go', '/d_removed', '/synced',
			'/groupclient/ping'
		]
	}

	*serverMessages {
		^['/status.reply', '/done', '/n_end',
		 '/recordingDuration', '/n_go', '/d_removed', '/synced']
	}



	*update { | changer ... args |
		if (excludedMessages includes: args[0]) {

		}{
			postln("changed:" + changer + "args:" + args);
		}
	}

	*ping {
		^{
			var startTime;
			startTime = Date.localtime.asString;
			inf do: { | i |
				LocalAddr().sendMsg(\oscping, startTime, i,  Date.localtime.asString);
				1.wait;
			};
		}.fork;
	}
}


+ Object {
	trace { this addDependant: Trace }
	untrace { this removeDependant: Trace }
}