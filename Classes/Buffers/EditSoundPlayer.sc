/*  4 Jul 2023 21:22

SoundFileSettings, SoundFileEvents, SoundFileEvent play dictionaries
of sounds with settings loaded from files.

Here we make a protype for playing buffers with settings selected
by the user interactively.  The user edits the parameters for playing the sound
and tests it. Edited parameters are written in the envir
of the Mediator that plays the sound.

The user can also save the edited paramters in scripts generated
by this class.

====== Design draft: ======

STATIC ELEMENTS:

- The Mediator name and the player name should be static,
so that playing new sounds replaces any previously playing ones.

DYNAMIC ELEMENTS:

- The playfunc should be selectable from a list of available playfuncs
	(Constructed by reading playfunc scripts, where?
	Use a subfolder from SoundFileSettings main directory?)
The playfunc may be stored in a separate instance variable, outside
the envir of the mediator, and provided to the mediator through
	aMediator.play(argPlayFunc, argEvent).
when playing.

*ALL* of the other elements below should be stored in the envir of the
Mediator that will play the sound with
	a Mediator.play(argPlayFunc, argEvent).
These elements are:

- The name of the buffer (selectable from a list of buffers).
	(Buffers loaded in scsynth and bound to Buffer dict names).

- Other parameters should be editable interactively from GUI elements.
	Main parameters to edit are: rate, startPos, loop, amp.
	Other parameters?

*/

EditSoundPlayer {
	classvar <>mediatorName = \soundEditor;
	classvar <playfuncs; // dictionary of playfunc names -> playfuncs
	classvar <>defaultPlayFunc;
	// classvar <buffers; // dictionary of buffer names -> buffers
	// User Buffer.dict to build your menu!
	var <mediator; // static. do not change this one.
	// var <>playfunc; // play function (!) selected via name key from
	// playfuncts loaded from file at
	// "~/sc-projects/EditSoundPlayfuncs" or other specified path
	// The name is stored in event playfunc
	var <>event; // event stores choices by user such as buffer,
	// parameter settings like rate etc.

	*getPlayFunc { | pfuncname |
		this.loadIfNeeded;
		if (pfuncname.isNil) {
			^defaultPlayFunc;
		}{
			// postln("ESP debugging getPlayfunc. funcname is" +
			// 	pfuncname + "func found is" + playfuncs[pfuncname]
			// );
			// postln("playfuncs are" + playfuncs);
			^playfuncs[pfuncname] ? defaultPlayFunc;
		}
	}

	*new { | mediatorName = \s |
		^Registry(this, mediatorName, { | name |
			this.newCopyArgs(mediatorName.envir).init;
		});
	}

	init {
		this.class.loadIfNeeded;
		this.initEvent;
		// this.initPlayfunc;
	}

	*loadIfNeeded {
		playfuncs ?? {
			this.load;
			defaultPlayFunc = { PlayBuf_.ar() };
		};
	}

	initEvent {
		event ?? {
			event = ();
			event[\buf] = Buffer.all.first;
			event[\playfunc] = \playbuf;
		};
		^event;
	}

	/* // USE EVENT ARGUMENT in play INSTEAD OF this.
	set { | ... args |
		args.pairsDo({ | key, value |
			event[key] = value;
		})
	}

	initPlayfunc {
		playfunc = playfuncs[event[\playfunc] ? \playbuf]
	}
	*/

	*load {
		PathPreferences.doWithPathFor(
			this,
			{ | path |
				this.loadFromPath(path)
			}
		);
	}

	*loadFromPath { | path |
		playfuncs = IdentityDictionary();
		PathName(path).files do: { | pn |
			pn.fullPath.load;
			playfuncs[pn.fileNameWithoutExtension.asSymbol] =
			pn.fullPath.load;

		}
	}

	*defaultPath { // TODO: user new class PathPreferences
		^"~/sc-projects/EditSoundPlayfuncs".standardizePath;
	}

	cplay { | argEvent | this.clear.play(argEvent) }
	clear { mediator.clear }
	play { | argEvent |
		mediator.play(argEvent);
	}

	makePlayfunc {

	}

	gui {
		this.vlayout(
			this.soundView,
			this.settingsView
		)
	}

	soundView {

	}

	settingsView {
		^HLayout(
			StaticText().string_("rate"),
			NumberBox(),
			StaticText().string_("startpos"),
			NumberBox(),
			StaticText().string_("duration"),
			NumberBox(),
		)
	}
}