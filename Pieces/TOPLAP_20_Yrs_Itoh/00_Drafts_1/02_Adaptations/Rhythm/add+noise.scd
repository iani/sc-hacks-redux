(
SynthDef(\addnoise, {arg freq=440, gate=1, mul=4, out=0;
	var osc, osc2, osc3, osc4, osc5, env, fil, fil2, fil3, fil4;

	env=Env.perc(0.05, 3, 1, -30).kr(2, gate);
	osc=WhiteNoise.ar(env).distort*3!7;
	fil=BPF.ar(osc, freq, 0.5, 1);


	osc2=WhiteNoise.ar(env);
	fil2=BPF.ar(osc, freq+200, 0.3, 2.5);


	osc3=SinOsc.ar(freq+100, 0, env);
	osc4=SinOsc.ar(freq+200, 0, env);
	osc5=SinOsc.ar(freq=300, 0, env);

	fil3=LPF.ar(fil+fil2+osc2+osc3+osc4, freq+1000, 1);
	fil4=HPF.ar(fil3, 4000, 1);



	Out.ar(out, fil4*mul);
}).add;
)

Synth(\addnoise, [\freq,4000,\mul,4]);

	sp.wait(16);


(
Pbind(*[
		instrument: \addnoise,
	    freq: Prand([6000, 8000, 7000],inf),
		dur: Prand([Rest(2/3),1/3, Rest(2), Rest(1/2), 1/4, 1/4], inf),
		mul: 0.3
]).play;
)


	