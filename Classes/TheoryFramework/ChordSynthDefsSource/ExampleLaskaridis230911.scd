# Giorgos-Laskaridis

s.boot;
s.reboot;

a = [0, 2, 4, 6, 7, 6, 4, 2] // main melody sequence
t = TempoClock(41.65/60).permanent_(true); // creates a standard tempo

(
SynthDef.new(\kick,{
	var freqA=250, freqB=20, freqC=10, freqDur1=0.01, freqDur2=0.2, freqC1=1, freqC2=(-1),
	atk=0.01,rel=1,c1=1,c2=(-12),amp=3.3,pan=0,out=0;
	var sig, env, freqSweep;
	freqSweep = Env([freqA, freqB, freqC], [freqDur1,freqDur2], [freqC1, freqC2]).ar;
	env = Env([0,1,0], [atk,rel], [c1,c2]).kr(2);
	sig = SinOsc.ar(freqSweep, pi/2);
	sig = sig * env;
	sig = Pan2.ar(sig,pan,amp);
	Out.ar(out,sig);
}).add;
)

SynthDef(\snare, {
    var sig, freq;
	var amp = 0.5;
    freq = 230 * (1 + (Env.perc(0.001, 0.03).ar * 2));
    sig = SinOsc.ar(freq + (200 * SinOsc.ar(freq * 1.2))) * Env.perc(0.001, 0.2).ar;
    sig = sig + (BPF.ar(Hasher.ar(Sweep.ar), 1000, 0.3) * Env.perc(0.1, 0.3).ar);
    sig = sig.tanh;
    sig = sig * Env.perc(0.001, 5).ar(Done.freeSelf);
    sig = sig ! 2;
	sig = sig + (FreeVerb.ar(sig,1, 0.5, 0.5));
    sig = sig * \amp.kr(1);
    Out.ar(\out.kr(0), sig);
}).add;

//------------------------------------------------------------------------------------------------------------------------

(
{
//main melody
Pbind(
	\degree, Pseq(a, inf),
	\dur, 0.18
).play;

//bass
Pbind(
	\degree, Pseq ([0,0,0,0,1, 2, 2, 2, 2,1], inf),
    \ctranspose, -24,
	\dur, Pseq([1.44,1.44,1.44,0.72,0.72,1.44,1.44,1.44,0.72,0.72], inf),
	\amp, 0.9,
	\legato, 1
).play;

//low mel
Pbind(
	\degree, Pseq(a, inf),
    \ctranspose, -12,
	\dur, 0.18,
	\amp, 0.3
).play;

11.52.wait;

//kick
Pbind(
	\instrument, \kick,
		\dur, Pseq ([1.1, 0.34, 1.44], inf),
	\amp, 5
).play; //tempo clock

0.72.wait;

//snare
Pbind(
	\instrument, \snare,
	\amp, 0.2
	).play(t);
}.fork;
)

//------------------------------------------------------------------------------------------------------------------------