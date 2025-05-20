// 日 18  5 2025 17:15
// This is what Georgios Diapoulis runs to start the piece
// It runs TsrDocument only for George.
// It runs TypingListener for everybody.
TsrStartup {
	var user, listener;
	*new { | user |
		^this.newCopyArgs(user).init;
	}

	init {
		TsrDocument(user);
	}
}