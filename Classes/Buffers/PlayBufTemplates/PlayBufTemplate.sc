/* 12 Jul 2023 07:08
Reads templates from a directory.
Constructs code for BufCode.
*/

PlayBufTemplate {
	classvar <playfuncs; // Dictionary of playfuncs
	classvar <homefolder, <templatesfolder, <playfuncsfolder, <codefolder;

	var <path, <name, <func, <template, <specs, <code;

	*initClass {
		StartUp add: { this.init }
	}

	*init {
		playfuncs = IdentityDictionary();
		this.templatePaths do: { | p |
			this.new(p)
		}
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
			specs = code[..delimiters[0]].interpret ?? { this.defaultSpecs };
			func = code[delimiters[0]..].interpret;
		};
		template = this.makeTemplate;
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