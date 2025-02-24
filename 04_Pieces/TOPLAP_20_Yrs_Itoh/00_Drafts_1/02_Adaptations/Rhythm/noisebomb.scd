(
SynthDef(\noisebomb, {arg freq=440, gate=1, mul=1, out=0;
	var osc, env, fil1, fil2, fil3;

	env=Env.perc(0.1, 1, 0.5, -6).kr(2, gate);

	osc=PinkNoise.ar(env*0.01)!7;
	fil1=LPF.ar(osc, freq+SinOsc.ar(41, 0, 500)+SinOsc.ar(121,0,500), 0.2);
	fil2=HPF.ar(fil1, freq-20+SinOsc.ar(128, 0, 500), 0.2);
	Out.ar(out, fil2*mul);

}).add
)

Synth(\noisebomb,[\freq,200,\mul,0.02]);
//:
(
var durations;
durations = [1/8, 1/16, 1/8, 1/8, 1/16, 1/16, 1/8, 1/8, 1/4, 1/3, 1/3, 1/3, 1/4, 1/4, 1/4];
    Pbind(*[
        instrument: \noisebomb,

	    out: Pseq([0,1,2,3,4,5,6],inf),

        freq: 400,

        dur: Prand(durations, inf),


]).play(TempoClock(120 / 60));
);
//:
