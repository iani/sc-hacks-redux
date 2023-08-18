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
		^this.getTemplate(funcname).func.amplify; // amplify: add amp control!
	}

	*templateNames {
		^this.allSubclasses.collect(_.funcnames).flat;
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
			specs = [\amp.asSpec.units_(\amp)] ++ (template collect: _.specs).flat;
		};
	}

	defaultSpecs { ^[PlayBuf_] }
	makeTemplate {
		// postln("Skipping template making for" + name);
		^""
	}
	dict { // dictionary for creating presets
		// merge default basicDict with entries from the individual template.
		^this.basicDict addEvent: this.customDict;
	}

	basicDict { // basic dictionary for all SynthTemplates.
		// subclasses may add more keys to this.  See BufferSynths
		// amp is provided to all template funcs using "amplify!" in method getFunc
		^(amp: [1, ""], playfunc: name, selectionNum: 0);
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