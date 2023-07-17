/* 12 Jul 2023 07:08
Reads templates from a directory.
Constructs code for BufCode.
*/

SynthTemplate {
	classvar <playfuncs; // Dictionary of SynthTemplate instances
	classvar <homefolder, <templatesfolder, <playfuncsfolder, <codefolder;

	var <path, <name, <func, <specs, <code, <template;

	*initClass {
		StartUp add: { this.init }
	}

	*init {
		Spec.addSC_Hacks_Specs;
		playfuncs = IdentityDictionary();
		this.templatePaths do: { | p | this.new(p) };
	}

	*templatePaths {
		^(PathName(this.filenameSymbol.asString).pathOnly ++ "*.scd").pathMatch;
	}

	*new { | path |
		^this.newCopyArgs(path).init;
	}

	init {
		name = PathName(path).fileNameWithoutExtension.asSymbol;
		playfuncs[name] = this;
		this.load;
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

	*getFunc { | argName = \playbuf |
		^this.getTemplate(argName).func;
	}

	*getTemplate { | argName = \playbuf |
		^(playfuncs[argName] ?? { playfuncs[\playbuf] });
	}

	*at {   | argName = \playbuf |  ^this.getTemplate(argName) }

}