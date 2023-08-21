/* 24 Jul 2023 18:00
Parsing snippets.
For various uses.
First: Reloading selections for SoundBufferGui.
*/

+ String {

	*readSnippetsDialog { | action |
		FileDialog({ | path |
			action.(path.first.readSnippets);
		})
	}

	readSnippets {  // read snippets from a files
		var string;
		string = File(this, "r").readAllString;
		^string.snippets;
	}

	snippets {
		var delimiters, snippets;
		delimiters = this.findAll("\n//:");
		if (delimiters.size == 0) {
			snippets = [this];
		}{
			delimiters do: { | b, i |
				var end, entry;
				end = delimiters[i + 1];
				if (end.notNil) {
					entry = this.copyRange(b + 1, end);
				}{
					entry = this.copyRange(b + 1, this.size - 1)
				};
				snippets = snippets add: entry;
			};
			^snippets;
		}
	}
	header { // return the first line of a snippet
		^this[..this.find("\n")]
	}

	timeStampLocation {
		^this.header.findRegexp(":--\\[\\d.*\\]")[0]
	}
	time {
		^this.timeStampLocation[1][3..].interpret.first;
	}

	codeReplaceTimeStamp { | newTime = 0 |
		var oldHeader, strippedHeader, location;
		oldHeader = this.header;
		location = this.timeStampLocation;
		strippedHeader = oldHeader[location[0] + location[1].size..];
		^format("//:--[%]%%", newTime, strippedHeader, this.code);
	}

	body { // return the rest of the snippet after the header
		^this[this.find("\n")..]
	}

	code {
		^this.body.interpret[1];
	}

	comments { // experimental
		^(this.body.findRegexp("^//[^\n]*").collect({ | x |
			(x.last ?? { "" }) + "\n" })).cat;
	}

	selectionIndex { // used by SfSelections for loading.
		^this.header.findRegexp("\\(\\d*\\)").first.last.interpret;
	}

	openSoundFileSelections { // called from emacs dired ...
		// see function dired-open-soundfile-selections-script
		var snippets, parsedsnippets, sbgui, selections;
		postln("opening sound file selections for" + this);
		snippets = this.readSnippets;
		"==========".postln;
		snippets.postln;
		snippets do: { | s |
			var header;
			// s.header.postln;
			// postln ("s size is" + s.size);
			header = s.header;
			if (header.size < 3) { // avoid empty snippets
				postln("snippet not parseable:" + s);
			}{
				parsedsnippets = parsedsnippets add: [s.selectionIndex, s];
			}
		};
		// ^parsedsnippets.postln;
		sbgui = SoundBufferGui.new();
		selections = sbgui.selections;
		selections.postln;
		parsedsnippets do: { | ps |
			selections.addSelectionFromSnippet(*ps);
		}
	}
}

+ Array {
	cat { ^(this.first ?? "").catArgs(*this[1..]) }
}