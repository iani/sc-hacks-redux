(
SynthDef(\newsynth, {arg freq1=440, freq2=440, freq3=440, freq4=440, filfreq1=640, filfreq2=440, gate=1, mul=1, mul1=0.25, mul2=0.15, mul3=300, mul4=0.25, out=0, hpffreq=1;
	var osc1, osc2, osc3, osc4, fil1, fil2, env, lfo, dec;

	t=TempoClock.new(125 / 60);
    t.beats;

	env=Env.linen(10, 3, 10, mul, -5).kr(2, gate);

	osc1=SinOsc.ar(freq1*SinOsc.kr(50, 2, 10), 0, env*mul1);
	osc2=SinOsc.ar(SinOsc.kr(100,0,freq2,0)+SinOsc.kr(135.12,0,freq2*4.2,0)+SinOsc.kr(140.135,0,freq2*2.4,0), 0, env*mul2);
	osc3=SinOsc.ar(freq3*Env.linen(1, 2, 3, 5).kr, 0, env)*mul3;
	osc4=SinOsc.ar(freq4+SinOsc.ar(Env.linen(1, 2, 3, 1000).kr,0,1), 0, env*mul4);

	fil1=LPF.ar(osc1+osc2, filfreq1, 1)!2;
	fil2=HPF.ar(fil1, 300+SinOsc.kr( hpffreq, 200, 200), 80).softclip;

	Out.ar(out, fil2);

}).add;
)

Synth(\newsynth,[\freq1,110,\freq2,100,\freq3,2,\freq4,50,\mul,0.5]);

t=TempoClock.new(240 / 60);
 t.beats;
//:
(
var durations;
durations = [4];
    Pbind(*[
        instrument: \newsynth,
		mul: 0.01,
	    hpffreq: Pseq([1], inf),


]).play(TempoClock(125 / 60));
)
//: