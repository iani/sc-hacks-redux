(
SynthDef("subbass",{arg out=0,gate=1,freq=50,mul=1;
	var env;

	env=Env.pairs([[0,0],[2,1.1],[4,0.01],[6,2.5],[9,2.5],[15,0]],-2).kr(2,gate);
	Out.ar(out,SinOsc.ar(freq,0,env*mul));
}).add;

//:

SystemClock.sched(0.0,{f=Synth("subbass",[\out,42],~modsources,\addToTail);
	g=Synth("subbass",[\out,43],~modsources,\addToTail);rrand(5,20);});
//:
)

f.set(\gate,0);
(
f.set(\mul,0);
g.set(\mul,0);
)
//:
Synth(\subbass)
//:
\subbass +> () +> \test;
//:
(freq: [50, 500].pwhite) ++> \test;

"test";
//:
OscMonitor.gui;