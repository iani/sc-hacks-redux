// 火  6  5 2025 01:21
// Templates for Synth or Stream that are waiting to start playing

PlayerTemplate : NamedSingleton2 { // neutral.  source specifies default behavior
	classvar permanent; // any instances added here will restart after CmdPeriod
	var <>source;
	var <process; // the running process of this player. A Synth or EventStream.
	var argDict; // accumulate arg settings
	// in FunctionNodeTemplate, and NodeTemplate
	init { | argSource |
		source = argSource ?? { this.defaultSource };
	}

	defaultSource { ^\default }
	defaultArgs { ^Array.new }
	argDict { ^argDict ?? { argDict = () } }

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << name.asString << " : ";
		stream << source.asString;
		stream << ">" ;
	}

	*permanent { ^permanent ?? permanent = IdentityDictionary() }

	fix {
		CmdPeriod add: this.class;
		// this.start;
		this.permanent[name] = this; // keep access by name, to unfix if needed
	}

	start { this.play }

	play {
		postln("Cannot play" + this ++". Use a subclass instead");
	}

	stop {
		postln("message stop does not apply to" + this);
	}

	isPlaying { ^process.isPlaying }

	unfix { this.permanent[name] = nil; }

	*doOnCmdPeriod { this.permanent do: _.start; }

	handleReplacement { | newValue, envir, key |
		// do not do anything if replaced by self
		// Note: Node and Function Template always stops previous
		// synth when a new source is set. (Even if it is the same...)
		// if (newValue === this) {} { this.stop; };
	}

	getParametersFrom { | old |
		// Get extra parameters from previous template
		// Do not overwrite parameters created by your args!
		// "========= GETTING PARAMETERS =========".postln;
		var newp, oldp;
		if (old.isNil) {
			^this;
		};
		newp = argDict ?? { argDict = () };
		oldp = old.argDict;
		// postln("Getting parameters. new" + newp + "old" + oldp);
		oldp keysValuesDo: { | key, value |
			// newp[key] ?? { newp[key] = value }
			 newp[key] = value;
		};
		if (old isKindOf: FunctionNodeTemplate) {
			argDict[\out] = old.outbus;
			argDict[\fadeTime] = old.fadeTime;
		};
		// postln("NodeTemplate:getParametersFrom out" + argDict[\out]
		// 	+ "fadeTime" + argDict[\fadeTime]
		// )
		// postln("AFTER Getting parameters. my argDict is" + argDict);
	}

	parameters { ^argDict ?? { () } }
	getArgs {
		^this.parameters.keys.asArray.sort.collect({ | key |
			[key, argDict[key]]
		}).flat;
	}
}

NodeTemplate : PlayerTemplate { // for synths
	var <args, <target, <addAction = \addToHead;
	defaultSource { ^\default }

	init { | argSource, old, argArgs, argTarget, argAddAction = \addToHead |
		var sourceHasChanged;
		// postln("NodeTemplate:init argSource is" + argSource);
		source ?? { source = this.defaultSource };
		argSource ?? { argSource = this.defaultSource };
		sourceHasChanged = this.sourceHasChanged(argSource);
		source = argSource;
		// postln("NodeTemplate:init SOURCE!!! is" + source);
		target = argTarget.asTarget;
		addAction = argAddAction;
		// first get parameters from older instance,
		// then merge the arguments in your dict.
		this getParametersFrom: old;
		args = (argDict ?? { argDict = (); }) mergeArgs: argArgs;
		// "\n\nDEBUGGING MERGED ARGS!".postln;
		// postln("The argDict after merging is:" + argDict);
		// "\n\n".postln;
		(this.isPlaying and: sourceHasChanged).if {
			this.prStop;
			this.prPlay;
		}{
			this.updateProcessControls;
		}
	}
	sourceHasChanged { | argSource |
		var prevdef, thisdef;
		prevdef = source;
		thisdef = argSource;
		^(thisdef == prevdef).not
	}


	play { // do not return Synth. Return self.
		this.isPlaying.if { this.updateProcessControls; }
		{ this.prPlay }
	}
	prPlay {
		^process = Synth(source, this.getArgs, target, addAction).register;
	}

	stop { this.isPlaying.if { this.prStop }; }
	prStop { process.stop }
	 // empty arguments  // NOTE: keep argDict? What for?
	clear { args = []; }

	set { | ... newArgs |
		if (newArgs[0] isKindOf: Event) {
			newArgs = newArgs[0]
		};
		args = (argDict ?? { argDict = (); }) mergeArgs: newArgs;
		this.updateProcessControls;
	}

	updateProcessControls {
		this.isPlaying.if { process.set(*this.synthArgs); }
	}
	synthArgs { ^args } // FunctionNode adds outbus, fadeTime

	outbus { ^argDict[\out] ? 0 }
	fadeTime { ^argDict[\fadeTime] ? 0.02 }
	// TODO: move synth to new position of addAction and new target if needed
	// moveSynth {  }
}

FunctionNodeTemplate : NodeTemplate { // for synths
	// NOTE: Explicitly changing order of arguments from that of
	// Function:play, because placing args at the end is dumb.
	// the order here is chosen on purpose
	// Copying from Synth:play and adding outbuss and fadeTime last!
	var <outbus, <fadeTime = 0.02;
	// TODO: Resolve this outbus vs out inconsistency in Function:asDef
    defaultSource {
		^{ | freq = 400, amp = 0.1 |
			SinOsc.ar(freq, 0, amp).dup;
		}
	}
	init { | argSource, old, argArgs, argTarget, argAddAction = \addToHead,
		argOutbus, argFadeTime = 0.01 |
		argDict ?? { argDict = () };
		if (argSource.isNil) { // ignore empty sources
			postln("WARNING: trying to init FunctionNodeTemplate" + name +
			"with source nil.\nABORTING")
			^this
		};
		super.init(argSource, old, argArgs, argTarget, argAddAction);
		// reconcile outbus with out
		// NOTE: This is for compatibility with Function:play
		if (argOutbus.notNil) {
			outbus = argOutbus;
			argDict[\out] = outbus;
		}{
			outbus = argDict[\out] ? 0;
		};
		if (argFadeTime.notNil) {
			fadeTime = argFadeTime;
			argDict[\fadeTime] = argFadeTime;
		}{
			fadeTime = argDict[\fadeTime] ? 0.02;
		}
	}
	sourceHasChanged { | argSource |
		var prevdef, thisdef;
		prevdef = source.def.sourceCode;
		thisdef = argSource.def.sourceCode;
		^(thisdef == prevdef).not
	}

	prPlay {
		// "Debugging FunctionNodeTemplate.play".postln;
		// postln("source" + source + "target" + target
		// 	+ "outbus" + outbus + "fadeTime" + fadeTime
		// 	+ "addAction" + addAction + "args" + this.getArgs;
		// );
		// ^this;
		this.fixOutAndFadeTime;
		^process = source.play(
			target, outbus, fadeTime, addAction, this.getArgs
		).register;
	}

	fixOutAndFadeTime {
		this.argDict[\out].notNil { outbus = argDict[\out] };
		this.argDict[\fadeTime].notNil { fadeTime = argDict[\fadeTime] };
	}

	synthArgs {
		argDict ?? { argDict = () };
		argDict[\out] ?? { argDict[\out] = outbus ? 0 };
		argDict[\fadeTime] ?? { argDict[fadeTime] = fadeTime ? 0.02 };
		^argDict.keys.asArray.collect({ | key | [key, argDict[key]] }).flat;
	}

	getParametersFrom { | old |
		old !? {
		super getParametersFrom: old;
		outbus = old.outbus ? 0;
		fadeTime = old.fadeTime ? 0.01;
		};
	}
}

// NOTE: The internal code is independent from that of NodeTemplate,
// but keeping it as subclass for consistency with \symbol.ndef etc.
PatternTemplate : PlayerTemplate { // for EventStreams

	init { | argSource | // argSource rarely used
		// Symbol:pdef does the merging with mergeEnvir.
		// var sourceHasChanged;
		// initialise an empty stream upon creation.
		source ?? { source = this.defaultSource };
		process = source;
		// mergin is done
		// argSource ?? { argSource = () };
		// source mergeEvent: argSource;
	}

	defaultSource { ^EventStream(()) } 	// start with an empty stream.

	mergeEnvir { | argEnvir |
		source mergeEvent: (argEnvir ?? { () });
	}

	handleReplacement { | newValue, envir, key |
		// do not do anything if replaced by self
		if (newValue === this) {} { this.stop; }
	}

	play { // do not return EventStream. Return self.
		// this.isPlaying.if { ^process } { ^this.prPlay }
		this.isPlaying.if { process } { this.prPlay }
	}

	prPlay { ^process.start; }

	stop {
		this.isPlaying.if { this.prStop };
	}

	prStop { process.stop }
	clear { source.clear }
	outbus { ^source.outbus }
	fadeTime { ^source.fadeTime }
	set { | inEvent |
		source mergeEvent: inEvent;
	}
}
