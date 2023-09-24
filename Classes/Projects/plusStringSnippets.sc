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
		// postln("Debugging timeStampLocation" + this.header);
		// postln("regexpmatch is" + this.header.findRegexp(":--\\[\\d.*\\]"));
		^this.header.findRegexp(":--\\[\\d.*\\]")[0]
	}
	time {
		^this.timeStampLocation[1][3..].interpret.first;
	}

	// TODO: Sun 24 Sep 2023 04:35: catch //: snippets without timestamps in hand edited code
	codeReplaceTimeStamp { | newTime = 0 |
		var oldHeader, strippedHeader, location;
		oldHeader = this.header;
		location = this.timeStampLocation;
		strippedHeader = oldHeader[location[0] + location[1].size..];
		^format("//:--[%]%%", newTime, strippedHeader, this.code.stripTimestamp);
	}

	// remove hand-edited timestamps when exporting as code:
	stripTimestamp {
		if (this[..5] == "//:--[") {
			^"//(ts)" + this[5..]
		}{
			^this;
		}
	}

	body { // return the rest of the snippet after the header
		^this[this.find("\n")..]
	}

	// this resulted in deep error on Sun 24 Sep 2023 03:57
	// TODO: Sun 24 Sep 2023 04:35: catch //: snippets without timestamps in hand-edited code
	code {
		^this.body.interpret[1];
	}

	// body returns something too short in just 1 case
	// need to find out what that is.
	// TODO: Sun 24 Sep 2023 04:35: catch //: snippets without timestamps in hand edited code
	codeDebugging {
		"============ body starts here: !!!! =============".postln;
		this.body.postln;
		"============ body ended above !!!! =============".postln;
		^this.body;//  [12..this.body.size-2]
	}


	comments { // experimental
		^(this.body.findRegexp("^//[^\n]*").collect({ | x |
			(x.last ?? { "" }) + "\n" })).cat;
	}

	comment { // ensure each line starts with //
		var result, result2 = "";
		result = this.split($\n).collect({ | s |
			case
			{ s.size == 0 }{ s }
			{ s[..1] == "//" } { "\n" ++ s }
			{ true } { "\n//" ++ s }
		});
		result do: { | x | result2 = result2 ++ x };
		^result2
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
	ensureNL {
		// snippets must end in \n in order for export to result in playable script
		if (this.last == Char.nl) {
			^this;
		}{
			^this ++ "\n";
		}
	}
}

+ Array {
	cat { ^(this.first ?? "").catArgs(*this[1..]) }
}