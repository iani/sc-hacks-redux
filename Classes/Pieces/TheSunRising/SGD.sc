


SGD {
	classvar <typingIOI, <typingVersesIOI, <typedChars, <autocorrIOI;
	classvar <typedVerseChars, <letters, <lowercase,<uppercase;
	classvar <lastTimestamp, <currTimestamp, <timeDiff, <currChar;
	classvar <running_sum, <letter_frequency;
	*initClass {
		StartUp add: { this.init }
	}

	*init {
		typingIOI = [];
		typingVersesIOI = [];
		typedChars = [];
		typedVerseChars = [];
		letters = Dictionary.new();
		lowercase = (97..122).asAscii; // lowercase
		uppercase = (65..90).asAscii; // uppercase
		lowercase do: { |v| letters[v.asSymbol] = 0; };

		Tsr.doOnType2({ | char, modifier, unicode |
			// postln("Tsr received char - for verse matching" + char);
			char.post; " ==== ".post; char.class.postln;
			char.asAscii.post; " == ".post; char.asAscii.class.postln;
			[char, modifier, unicode].postln;
			currTimestamp = Clock.seconds;
			if(lastTimestamp.notNil){
				timeDiff = (lastTimestamp - currTimestamp).abs;
				typingIOI = typingIOI ++ timeDiff;
				typedChars = typedChars ++ char;
			};

			if(unicode == 13){
				typingIOI[0] = 0.0;
				typingVersesIOI = typingVersesIOI ++ [typingIOI];
				typedVerseChars = typedVerseChars ++ [typedChars];
				typingIOI = [];
				typedChars = [];
				// autocorrIOI= typingVersesIOI collect: {|arr| arr.autocorr };
			};

			// ">> timeDiff: ".post; ~timeDiff.postln;
			lastTimestamp = currTimestamp;
			// (instrument: \pinch, freq: char*8, ffreq: char / 3).play;
			// >> Letter frequency
			if(char.asAscii.isUpper){
				// " wdENTER UPPER".postln;
				currChar = lowercase[uppercase.find(char.asAscii.asString)];
				// "upercase: ".post; uppercase.find(char.asAscii.asString);
				// "currChar: ".post; currChar.postln;
			}{
				// "ENTER lowercase".postln;
				currChar = char.asAscii;
			};

			if(letters.includesKey(char.asAscii.asSymbol)){
				letters[currChar.asSymbol] = letters[currChar.asSymbol] + 1;
				running_sum = letters.values.sum;
				letter_frequency = letters[currChar.asSymbol] / running_sum;
			};
			// (instrument: \pinch, freq: char*4, ffreq: char / 2).play;
		}, \geodia_statistics);
	}

}