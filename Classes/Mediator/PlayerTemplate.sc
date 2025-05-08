// 火  6  5 2025 01:21
// Templates for Synth or Stream that are waiting to start playing

PlayerTemplate : NamedSingleton2 { // neutral.  source specifies default behavior
	classvar permanent; // any instances added here will restart after CmdPeriod
	var <>source;
	var <process; // the running process of this player. A Synth or EventStream.
	var <argDict; // accumulate arg settings
	// in FunctionNodeTemplate, and NodeTemplate
	init { | argSource |
		source = argSource ?? { this.defaultSource };
	}

	defaultSource { ^\default }
	defaultArgs { ^Array.new }

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
}

NodeTemplate : PlayerTemplate { // for synths
	var <args, <target, <addAction = \addToHead;
	// var sourceHistory; // for checking if source has changed and must restart;
	defaultSource { ^\default }

	init { | argSource, argArgs, argTarget, argAddAction = \addToHead |
		var sourceHasChanged;
		// TODO: Revisit this: what to do when previous source or new source are nil???
		// ????????????????????????????????????????????????????????
		// TODO: if source is nil, play silent synth.
		source ?? { source = this.defaultSource };
		// TODO: empty source means play silence, or update args????
		argSource ?? { argSource = this.defaultSource };
		sourceHasChanged = this.sourceHasChanged(argSource);
		source = argSource;
		target = argTarget.asTarget;
		addAction = argAddAction;
		// postln("Checking args before merging. argDict is:" + argDict + "args are:" + args);
		args = (argDict ?? { argDict = (); }) mergeArgs: argArgs;
		// postln("Checking args AFTER merging. argDict is:" + argDict + "args are:" + args);
		// if (this.isPlaying) { this.stop; this.play; };
		(this.isPlaying and: sourceHasChanged).if {
			this.prStop;
			this.prPlay;
			// "Will restart in 1".postln;
			// { this.play; }.defer(0.5);
		}{
			// postln("Checking args before resending. args are:\n" ++ args);
			this.resendArgs;
		}
	}
	sourceHasChanged { | argSource |
		var prevdef, thisdef;
		// postln("confirming: this is compareSources of class: NodeTemplate");
		prevdef = source; // .def.sourceCode;
		thisdef = argSource; // (argSource ?? { {"x"} }).def.sourceCode;
		// "---------".postln;
		// ("prevdef: " + prevdef).postln;
		// "---------".postln;
		// ("thisdef: " + thisdef).postln;
		// postln("-------- thisdef == prevdef?" + (thisdef == prevdef));
		// postln("debugging sourceHasChanged NodeTemplate. has it changed?" + (thisdef == prevdef).not);
		^(thisdef == prevdef).not
	}

	resendArgs {
		// TODO: FunctionNodeTemplate may want to send outbus and fadeTime, too!
		// postln("resend args for" + this + "args are" + args);
		if (this.isPlaying) { process.set(*args) }
	}

	play { // do not return Synth. Return self.
		// this.isPlaying.if { ^process } { ^this.prPlay }
		this.isPlaying.if { process } { this.prPlay }
	}

	prPlay { ^process = Synth(source, args, target, addAction).register; }

	stop {
		// postln("Stopping:" + this);
		// postln("Is it playing?" + this.isPlaying);
		// this.isPlaying.if { "I will stop".postln } { "I will not stop".postln; };
		this.isPlaying.if { this.prStop };
	}

	prStop { process.stop }

	clear { // empty arguments
		args = []; // NOTE: keep argDict? What for?
	}
	updateProcessControls {
		this.isPlaying.if {
			process.set(*args);
			// this.moveSynth;
		}
	}

	// TODO: move synth to new position of addAction and new target if needed
	// moveSynth {  }

}

FunctionNodeTemplate : NodeTemplate { // for synths
	// NOTE: Explicitly changing order of arguments from that of
	// Function:play, because placing args at the end is dumb.
	// the order here is chosen on purpose
	// Copying from Synth:play and adding outbuss and fadeTime last!
	var <outbus = 0, <fadeTime = 0.02;
	// original:
	// play { arg target, outbus = 0, fadeTime = 0.02, addAction=\addToHead, args;
	// from compiled sc-hacks-redux (may be the same!)
	// play { arg target, outbus = 0, fadeTime = 0.02, addAction=\addToHead, args;
	// TODO: Resolve this outbus vs out inconsistency in Function:asDef
    defaultSource {
		// "defaultSource of FunctionNodeTemplate creates a function".postln;
		^{ | outbus = 0, freq = 400, amp = 0.1 |
			SinOsc.ar(freq, 0, amp).dup;
		}
	}
	init { | argSource, argArgs, argTarget, argAddAction = \addToHead,
		argOutbus = 0, argFadeTime = 0.01 |
		postln(
			"argSource" + argSource + "argArgs" + argArgs
			+ argTarget + argTarget + "argOutbus" + argOutbus + "argFadeTime" + argFadeTime
		);
		if (argSource.isNil) { ^this }; // ignore empty sources
		// postln("CHecking restart. argsource != source:" + (argSource != source));
		// postln("this.isPlaying:" + this.isPlaying);
		// postln("argsource is different + this is playing" + ((argSource != source) and: { this.isPlaying }));
		// restart = (argSource != source) and: { this.isPlaying };
		outbus = argOutbus;
		fadeTime = argFadeTime;
		super.init(argSource, argArgs, argTarget, argAddAction);
		// source = argSource ?? { this.defaultSource };
	}
	sourceHasChanged { | argSource |
		var prevdef, thisdef;
		// postln("confirming: this is compareSources of class: FunctionTemplate");
		prevdef = source.def.sourceCode;
		thisdef = argSource.def.sourceCode;
		// "---------".postln;
		// prevdef.postln;
		// "---------".postln;
		// thisdef.postln;
		// postln("-------- thisdef == prevdef?" + (thisdef == prevdef));
		// postln("debugging sourceHasChanged FunctionNodeTemplate. has it changed?" + (thisdef == prevdef).not);
		^(thisdef == prevdef).not
	}
	// play {
	// 	this.isPlaying.if { ^process } { ^this.prPlay }
	// }

	prPlay {
		^process = source.play(target, outbus, fadeTime, addAction, args).register;
	}

	// updateProcessControls {
		// super.updateProcessControls;
		// TODO: Implement these
		// this.setOutbus;
		// this.setFadeTime;
	// }

	// TODO: Implement these
	// setOutbus {}
	// setFadeTime {}
}


PatternTemplate : PlayerTemplate { // for EventStreams

	init { | argSource | // argSource rarely used
		// Symbol:pdef does the merging with mergeEnvir.
		var sourceHasChanged;
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
}
