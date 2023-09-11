/* 10 Sep 2023 21:01
Experimental.
Playing chords in various ways.

Here a basic first start.

To play use class variable play. Args: playfunc, freqs, durations = 1, amps = 0.1
*/
//	where:
// playfunc is one of:
// 1. symbol -> obtain from template or use as synthdef name
// 		the template may return a function or an array of synthdef names.
//      the template is looked up in playfuncTemplates folder. if not found, it
//      is used as a SynthDef name, and a function is constructed for playing it
//      withe one synth per freq - dur - amp triple.
// 2. function -> use as playFunc
// 3. array -> array of symbols to be used as synthdef names.

// ====== Getting the chordPlayfunc:
// 1. If a Function: store it.
// 2. If an array: The array should contain only names of synthdefs!:
// construct a playfunc which iterates over each element of the
// array and makes a synth with it, using the freqs, durations and amps for each symbol
// 3. If a symbol: Get the template from the matching file in playfuncTemplates.
// 3a. if found, the file can return a function or an array.  Treat these as in 1 and 2 above,
// and store a function created from that.
// 3b. if not found, construct an array iterating the symbol for each frequency, and
// create a function that iterates over this array as in 2 above.
// ---- applying the chordPlayfunc:
// THEN in play ChordPlayer:play:
// The function obtained above, is called in play with: func.(freqs, durations, amps);

ChordPlayer {
	classvar playfuncs;
	var <playfunc, <freqs, <durations, <amps;

	*initClass {
		StartUp add: { this.readPlayfuncs }
	}

	*readPlayfuncs {
		playfuncs = IdentityDictionary();
		this.playfuncTemplatePaths do: { | p |
			playfuncs[p.name] = p.load;
		};
	}

	*playfuncTemplatePaths {
		^(PathName(this.filenameSymbol.asString).pathOnly +/+ "playfuncTemplates" +/+ "*.scd").pathMatch;
	}

	*playfuncs { ^playfuncs ?? { playfuncs = IdentityDictionary() }}

	*play { | playfunc, freqs, durations = 1, amps = 0.1 |
		^this.newCopyArgs(playfunc.asChordPlayfunc, freqs.freqs, durations, amps).play;
	}

	play { playfunc.(freqs, durations, amps); }

}