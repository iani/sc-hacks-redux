// Respond to messages only if your name is identical with User.localId
// Created and returned by classes that want to perform actions
// only if their instances have the same name as User.localId;

LocalUserAction {
	var <user, <action; // action must be an instenace other thean Function

	*new { | user = \x, action |
		^this.newCopyArgs(user, action);
	}

	run { | ... args |
		(user === User.localId).not.if {
			postln("Cannot run because user" + user + "is not local");
			^this;
		};
		^action.value(*args);
	}


	// older method - too limited
	doesNotUnderstand { | message ... args |
		(user === User.localId).not.if {
			postln("Cannot do" + message + "because user" + user + "is not local");
			^this;
		};
		^action.perform(message, *args);
	}
}