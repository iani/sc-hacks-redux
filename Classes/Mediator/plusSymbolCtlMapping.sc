// 水 21  5 2025 19:49
// Mapping methods for Symbol.
// These operate only on NodeTemplate or Synth.
/* // test if Symbol already has any of the methods
	// method list for testing before adding the methods:


*/
+ Symbol {
	rc { | ... keys |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.rc(*keys) }
	}

	remap { | key |  // only works if synth is the same as before unmap
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.remap(key) }
	}

	remapAll { // only works if synth is the same as before unmap
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.remapAll }
	}

	c { | ... argFuncPairs |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.c(*argFuncPairs) }
	}
	ctl { | ... argFuncPairs |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.ctl(*argFuncPairs) }
	}
	add1Ctl { | ctlname, synthfunc |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.add1Ctl(ctlname, synthfunc) }
	}
	c_ { | ... ctlnames |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.c_(*ctlnames) }
	}
	u { | ... ctlnames |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.u(*ctlnames) }
	}
	unmap { | ... ctlnames |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.unmap(*ctlnames) }
	}
	cS { | ctlname |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.cS(ctlname) }
	}
	getCtlSynth { | ctlname |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.getCtlSynth(ctlname) }
	}
	removeCtl { | ctlname |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.removeCtl(ctlname) }
	}
	cc_ { | ctlname |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.cc_(ctlname) }
	}
	stopCtl { | ctlname |
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.stopCtl(ctlname) }
	}
	freeCtls {
		var s;
		s = currentEnvironment[this];
		if (s isKindOf: Synth or: { s isKindOf: NodeTemplate })
		{ s.freeCtls }
	}

}