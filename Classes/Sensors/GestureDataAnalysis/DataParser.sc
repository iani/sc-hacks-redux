/*
DataParser.sc
Handles loading and parsing of timestamped sensor data from files
*/

DataParser {
	classvar <>dataPath;
	
	*initClass {
		dataPath = "data/".resolveRelative;
	}
	
	*parseFile { |filename|
		// construct full path and check if file exists
		var file;
		var path = this.dataPath +/+ filename;
		
		if(File.exists(path).not) {
			"File does not exist: %".format(path).error;
			^nil
		};
		^this.parseFileFromFullPath(path);
	}

	*parseFileFromFullPath { | path |
		// read contents of existing text file and collect data
		var fileContents, lines, data = List[];
		fileContents = File.readAllString(path);
		lines = fileContents.split(Char.nl);
		^lines.collect({ | l | format("[%]", l).interpret })
		.select({ | i | i.size == 2 });
	}
	
	*getFiles {
		var path = this.dataPath;
		^PathName(path).files.collect({ |file| file.fileName });
	}
	
	// Extract a specific dimension of data across all timestamps
	*extractDimension { |data, dimension|
		^data.collect({ |item| [item[0], item[1][dimension]] });
	}

	// Normalize data to 0-1 range
	// 月 28  4 2025 13:51
	// IZ Rewriting this because original was broken (see below)
	*normalizeData { |data|
		var times, values, numColumns;
		#times, values = data.flop;
		numColumns = values.first.size;
		values = values.flat.normalize.clump(numColumns);
		^[times, values].flop;
	}

	*normalizeDataOriginal_Broken { |data|
		// see reason for error below at lines 6-8.
		var values = data.collect({ |item| item[1] });
		var min = values.minItem;
		var max = values.maxItem;
		var range = max - min;

		// this creates error Non Boolean in test
		// because range is an array of booleans.
		if(range == 0) { ^data };
		
		^data.collect({ |item|
			[item[0], (item[1] - min) / range]
		});
	}
}
