/* 27 Jul 2023 23:15

*/

BufferSynths : SynthTemplate {
	// TODO: Store this in Library and keep DRY for subclasses of SynthTemplate
	classvar <playfuncs; // Dictionary of BufferSynths instances

	*init {
		playfuncs = IdentityDictionary();
		this.templatePaths do: { | p | this.new(p) };
	}
	init {
		name = PathName(path).fileNameWithoutExtension.asSymbol;
		playfuncs[name] = this;
		this.load;
	}
	*getTemplate { | argName = \playbuf |
		^(playfuncs[argName] ?? { playfuncs[\playbuf] });
	}

	*at {   | argName = \playbuf |  ^this.getTemplate(argName) }

}