// Respond to messages only if your name is identical with User.localId
// Created and returned by classes that want to perform actions
// only if their instances have the same name as User.localId;

LocalUserAction {
	var <user, <action;

	*new { | user = \x, action |
		^this.newCopyArgs(user, action);
	}

	doesNotUnderstand { | message ... args |
		(user === User.localId).not.if {
			postln("Cannot do" + message + "because user" + user + "is not local");
			^this;
		};
		^action.perform(message, *args);
	}
}