/* 12 Jul 2023 07:08
Reads templates from a directory.
Constructs code for BufCode.

SuperClass for BufferSynths, PlainSynths - which look for their templates inside their
separate folders.
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

	*getFunc { | funcname | ^this.getTemplate(funcname).func }

	// TODO : Use Library instead of this. Optimize.
	*getTemplate { | funcname |
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
		// template = this.makeTemplate;
	}

	defaultSpecs { ^[PlayBuf_] }
	makeTemplate {
		// postln("Skipping template making for" + name);
		^""
	}
}