/* 11 Sep 2023 10:33
Alternative method for playing patterns with Chords:



PROTOTYPE - example to develop:
//:
a = Pseq((60..70), inf).midicps;
Pbind(\freq, a, \dur, 0.05).play;
//:
a = Prand((60..70), inf).midicps;
Pbind(\freq, a, \dur, 0.05).play;
//:

TASK:
Produce a variant of midicps called chordcps, that takes
as input chord specs (as symbols or strings, format "a:b:c" etc.)
and produces arrays of frequencies.

*/

/*
IMPLEMENTATION:
1. define chordcps method for AbstractFunction based on this:
	midicps { ^this.composeUnaryOp('midicps') }
2. define chordcps for SequenceableCollection, String and Symbol.
For SequenceableCollection:
	midicps { ^this.performUnaryOp('midicps') }
For String and Symbol:
^Chord(this).freqs
*/

/*
See also:
AbstractFunction.sc        AbstractFunction-midicps
SequenceableCollection.sc  SequenceableCollection-midicps
SimpleNumber.sc            SimpleNumber-midicps
Symbol.sc                  Symbol-midicps

*/

// ====== Shortcut for playing chord sequences as patterns?
// Pattern.chordpattern(other keys) => EventStream or Pbind
// with freq key set to the chordpattern
// alternatively code this directly in the key of a pbind
// or eventstream:
// Does not work!:
// (freq: ["a", "bdg"].pseq.chordcps, dur: 0.5) +> \x;
// works:
/*
Pbind(\freq, ["a", "bd:g"].pseq.chordcps, \dur, 0.5).play;

*/
//
+ AbstractFunction {
	chordcps { ^this.composeUnaryOp('chordcps') }
}

+ SequenceableCollection {
	chordcps { ^this.performUnaryOp('chordcps') }
}

+ Symbol {
	chordcps {
		^this.asChord.freqs
	}

}

+ String {
	chordcps {
		^this.asChord.freqs
	}

}