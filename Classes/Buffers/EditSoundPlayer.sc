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
	// classvar <buffers; // dictionary of buffer names -> buffers
	// User Buffer.dict to build your menu!
	var <mediator; // static. do not change this one.
	var <>playfunc; // dynamic. Edited by choice from user ...
	var <>event; // event stores choices by user such as buffer,
	// parameter settings like rate etc.

	*new { | mediatorName = \soundeditor |
		^Registry(this, mediatorName, { | name |
			this.newCopyArgs(mediatorName.envir).init;
		});
	}

	init {
		this.loadIfNeeded;
		this.getPlayfunc;
		this.getEvent;
	}

	loadIfNeeded {
		playfuncs ?? { this.class.load };
	}

	getEvent {
		event ?? {
			event = ();
			event[\buf] = Buffer.all.first;
		};
		^event;
	}

	getPlayfunc {
		^playfunc ?? { playfunc = playfuncs[\playbuf] }
	}

	*load {
		PathPreferences.doWithPathFor(
			this,
			{ | path |
				this.loadFromPath(path)
			}
		);
	}

	*loadFromPath { | path |
		path.postln;
		PathName(path).files.postln;
		playfuncs = IdentityDictionary();
		PathName(path).files do: { | pn |
			pn.postln;
			pn.fullPath.load.postln;
			playfuncs[pn.fileNameWithoutExtension.asSymbol] =
			pn.fullPath.load;
		}
	}

	*defaultPath { // TODO: user new class PathPreferences
		^"~/sc-projects/EditSoundPlayfuncs".standardizePath;
	}

	play {
		mediator.play(this.playfunc, this.event);
	}

}