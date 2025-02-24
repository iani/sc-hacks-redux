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
		playerName = (bufferName ++ "_" ++ eventName).asSymbol;
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

	edit {
		Document.open(path.fullPath);
	}
	play {
		^playerName.play(playFunc.func, event);
	}
	gui { // experimental!!!!
		// open gui with sliders for setting parameters
		// get the specs for the sliders from the specs of the playfunc;
		var specs;
		specs = playFunc.specs collect: _.specs;
		postln("specs are: " + specs);
		postln("func is" + playFunc.func);
	}
}