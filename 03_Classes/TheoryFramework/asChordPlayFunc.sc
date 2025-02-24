/* 11 Sep 2023 01:36
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
}
*/

+ Function { asChordPlayFunc { ^this } }

+ Symbol {
	asChordPlayFunc {
		^{ | freqs, durs, amps |
			[freqs, durs, amps].flop do: { | fda |
				Synth(this, [freq: fda[0], dur: fda[1], amp: fda[2]])
			}
		}
	}
}

+ Array {
	asChordPlayFunc {
		^{ | freqs, durs, amps |
			[freqs, durs, amps].flop do: { | fda, i |
				Synth(this@@i, [freq: fda[0], dur: fda[1], amp: fda[2]])
			}
		}
	}
	
}