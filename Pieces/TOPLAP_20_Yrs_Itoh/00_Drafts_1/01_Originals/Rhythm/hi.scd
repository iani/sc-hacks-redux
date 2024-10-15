(
SynthDef(\hi, {arg freq=440, gate=1, mul=1, out=0, rq=0.1;
	var osc, env, fil, fil2, mod, las, lim;
	env=Env.linen(0.01, 0.04, 0.03, 100, -4).kr(2,gate);
	osc=Dust2.ar(400,env*200).distort*5!2;
	fil=HPF.ar(osc,freq-500,env).distort*0.4;
	fil2=BPF.ar(osc,freq, rq, 1000).distort*0.9;


	Out.ar(out,fil2*mul);
}).add;
)

Synth(\hi,[\freq,20000,\mul,0.1]);

(
var durations;
durations = [1/2, 1/4, 1/8, 1/16 ];
    Pbind(*[
        instrument: \hi,

	    out: 0,

	    rq: Prand([0.1, 0.5, 1, 2],inf),

	    freq: Prand([1000, 3000, 5000, 7000, 9000, 11000],inf),

	    mul: 0.4,

        dur: Prand(durations, inf),


]).play(TempoClock(140 / 60));
)


(
SynthDef("help-Dust", { arg out=0;
    Out.ar(out,
        Dust.ar(10, 0.5)
    )
}).play;
)



	