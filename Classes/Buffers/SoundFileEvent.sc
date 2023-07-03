/*  3 Jul 2023 22:13
\a ++ \b;
*/

SoundFileEvent {
	//  given from SoundFileEvents at creation:
	var <fileEvents;
	var <path;
	// created internally:
	var <bufferName;
	var <eventName;
	var <event;
	var <playFunc; // Function used to make the synth.
	var <playerName; // constructed: bufferName+eventName

	*new { | fileEvents, path |
		^this.newCopyArgs(fileEvents, path).init;
	}

	init {
		bufferName = fileEvents.name;
		eventName = path.fileNameWithoutExtension.asSymbol;
		playerName = (bufferName ++ "+" ++ eventName).asSymbol;
		this.makeEvent;
	}

	makeEvent {
		event = ();
		event.push;
		path.fullPath.load;
		event.pop;
		event[\buf] = bufferName;
		this.makePlayFunc;
	}

	makePlayFunc {
		// If playfunc is provided, use it.
		// Else get it from FileEvents based on sfname
		var synthFuncName;
		^playFunc = event[\playfunc] ?? {
			fileEvents.playFuncAt(event[\sfname])
		};
	}

	play {
		playerName.play(playFunc, event);
	}
}