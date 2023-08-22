/* 20 Feb 2022 15:43
Simple way to post / stop posting update messages emitted by any object
*/

Trace {
	classvar <>excludedMessages;

	*initClass {
		excludedMessages = [
			'/cbmon', '/status.reply', '/done', '/n_end',
			'/recordingDuration', '/n_go', '/d_removed', '/synced',
			'/groupclient/ping'
		]
	}

	*exclude { | message |
		if (excludedMessages includes: message) {
		}{ excludedMessages = excludedMessages add: message }
	}

	*include { | message |
		if (excludedMessages includes: message) { excludedMessages remove: message }
	}

	*excludeServerMessages_p {
		^excludedMessages includes: '/status.reply'
	}
	*excludeServerMessages {
		this.serverMessages do: { | m | this exclude: m }
	}

	*includeServerMessages {
		this.serverMessages do: { | m | this include: m }
	}

	*serverMessages {
		^['/status.reply', '/done', '/n_end',
		 '/recordingDuration', '/n_go', '/d_removed', '/synced']
	}

	*filterServerMessages { | status = false |
		if (status) {
			this.excludeServerMessages;
		}{
			this.includeServerMessages;
		}
	}

	*update { | changer ... args |
		if (excludedMessages includes: args[0]) {
			// postln("Testing excludedMessages" + excludedMessages + "args[0] is" + args[0])
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
	traceChanges { this addDependant: { | ... args | args.postln; } }
	trace { this addDependant: Trace }
	untrace { this removeDependant: Trace }
}