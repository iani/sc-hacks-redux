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
		// if synthFunc is nil, return existing ctlSynth
		synthFunc ?? { ^this };
		this.stop;
		ctlSynth = synthFunc.play(outbus: this.index).register;
	}

	stop {
		ctlSynth.isPlaying.if { ctlSynth.free };
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
	var <synth, <ctlName, <ctlSynth;

	*new { | synth, ctlName, ctlSynth |
		^this.newCopyArgs(synth, ctlName, ctlSynth);
	}

	bus { ^ctlSynth.bus }
	index { ^ctlSynth.index }
	play { | synthfunc |
		ctlSynth play: synthfunc;
		synth.map(ctlName, ctlSynth.index);
	}

	stop { ctlSynth.stop; }

	// stop control synth and free bus
	// DANGER: this may stop control synths controlling other synths
	free { ctlSynth.free }

	isPlaying { ^ctlSynth.isPlaying }
}

+ Synth {
	// get bus, play synth func into it, map it to ctlname

	// shortcuts - also for chaining to higher-order control
	c { | ctlname, synthfunc |
		synthfunc.isNil.if {
			^this.getCtl.ctlSynth.ctlSynth;
		}{
			^this.addCtl(ctlname, synthfunc);
		}
	}

	c_ { | ctlname |
		this.unmap(ctlname);
	}

	// unmap in a safe way: set control to current bus value
	unmap { | ctlname |
		var ctl;
		ctl = this getCtl: ctlname;
		ctl !? {
			ctl.bus get: { | val | this.set(ctlname, val);};
		}
	}

	// return ctl if it exists, else return nil
	getCtl { | ctlname | ^this getVar: ctlname }

	addCtl { | ctlname, synthfunc |
		var ctl;
		ctl = this getCtl: ctlname;
		ctl.isNil.if {
			ctl = MapBus(this, ctlname, CtlSynth.fromFunc(synthfunc));
			this.map(ctlname, ctl.index);
			this.putVar(ctlname, ctl);
		}{
			ctl play: synthfunc;
		};
		this.map(ctlname, ctl.index);
		^ctl.ctlSynth.ctlSynth;
	}

	// remove control instance
	// do not stop control synth, as it may be controlling other synths
	removeCtl { | ctlname | this.removeValue(ctlname); }
	stopCtl { | ctlname |
		// stop synth but do not remove bus or unmap
		var ctl;
		ctl = this getVar: ctlname;
		ctl !? { ctl.stop; }
	}

	// stop all control synths and free their buses
	// DANGER: this may stop control synths controlling other synths
	freeCtls {
		this.getVarDict keysValuesDo: { | key, ctl |
			ctl.free;
			this removeVar: key;
		}
	}
}