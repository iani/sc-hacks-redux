(
SynthDef(\fmbass, {arg freq=440, modfreq=1.5, modfreq2=1.5, gate=1, mul=1, modmul=10, out=0;
	var env, oscmod, oscmod2, osccar, fil;

	env=Env.linen(0.01, 0.1, 6, 1, -50).kr(2, gate);
	oscmod=SinOsc.ar(freq*modfreq, 0, modmul);
    oscmod2=SinOsc.ar(freq*modfreq2, 0, modmul);
	osccar=SinOsc.ar(freq+oscmod+oscmod2, 0, env*mul)!7;

	Out.ar(0, osccar+SinOsc.ar(freq,0,env*mul));
}).add;
)
(
Synth(\fmbass, [\freq,15000,\mul,0.5,\modfreq,1.498, \modfreq2, 3.323, \modmul,40]);
)
(
Synth(\fmbass, [\freq,40,\mul,2,\modfreq,1.298, \modfreq2, 4.423, \modmul,20]);
)

(
Pbind(*[
	\mul: 0.15,
	\instrument: \fmbass,
	\freq: 55,
	\modfreq,1.898,
	\modfreq2, 2.123,
	\dur: Prand([Rest(1/2),1,Rest(1/2),1/2,Rest(1/4),3/4,Rest(1),3/2],inf)
]).play
)

{Out.ar(0, SinOsc.ar(53, 0, 0.3)!2)}.play;




	