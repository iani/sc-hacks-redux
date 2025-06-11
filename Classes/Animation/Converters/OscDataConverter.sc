// 水 11  6 2025 12:05
// Choose converter to perform conversion from osc
// messages to numeric data, based on the data.
// Then convert messages and return the numeric data.

OscDataConverter {
	classvar converters;
	var <oscdata, <oscmessages, <numdata, <buffer;

	*converters {
		converters ?? {
			converters = (
				'/rokoko/': RokokoConverter,
				'/poseperson': YoloConverter
			)
		};
		^converters;
	}

	*convert { | files |
		var oscdata, converter;
		oscdata = OscData(files);
		converter = this.converters[oscdata.type];
		converter ?? {
			^postln("Could not find converter of type" + oscdata.type);
		};
		// ^converter convert: oscdata;
		^converter.new(oscdata);
	}

	*new { | oscdata |
		^this.newCopyArgs(oscdata).convert;
	}

	convert {
		this.makeMessages;
		this.makeNumData;
		this.makeBuffer;
	}

	makeMessages {
		// oscmessages = oscdata.messages collect: { | m | m.interpret };
		// Only include MOCAP data messages!
		oscdata.messages do: { | string |
			var m;
			m = string.interpret;
			if (m[0] === this.mocapMessage) { oscmessages = oscmessages add: m };
		}
	}

	mocapMessage { this.subclassResponsibility(\mocapMessage) }

	makeNumData {
		numdata = oscmessages collect: this.getNumVector(_);
	}

	getNumVector { this.subclassResponsibility(\makeNumData) }

	makeBuffer {
		var channels;
		channels = numdata.flop;
		// channels.size.postln;
		buffer = Buffer.loadCollection(
			Server.default, channels.lace, channels.size,
			{ | b | this saveBuffer: b }
		);
	}

	saveBuffer { | argBuffer |
		var bufPath;
		bufPath = oscdata.paths.first.folder +/+
		oscdata.paths.first.folderName ++ ".aiff";
		argBuffer.write(bufPath);
		argBuffer.path = bufPath;
	}
}

// Convert rokoko format data into numeric arrays
RokokoConverter : OscDataConverter {

	mocapMessage { ^'/rokoko/' }
	getNumVector { | messageVector |
		var data; // remove header + columns with joint symbols
		data = messageVector[3..].clump(8);
		^data.flop[1..].flop.flat;
	}
}

// Convert yolo format data into numeric arrays
YoloConverter : OscDataConverter {
	mocapMessage { ^'/poseperson' }
	// a yolo data vector has a header of 3 elements:
	// 0: osc message '/poseperson', 1: person id, 2: global confidence
	// This is followed by 17 x,y,z point coordinate triplets. Total: 51 floats.
	getNumVector { | messageVector | ^messageVector[3..]; }

	/* 	// debuged!!!
	makeBuffer {
		var channels;
		channels = numdata.flop;
		buffer = channels;
		// channels.size.postln;
		// buffer = Buffer.loadCollection(
		// 	Server.default, channels.lace, channels.size,
		// 	{ | b | this saveBuffer: b }
		// );
	}
	*/
}