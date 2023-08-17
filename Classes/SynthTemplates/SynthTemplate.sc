/* 12 Jul 2023 07:08
Reads templates from a directory.
Constructs code for BufCode.

SuperClass for BufferSynths, PlainSynths - which look for their templates inside their
separate folders.

A SynthTemplate can search and return 2 things with 2 methods:
1. getFunc: The Function used to play in a Preset or ...
2. getTemplate: the template - a SynthTemplate or subclass instance containing
	some information needed:
	- the name, function, specs, code used to make the template, and the template itself
	which is an array of classes used to create the specs

*/

SynthTemplate {
	classvar <homefolder, <templatesfolder, <playfuncsfolder, <codefolder;

	var <path, <name, <func, <specs, <code, <template;

	*initClass {
		StartUp add: { this.init }
	}

	*init {
		Spec.addSC_Hacks_Specs;
		this.allSubclasses do: _.init;
	}

	*templatePaths { // Load from same folder as your definition is
		^(PathName(this.filenameSymbol.asString).pathOnly ++ "*.scd").pathMatch;
	}

	*new { | path |
		^this.newCopyArgs(path).init;
	}

	*getFunc { | funcname |
		var debug;
		// debug = this.getTemplate(funcname);
		// postln("Debugging SynthTemplate:getFunc. funcname:" + funcname
		// 	+ "template" + debug +
		// 	"name" + debug.name + "\npath" + debug.path
		// 	+ "\n\ncode\n\n" + debug.code
		// 	+ "\n\n\template:\n\n" + debug.template
		// );
		^this.getTemplate(funcname).func;
	}

	*getTemplate { | funcname |
		// look in all subclases and return the first match.
		^this.allSubclasses.collect(_.getTemplate(funcname)).detect(_.notNil);
	}

	load {
		var delimiters;
		code = File.readAllString(path);
		delimiters = code.findAll("\n//:");
		if (delimiters.size == 0) {
			specs = this.defaultSpecs;
			func = code.interpret;
		}{
			func = code[delimiters[0]..].interpret;
			template = code[..delimiters[0]].interpret ?? { this.defaultSpecs };
			specs = template collect: _.specs;
		};
		// postln("Debugging SynthTemplate Load. name" + name + "path" + path
			// +
			// "code" + code;
		// );
		// template = this.makeTemplate;
	}

	defaultSpecs { ^[PlayBuf_] }
	makeTemplate {
		// postln("Skipping template making for" + name);
		^""
	}
	dict { // dictionary for creating presets
		// merge default basicDict with entries from the individual template.
		var dict;
		dict = this.customDict;
		this.basicDict keysValuesDo: { | key, value | dict[key] = value; };
		^dict;
	}

	basicDict { // basic dictionary for all BufferSynths
		var buf = \default;
		^(
			amp: [1, ""],
			buf: [buf, ""],
			startframe: [0, ""],
			endframe: [buf.buf.numFrames, ""],
		)
	}

	customDict { // dict from template specs
		var dict;
		dict = ();
		this.specs.flat do: { | s |
			dict[s.units] = [s.default, ""];
		};
		^dict;
	}


	edit { Document open: path; }
}