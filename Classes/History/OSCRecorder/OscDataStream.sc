/*  9 Nov 2022 12:16
Play a subsection of an osc data array, converting absolute time to relative time.
*/

OscDataStream {
	var <data, <from = 0, <length, <repeats = 1;
	var <index = 0;

	*new { | data, from = 0, length, repeats = 1 |
		^this.newCopyArgs(data, from, length, repeats).init;
	}

	init {

		index = Pser(from, length, repeats + 1)
	}

	next { // called when playing in an EventStream

		var thisMessage, nextMessage;
		thisMessage = data[index];

	}
}