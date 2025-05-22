
// Mapping NodeTemplate synths
// Test 水 21  5 2025 19:48
// Create a synth to control and store it in a

\a play: { SinOsc.ar(\freq.kr (400), 0, \amp.kr(0.02)) };
\a play: { LFSaw.ar(\freq.kr(600), 0, \amp.kr(0.02)) };

~a.process;

b = Library.at(~a.process)[\vars];
c = b[\freq];
c.synth;
// map control \freq to a synth synth made from a func
\a.ctl(\freq, { LFNoise0.kr(10).range(100, 900) });

// Shortcut for ctl: c
\a.c(\freq, { LFNoise1.kr(10).range(800, 900) });

// Adding multiple controls with list of key-function pairs
\a play: { SinOsc.ar(\freq.kr(600), 0, \amp.kr(0.02)) };
//: with ctl:
\a.ctl(\freq, { LFNoise1.kr(1).range(1080, 2090) },
	\amp, { LFSaw.kr(7).range(0, 0.1)}
);
//: with c
\a.c(\freq, { LFNoise0.kr(4).range(1080, 2090) },
	\amp, { SinOsc.kr(1).range(0, 0.1)}
);
\a play: { SinOsc.ar(\freq.kr(610), 0, \amp.kr(0.02)) };

// undoing controls (3 different ways: set, unmap, stopCtl)
// set control \freq to 400. this unmaps the control
// but the control synth keeps running
\a.set(\freq, 400);

// Note: a.set requires that you provide the value to set.
// This can be different from the current value of the parameter,
// which results in a click-like jump.
// unmap: unmap control: unmap the bus, but keep the control synth

// To keep the latest value of a parameter set by a control synth,
// use unmap
\a play: { SinOsc.ar(\freq.kr(400), 0, \amp.kr(0.02)) };
\a.c(\freq, { LFNoise1.kr(1).range(1080, 2090) });
\a.unmap(\freq); // disconnect from bus
\a.u(\freq, \amp); // u shortcut for unmap
\a.rc(\freq); // remap to bus
\a.rc(\amp, \freq);
//: remapAll unmapped controls
\a.remapAll;

\a.u(\freq, \amp); // u shortcut for unmap


/// 木 22  5 2025 19:44 connection breaks. Why?
\a play: { SinOsc.ar(\freq.kr(400), 0, \amp.kr(0.03)) };

// Unmap keeps the control synth running, so it can be used
// again with remap (or also mapped to another synth?)
// use rc :
\a play: { SinOsc.ar(\freq.kr(400), 0, \amp.kr(0.02)) };
\a.c(\freq, { LFNoise0.kr(30).range(280, 2090) });
\a.unmap(\freq);
\a.rc(\freq); // reconnect: remaps to existing control synth
// shortcuts:
\a.u(\freq); // disconnect from control
\a.rc(\freq); // reconnect: remaps to existing control synth

// shortcut for unmap: c_
\a play: { SinOsc.ar(\freq.kr(400), 0, \amp.kr(0.02)) };
\a.c(\freq, { LFNoise1.kr(3).range(1080, 2090) });
\a.c_(\freq);

// Stopping the control synth:
// stopCtl stops the control synth - but does not unmap
\a play: { SinOsc.ar(\freq.kr(401), 0, \amp.kr(0.02)) };
a.c(\freq, { LFNoise0.kr(10).range(1080, 2090) });
a.stopCtl(\freq);

// shortcut for stopCtl: cc_
\a play: { SinOsc.ar(\freq.kr(420), 0, \amp.kr(0.02)) };
\a.c(\freq, { LFNoise0.kr(14).range(1080, 2090) });
\a.cc_(\freq);

// Free all control synths: freeCtls
\a play: { SinOsc.ar(\freq.kr(400), 0, \amp.kr(0.02)) };
\a.c(\freq, { LFNoise0.kr(4).range(1080, 2090) });
\a.c(\amp, { LFSaw.kr(10).range(0, 0.1) });
\a.freeCtls; // frees control synths and their MapBus, CtlSynth


// Getting a control synth.  This is useful for
// for adding controls to control synths
// ("higher order control mapping")
// <synth>.getCtlSynth
a = { SinOsc.ar(\freq.kr(400), 0, \amp.kr(0.02)) }.play;
a.c(\freq, { LFNoise0.kr(4).range(1080, 2090) });
a.getCtlSynth(\freq);
// shortcut for getCtl: cs
a.cS(\freq); // not to overwrite Object:cs!
a.unmap(\freq); // unmaps input but keeps control synth
a.stopCtl(\freq); // stops control synth


//========== Higher order mapping example ==========
a = { SinOsc.ar(\freq.kr(400), 0, \amp.kr(0.02)) }.play;
//:
a.c(\freq, {
	LFNoise0.kr(\rate.kr(4))
		.range(\lo.kr(1080), \hi.kr(2090))
});
//: cS : shortcut for getCtlSynth
// Note: do not overwrite Object:cs ...
a.cS(\freq).c(\lo, { SinOsc.kr(10).range(100, 400) })
