// Open a Document for typing verses of The Sun Rising.
// Forward the typing actions via OSC to all members in the OscGroup, including yourself

TsrDocument {
	var <user; // the user for this document. Document will only open if the user is the local user.
	var <listener, <document;

	*new { | user |
		^this.newCopyArgs(user).init;
	}

	init {
		ShowTyping();
		(user != User.localId).if {
			postln("Won't make document for" + user + "who is not the local user");
		}{
			document = Document("The Sun Rising for" + user);
			document.keyDownAction = { | doc, char, cocoaModifiers, unicode, keycode, key |
				listener.processKeyboardInput(char.ascii, cocoaModifiers, unicode, keycode, key);
			};
			listener = TypingListener();
		};
	}
}
