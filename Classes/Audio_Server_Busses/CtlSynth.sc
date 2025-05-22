//月 19  5 2025 12:09
// Add a control synth writing to a control bus
// and map the control of this synth to that bus.

// Hold the bus and the synth that writes to it.
// Play functions into that bus. Playing a new function
// replaces the previous control synth.
// NOTE: a CtlSynth can be mapped to more than one synths
// Therefore the control name is stored in separate instance, MapBus

CtlSynth {
	var <bus, <ctlSynth;
	*fromFunc { | synthFunc |
		^this.new(Bus.control).play(synthFunc);
	}
	*new { | bus |
		^this.newCopyArgs(bus);
	}

	play { | synthFunc |
		// if synthFunc is nil, do nothing. allows remap
		synthFunc ?? { ^this };// maybe remove this
		// remap is done in MapBus!
		this.stop;
		ctlSynth = synthFunc.play(outbus: this.index).register;
		// ctlSynth onEnd: {
		// 	// postln("Synth ended:" + ctlSynth);
		// 	ctlSynth = nil;
		// };
	}

	stop {
		// postln("debugging stop. ctlSynth" + ctlSynth
		// 	+ "isPlaying" + ctlSynth.isPlaying;
		// );
		ctlSynth.isPlaying.if {
			VarHolder removeObject: ctlSynth;
			ctlSynth.free;
		};
		ctlSynth = nil;
	}

	index { ^bus.index }
	// stop all control synths and free bus
	// DANGER: The control ynth will no longer control other synths
	free { ctlSynth.free; bus.free; }
}

// Map a control synth to a parameter in a source synth
// Hold the synth controled, the control name, and the controlSynth
// The controlSynth holds both the bus and the controlling Synth instance.
MapBus {
	var <>synth, <ctlName, <ctlSynth;

	*new { | synth, ctlName, ctlSynth |
		// postln("creating MapBus. synth is" + synth +
		// 	"ctlname is" + ctlName
		// 	+ "ctlSynth is" + ctlSynth
		// );
		^this.newCopyArgs(synth, ctlName, ctlSynth);
	}

	bus { ^ctlSynth.bus }
	index { ^ctlSynth.index }
	play { | synthfunc |
		var theSynth;
		// if no synthFunc provided, remap!
		synthfunc !? {
			ctlSynth play: synthfunc;
			ctlSynth.ctlSynth.onStart({
				// postln("started synth" +
				// 	ctlSynth.ctlSynth + "MAPPING!");
				// "\n\nI GOT IT. CLOSE CHECKING 2 NODES\n".postln;
				// postln("the CONTROLLING synth is" + ctlSynth.ctlSynth);
				// postln("the CONTROLLED synth is" + synth);
				synth.map(ctlName, ctlSynth.index);
			})
		};
	}

	stop { ctlSynth.stop; }

	// stop control synth and free bus
	// DANGER: this may stop control synths controlling other synths
	free { ctlSynth.free }

	isPlaying { ^ctlSynth.isPlaying }
}

+ Synth {
	// get bus, play synth func into it, map it to ctlname

	// shortcuts
	rc { | ... keys | // reconnect
		keys do: { | key | this remap: key }
	}

	remap { | key | // remap to existing control synth/bus
		var ctl;
		ctl = this getCtl: key;
		ctl !? { this.map(key, ctl.bus.index); }
	}

	remapAll {
		this.rc(*this.vars.keys.asArray)
	}
	// TODO: c, ctl, addCtl should share code
	//
	c { | ... argFuncPairs |
		// argFuncPairs keysValuesDo: { | ctl, func |
		// 	this.addCtl(ctl, func);
		// };
		this.ctl(*argFuncPairs);
	}

	ctl { | ... argFuncPairs |
		// why doesn't this work ????:
		// this.addCtl(*argFuncPairs);
		argFuncPairs keysValuesDo: { | ctl, func |
			this.add1Ctl(ctl, func);
		};
	}

	add1Ctl { | ctlname, synthfunc |
		var ctl;
		// "Debugging add1Ctl".postln;
		ctl = this getCtl: ctlname;
		// postln("ctl is" + ctl);
		ctl.isNil.if {
			// "ctl is nil so I make a new one".postln;
			ctl = MapBus(this, ctlname, CtlSynth.fromFunc(synthfunc));
			// postln("ctl is now" + ctl);
			// postln("I will map this" + this +
			// 	"with ctlname" + ctlname + "and ctl" + ctl
			// );
			// postln("ctl is" + ctl + "ctl.index is" + ctl.index);
			this.map(ctlname, ctl.index);
			// postln("I will now put in Var" + ctlname + "value" + ctl);
			this.putVar(ctlname, ctl);
		}{
			// postln("ctl is not nil. it is" + ctl);
			// postln("i will play into it synthfunc" + synthfunc);
			ctl play: synthfunc;
		};
		// postln("now mapping ctlname" + ctlname + "to ctl" + ctl);
		// postln("ctl.index is" + ctl.index);
		this.map(ctlname, ctl.index);
		// postln("returning ctl.ctlSynth.ctlSynth. ctl" + ctl);
		// postln("ctl.ctlSynth" + ctl.ctlSynth);
		// postln("ctl.ctlSynth.ctlSynty" + ctl.ctlSynth.ctlSynth);
		^ctl.ctlSynth.ctlSynth;
	}

	// unmap in a safe way: set control to current bus value
	c_ { | ... ctlnames | this.unmap(*ctlnames); }
	u { | ... ctlnames | this.unmap(*ctlnames); }
	unmap { | ... ctlnames |
		ctlnames do: { | cn | this unmap1: cn }
	}
	unmap1 { | ctlname |
		var ctl;
		ctl = this getCtl: ctlname;
		ctl !? {
			ctl.bus get: { | val | this.set(ctlname, val);};
		}
	}

	// return ctl synth if it exists, else return nil
	// cS : do not overwrite Object:cs
	cS { | ctlname | ^this.getCtlSynth(ctlname) }
	getCtlSynth { | ctlname | ^this.getCtl(ctlname).ctlSynth.ctlSynth }
	getCtl { | ctlname | ^this getVar: ctlname }

	// remove control instance
	// do not stop control synth, as it may be controlling other synths
	removeCtl { | ctlname | this.removeValue(ctlname); }

	// stop synth but do not remove bus or unmap
	cc_ { | ctlname | this.stopCtl(ctlname); }
	stopCtl { | ctlname |
		var ctl;
		ctl = this getCtl: ctlname;
		ctl !? { ctl.stop; }
	}

	// stop all control synths and free their buses
	// DANGER: this may stop control synths controlling other synths
	freeCtls {
		// this.vars keysValuesDo: { | key, ctl |
		// 	[key, ctl].postln;
		// };
		var vars;
		vars = this.vars;
		vars keysValuesDo: { | key, ctl |
			ctl.free;
			vars[key] = nil;
		};
		((vars.size) == 0).if { this.removeVarDict }
	}
}