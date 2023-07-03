/*  2 Jul 2023 10:20

Holds events with parameters for playing a buffer.
Each event is created by loading an scd file that sets the parameters
as environment variables.


Each event gives

loads event code from files in a folder and creates events for playing a sound file.

Stores the events in a dictionary, using the names of the files (without extensions) as keys.
*/

SoundFileEvents {
	var <settings;
	var <path;
	var <dict;
	var <name;

	*new { | argSettings, argPath |
		^this.newCopyArgs(argSettings, argPath, IdentityDictionary()).read;
	}

	playFuncAt { | funcName | ^settings.playFuncAt(funcName) }

	eventAt { | argName | ^dict[argName] }

	playAt { | argName |
		var e;
		e = this.eventAt(argName);
		if (e.isNil) {
			postln("no event fonud at" + argName);
		}{
			postln("playing" + e[0]);
			e[0].play;
		}
	}

	editAt { | argName |
		var e;
		e = this.eventAt(argName);
		if (e.isNil) {
			postln("no event found at" + argName);
		}{
			postln("editing" + e[1]);
			Document.open(e[1]);
		}
	}

	read {
		name = path.folderName.asSymbol; // this is also the buffer name!
		postln("SoundFileEvents loading" + path);
		postln("Files are:" + path.files);
		path.files do: { | p |
			var soundevent;
			postln("path " + p + "is scd" + (p.extension == "scd"));
			if (p.extension == "scd") {
				soundevent = this.makeSoundFileEvent(p);
				// eventName = p.filenameWithoutE
				// dict[name]
			};
		};
		postln("Made these events:" + dict);
	}

	makeSoundFileEvent { | argPath |
		var sevent;
		sevent = SoundFileEvent(this, argPath);
		dict[sevent.eventName] = sevent;
		// e = Event();
		// e.push;
		// argPath.fullPath.load; // fill e with specs from file
		// e.pop;
		// e[\buf] = name;
		// needs review:
		// synthFuncName =
		// e[\synthfunc] ?? { e[\synthfunc] = \playbuf };
		// e[\synthfunc] = settings.playfunc(e[\synthfunc]);
		// e[\play] = { ~synthfunc.play };
		// dict[p.fileNameWithoutExtension.asSymbol] = [e, p];
	}
	// ----------- gui methods not used. See SoundFileSettings gui!
	gui {
		this.hlayout(
			this.dictList,
			this.eventList
		).name_(name + "Sound File Settings");
		postln(name + "Sound File Settings");

	}

	dictList {
		^ListView()
		.palette_(QPalette.light
			.highlight_(Color(1.0, 0.9, 0.7))
			.highlightText_(Color(0.0, 0.0, 0.0))
		)
		.items_(dict.keys.asArray.sort)
		.action_({ | me |
			me.item.postln;
			dict[me.item].postln;
			this.changed(\event, me.item);
		})
	}

	eventList {
		^ListView()
		.palette_(QPalette.light
			.highlight_(Color(1.0, 0.9, 0.7))
			.highlightText_(Color(0.0, 0.0, 0.0))
		)
	}
}
