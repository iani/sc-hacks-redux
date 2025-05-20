// Open a Document for typing verses of The Sun Rising.
// Forward the typing actions via OSC to all members in the OscGroup, including yourself

TsrDocument {
	var <user; // the user for this document. Document will only open if the user is the local user.
	var <listener, <document;

	*new { | user |
		(user != User.localId).if {
			^postln("Won't make document for" + user + "who is not the local user");
		};
		^this.newCopyArgs(user).init;
	}

	init {
		document = Document("The Sun Rising for" + user);
		listener = TypingListener();
		document.keyDownAction = { | doc, char, cocoaModifiers, unicode, keycode, key |
			// postln("TYPING:" + [char, cocoaModifiers, unicode, keycode, key]);
			listener.processKeyboardInput(char.ascii, cocoaModifiers, unicode, keycode, key);
			// User.sendToAll(\char, char.ascii, cocoaModifiers, unicode, keycode, key);
		};
	}
}
