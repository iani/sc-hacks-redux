/* 12 Jan 2022 12:45
List all buffers
*/

+ Buffer {
	*dict { ^Library.at(this) }
	/*
	*allocAdd { | key = \buffer, duration = 60, numChannels = 1 |
		if (this.dict.at(\))
	}
	*/
	*addBuffer { | key = \buffer, buffer |  this.dict.put(key, buffer) }
	*all {
		var bufferDict;
		bufferDict = this.dict;
		if (bufferDict.size == 0) {
			"There are no buffers in the library. Please check if server is booted".postln;
			^[]
		}{
			^bufferDict.keys.asArray.sort;
		}
	}
	*listAll {
		var bufferdict;
		bufferdict = this.dict;
		this.all do: { | bname |
			bufferdict[bname].postln;
		}
	}
}