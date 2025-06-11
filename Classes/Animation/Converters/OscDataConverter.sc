// 水 11  6 2025 12:05
// Choose converter to perform conversion from osc
// messages to numeric data, based on the data.
// Then convert messages and return the numeric data.

OscDataConverter {
	classvar converters;
	var <oscdata, <oscmessages, <numdata;

	*converters {
		converters ?? {
			converters = (
				'/rokoko/': RokokoConverter(),
				'/poseperson': YoloConverter()
			)
		};
		^converters;
	}

	*convert { | oscdata |
		var converter;
		converter = this.converters[oscdata.type];
		converter ?? {
			^postln("Could not find converter of type" + oscdata.type);
		};
		^converter convert: oscdata;
	}

	convert { | argOscData |
		oscdata = argOscData;
		this.makeMessages;
		this.makeNumData;
		^numdata;
	}

	makeMessages {
		oscmessages = oscdata.messages collect: { | m | m.interpret };
	}

	makeNumData {
		numdata = oscmessages collect: this.getNumVector(_);
	}

	getNumVector { this.subclassResponsibility(\makeNumData) }
}

// Convert rokoko format data into numeric arrays
RokokoConverter : OscDataConverter {

	getNumVector { | messageVector |
		var data;
		data = messageVector[3..].clump(8);
		^data.flop[1..].flop.flat;
	}
}

// Convert yolo format data into numeric arrays
YoloConverter : OscDataConverter {
	// a yolo data vector has a header of 3 elements:
	// 0: osc message '/poseperson', 1: person id, 2: global confidence
	// This is followed by 17 x,y,z point coordinate triplets. Total: 51 floats.
	getNumVector { | messageVector | ^messageVector[3..]; }
}