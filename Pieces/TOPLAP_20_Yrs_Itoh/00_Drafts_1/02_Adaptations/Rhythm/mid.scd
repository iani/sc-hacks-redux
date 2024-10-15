(
SynthDef(\mid, { arg freq = 60, gate=1, mul=1, out=0;
    var env, sig, env2, fil, fil2, filmod, reverb;

	env=Env.perc(0.6, 0.01, 1, 5).kr(2,gate);

	sig=WhiteNoise.ar(env).distort*1!2;

	filmod=SinOsc.kr(1/3,5).range(1, 10);
	fil=BPF.ar(sig, freq*filmod, 0.1, 2);
	fil2=HPF.ar(fil, 1000, 2);
	reverb=FreeVerb.ar(fil2, 1, 2, 4, 20);


	Out.ar(out, reverb*mul);

}).add;
)

Synth(\mid,[\out,0,\mul,0.1]);




(
var durations;
durations = [1/16, 1/16, 1/16, 1/16, Rest(1), 1, 1/4,Rest(2/4), 1/2, 1/2];
    Pbind(*[
        instrument: \mid,
	    out: Prand([0,1],inf),

        freq: 60,

        dur: Pseq(durations, inf),


]).play(TempoClock(125 / 60));

   Pbind(*[
        instrument: \kick,
        freq: 60,
		mul: 0.6,
	dur: Pseq([1,1,1,1], inf),
]).play(TempoClock(125 / 60));

   Pbind(*[
		instrument: \addnoise,
		freq: 6000,
	dur: Pseq([Rest(2/3),1/3, Rest(2), Rest(1/2), 1/4, 1/4], inf),
	    mul: 0.3
]).play(TempoClock(125 / 60));
)












(
r = Routine({
    var delta;
    loop {
        delta = rrand(1, 3) * 0.5;
        Synth(\singrain, [freq: exprand(200, 800), amp: rrand(0.1, 0.5), sustain: delta * 0.8]);
        delta.yield;
    }
}).play;
)