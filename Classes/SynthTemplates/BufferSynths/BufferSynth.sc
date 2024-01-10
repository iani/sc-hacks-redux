/* 27 Jul 2023 23:15

*/

BufferSynth : SynthTemplate {

	basicDict { // basic dictionary for all BufferSynths
		var buf = \default;
		^(
			buf: [buf, ""],
			startframe: [0, ""],
			endframe: [buf.buf.numFrames, ""],
		) addEvent: super.basicDict;
	}
}