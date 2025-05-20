// 月 19  5 2025 19:21
// You can now map control synths to any synth directly.
// Here is how:

//Note: you can make synth a with { Function }.play or Synth(...)

a = { SinOsc.ar(\freq.kr(400), 0, \amp.kr(0.02)) }.play;
// Long forM
a.addCtl(\freq, { LFNoise0.kr(10).range(100, 900) });
a.addCtl(\amp, { LFNoise0.kr(10).range(0.01, 0.3) });
a.unmap(\freq); // remove control for frequency
// add other controls. Each new synth replaces the old one:
a.addCtl(\freq, { LFNoise1.kr(10).range(100, 900) });
a.addCtl(\freq, { LFNoise2.kr(10).range(100, 900) });
a unmap: \amp;

// Short forms:
// 1. c = addCtl
a.c(\freq, { LFNoise2.kr(1).range(100, 900) });
// 2. c_ = unmap (remove control)
a c_: \freq; //
//
a.c(\freq, { LFNoise2.kr(20).range(150, 200) })
a.set(\freq, 400); // set unmaps - the old synth is left running
