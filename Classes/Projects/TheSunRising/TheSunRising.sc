// 水 30  4 2025 17:07
// Methods for playing buffers in various ways inside the performnce
// TheSunRising (ICLC25).
// This should be used by the GUI created by the iani startup file:
// 00LoadAudioVerses.scd

TheSunRising {
	classvar <>verseFunc; // possibly a global verse playing function
	// preferably a separate verse function for each voice wav folder
	classvar <>verseFunc1, <>verseFunc2, <>verseFunc3, <>verseFunc4;
	*addVerses { | argVerses |
		// do some stuff with the verse name or buffer array
		// received by listviews in window created by 00LoadAudioVerses.scd
		verseFunc.(argVerses);
	}

	// to changge the behavior of this class, store a different function
	// in verseFunc.
	// Additionally this function could call other methods from the
	// present class.  Or it could be a subclass of TheSunRising
	// which encapsulates the playing behavior, and responds to the
	// message "value" by playing the verse info given by argVerses
	// Additionally the subclass stored in verseFunc could be returned
	// by some method(s) that select(s) the kind of subclass whose
	// behavior we want to set.

}