/*  4 Jul 2023 16:08
Hold a playfunc and its specs.
Create these by reading, parsing and evaluating a file of format:

[specs code ...]
//:
{ function code }

The specs are used to create gui elements
The function is used to play an event from SoundFileEvent.
See class SoundFileEvent.

*/

PlayFunc {
	var <settings, <path, <specs, <func;
	var <template; // template for BufCode

	*new { | settings, path |
		^this.newCopyArgs(settings, path).load;
	}

	load {
		var string, delimiters;
		string = File.readAllString(path.fullPath);
		delimiters = string.findAll("\n//:");
		if (delimiters.size == 0) {
			specs = this.defaultSpecs;
			func = string.interpret;
		}{
			specs = string[..delimiters[0]].interpret ?? { this.defaultSpecs };
			func = string[delimiters[0]..].interpret;
			template = this.makeTemplate;
		}
	}

	defaultSpecs { ^[PlayBuf_] }

	makeTemplate {
		// TODO!
		^""
	}
}