/* 12 Jan 2022 12:45
List all buffers
*/

+ Buffer {
	*all {
		var bufferDict;
		bufferDict = Library.at(this);
		if (bufferDict.size == 0) {
			"There are no buffers in the library. Please check if server is booted".postln;
			^[]
		}{
			^bufferDict.keys.asArray.sort;
		}
	}
	*listAll {
		var bufferdict;
		bufferdict = Library.at(this);
		this.all do: { | bname |
			bufferdict[bname].postln;
		}
	}
}