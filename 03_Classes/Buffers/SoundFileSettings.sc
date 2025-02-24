/*  1 Jul 2023 23:33

*/

SoundFileSettings {
	classvar <settingsPath;
	classvar <sets; // dict of one set per folder under project/AudioSampleEvents folder.
	classvar <>defaultPlayfunc;

	var <path;
	var <playfuncs; // dict: playfunc (folder) names -> functions
	var <events; // dict: buffernames -> SoundFileEvents instances
	var <name;
	// internal state for gui
	var <buffer;


	/*


{ "r".postln; }.confirm("something", "Locate preference file");

	*/
	playFuncAt { | playFuncName = \playbuf |
		^playfuncs[playFuncName] ?? { this.defaultPlayFunc }
	}

	defaultPlayFunc {
		^PlayFunc.newCopyArgs(
			nil, nil, [PlayBuf_],
			{
			var buf;
			// TMP debugging messages:
			// postln("debugging playbuf. ~buf is" + ~buf);
			// postln("debugging playbuf. ~buf.buf is" + ~buf.buf);
			buf = ~buf.buf;
			PlayBuf.ar(
				buf.numChannels,
				buf,
				1,
				// \rate.br(~rate ? 1),
				1,
				// \trigger.br(1),
				0,
				// \startpos.br(~startpos ? 0),
				0,
				// \loop.br(~loop ? 0),
				Done.freeSelf
			)
			}
		)
	}
	*setGui {
		this.loadSets;
		^this.br_(200, 100).vlayout(
			ListView()
			.palette_(
				QPalette.light
				.highlight_(Color(0.7, 1.0, 1.0))
				.highlightText_(Color(0.15, 0.2, 0.25))
			)
			.items_(sets.values.asArray.collect(_.name).sort)
			.enterKeyAction_({ | me |
				sets[me.item].gui;
			})
		)
	}

	*gui { | set = \default |
		sets[set].postln;
		sets[set].gui;
	}

	gui {
		// \test.window({  | w |
			// w.view.layout = HLayout(ListView());
		// });
		// "Making a window".postln;
		this.hlayout(
			this.buffersGui(),
			this.eventsGui
		).name_(name.asString + "Sound File Settings");
		{ this.changed(\buffer, events.keys.asArray.sort.first) }.defer(0.1)
	}

	buffersGui {
		^ListView()
		.palette_(QPalette.light
			.highlight_(Color(1.0, 0.9, 0.7))
			.highlightText_(Color(0.0, 0.0, 0.0))
		)
		.items_(events.keys.asArray.sort)
		.action_({ | me |
			buffer = me.item;
			this.changed(\buffer, me.item);
		})
		.addNotifier(this, \buffer, { | n, b |
			buffer = b;
			n.listener.value = n.listener.items.indexOf(b);
		})
	}

	eventsGui {
		^ListView()
		.palette_(QPalette.light
			.highlight_(Color(1.0, 0.9, 0.7))
			.highlightText_(Color(0.0, 0.0, 0.0))
		)
		.addNotifier(this, \buffer, { | n, item |
			events[item].postln;
			events[item].dict.keys.asArray.sort;
			n.listener.items = events[item]
			.dict.keys.asArray.sort;
		})
		.enterKeyAction_({ | me |
			var playingEvent;
			playingEvent = events[buffer].eventAt(me.item).play;
			playingEvent.addNotifier(this, playingEvent[\mediator], { | n |
				n.listener.stopSynths;
			})
		})
		.keyDownAction_({ | me, key ... args |
			case
			{ key == $e } {
				events[buffer].eventAt(me.item).edit;
				// Document.open(events[buffer].eventAt(me.item).fullPath);
			}
			{ key == $p } {
				events[buffer].eventAt(me.item).play;
			}
			{ key == $g } { // EXPERIMENTAL!
				events[buffer].eventAt(me.item).gui;
			}
			{ key == $. } { // stop only this item
				this.changed(events[buffer].eventAt(me.item).playerName);
			}
			{ key == $> } { // stop all items
				me.items do: { | i |
					this.changed(events[buffer].eventAt(i).playerName);
				};
			}
			{ true } {
				me.defaultKeyDownAction(me, key, *args);
			}

		})
	}


	*loadSets {
		this.initSets;
		this.loadSettingsPath;
		if (File.exists(settingsPath)) {
			postln("Loading settings from" + settingsPath);
			this loadSetsFromPath: settingsPath;
		}{
			{
				FileDialog({ | path |
					this.loadSetsFromPath(path[0]);
				}, {}, 2);
			}.confirm(
				"Please locate a folder containing the Sound File Settings",
				"Choose folder"
			)
		}
	}

	*loadSetsFromPath { | argPath |
		settingsPath = argPath;
		this.saveSettingsPath;
		// PathName(settingsPath).folders.postln;
		PathName(settingsPath).folders do: { | p |
			postln("loading " + p.folderName);
			sets[p.folderName.asSymbol] = this.new(p);
		};
	}

	*new { | argPath |
		this.makeDefaultPlayfunc;
		^this.newCopyArgs(argPath, IdentityDictionary(), IdentityDictionary(),
			argPath.folderName.asSymbol
		).read;
	}

	*makeDefaultPlayfunc {
		defaultPlayfunc = {
			var buf;
			// TMP debugging messages:
			postln("debugging playbuf. ~buf is" + ~buf);
			postln("debugging playbuf. ~buf.buf is" + ~buf.buf);

			buf = ~buf.buf;
			PlayBuf.ar(
				buf.numChannels,
				buf,
				\rate.br(~rate ? 1),
				\trigger.br(1),
				\startpos.br(~startpos ? 0),
				\loop.br(~loop ? 0),
				Done.freeSelf
			)
		}
	}

	read {
		this.readPlayfuncs;
		this.readEvents;
	}

	readPlayfuncs {
		(path +/+ "playfuncs").files do: { | p |
			if (p.extension == "scd") {
				playfuncs[p.fileNameWithoutExtension.asSymbol] = PlayFunc(this, p);
			}
		}
	}

	playfunc { | n |
		^playfuncs[n] ?? {
			postln("WARNING: playfunc" + n + "not found.");
			"Returning default play func".postln;
			defaultPlayfunc;
		}
	}

	readEvents {
		(path +/+ "events").folders do: { | f |
			events[f.folderName.asSymbol] = SoundFileEvents(this, f);
		}
	}

	*loadSettingsPath {
		if (File.exists(this.preferencesPath)) {
			settingsPath = Object.readArchive(this.preferencesPath);
			// postln("settingsPath is:" + settingsPath);
		}{
			"The file does not exist. I will make it".postln;
			this.defaultSettingsPath.writeArchive(this.preferencesPath);
		}
	}

	*initSets { sets = IdentityDictionary() }

	*settingsPath_ { | path |
		settingsPath = path;
		this.saveSettingsPath;
	}

	*saveSettingsPath {
		settingsPath.writeArchive(this.preferencesPath);
	}

	*readSettings {
	}

	*preferencesPath {
		^Platform.userAppSupportDir +/+ "SoundFileSettings.scd";
	}

	*defaultSettingsPath {
		^Platform.userHomeDir +/+ "sc-projects" +/+ "AudioSampleEvents/";
	}

}