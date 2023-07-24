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
					entry = this.copyRange(b, end);
				}{
					entry = this.copyRange(b, this.size - 1)
				};
				snippets = snippets add: entry;
			};
			^snippets;
		}
	}
	header { // return the first line of a snippet
		^this[..this.find("\n")]
	}

	selectionIndex { // used by SfSelections for loading.
		^this.header.findRegexp("\\(\\d*\\)").first.last.interpret;
	}
}