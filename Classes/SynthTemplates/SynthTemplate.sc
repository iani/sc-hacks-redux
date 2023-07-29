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

	// TODO : Use Library instead of this. Optimize.
	*getTemplate { | funcname |
		var debug, instance;
		// postln("Debugging SynthTemplate getTemplate. Funcname:" + funcname);
		// debug = this.allSubclasses.collect(_.getTemplate(funcname));
		// postln("all found were:" + debug);
		// instance = debug.detect(_.notNil);
		// postln("instance is" + instance);
		// postln("func is" + instance.func);
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
}