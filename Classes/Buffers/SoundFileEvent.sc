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
	var <playFunc;
	var <playerName; // constructed: bufferName+eventName

	*new { | fileEvents, path |
		^this.newCopyArgs(fileEvents, path).init;
	}

	init {
		bufferName = fileEvents.name;
		eventName = path.fileNameWithoutExtension.asSymbol;
		playerName = bufferName ++ eventName;
		this.makeEvent;
	}

	makeEvent {
		var synthFuncName;
		event = ();
		event.push;
		path.fullPath.load;
		event.pop;
		event[\buf] = bufferName;
		synthFuncName = event[\synthfunc] ?? { \playbuf };
		playFunc = fileEvents.playFuncAt(synthFuncName);
	}

	play {
		playerName.play(playFunc, event);
	}
}