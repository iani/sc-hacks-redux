(
SynthDef("carrier", { arg inbus = 2, outbus = 0, freq = 440, carPartial = 1, index = 3, gate = 1;

    var mod;
	var env;

	env=Env.pairs([[0,0],[2,0.1],[4,0.01],[6,0.2],[9,0.2],[13,0]],-1).kr(2,gate);

    mod = In.ar(inbus, 1);

    Out.ar(
        outbus,
        SinOsc.ar((freq * carPartial) + mod, 0, env);
    )
}).add;

SynthDef("modulator", { arg outbus = 2, freq, modPartial = 1, index = 3;
    Out.ar(
        outbus,
        SinOsc.ar(freq * modPartial, 0, freq)
        *
        LFNoise1.kr(Rand(3, 6).reciprocal).abs
        *
        index
    )
}).add;





a=Synth("modulator", [\outbus, 14, \freq,{rrand(40,1000)}, \modPartial, 0.649, \index, 3],~modsources,\addToHead);

b=Synth("modulator", [\outbus, 14, \freq,{rrand(40,1000)}, \modPartial, 1.599, \index, 4],~modsources,\addToHead);


c=Synth("modulator", [\outbus, 15, \freq, {rrand(40,1000)}, \modPartial, 0.729, \index, 4.43],~modsources,\addToHead);

d=Synth("modulator", [\outbus, 15, \freq, {rrand(40,1000)}, \modPartial, 2.819, \index, 2.24],~modsources,\addToHead);


e=Synth("modulator", [\outbus, 16, \freq, {rrand(40,1000)}, \modPartial, 0.109, \index, 1.53],~modsources,\addToHead);

f=Synth("modulator", [\outbus, 16, \freq, {rrand(40,1000)}, \modPartial, 1.029, \index, 3.14],~modsources,\addToHead);


g=Synth("modulator", [\outbus, 17, \freq, {rrand(40,1000)}, \modPartial, 0.621, \index, 4.83],~modsources,\addToHead);

h=Synth("modulator", [\outbus, 17, \freq, {rrand(40,1000)}, \modPartial, 1.031, \index, 1.24],~modsources,\addToHead);


i=Synth("modulator", [\outbus, 18, \freq, {rrand(40,1000)}, \modPartial, 0.422, \index, 3.03],~modsources,\addToHead);

j=Synth("modulator", [\outbus, 18, \freq, {rrand(40,1000)}, \modPartial, 3.209, \index, 2.49],~modsources,\addToHead);


k=Synth("modulator", [\outbus, 19, \freq, {rrand(40,1000)}, \modPartial, 1.729, \index, 5.02],~modsources,\addToHead);

l=Synth("modulator", [\outbus, 19, \freq, {rrand(40,1000)}, \modPartial, 3.802, \index, 2.44],~modsources,\addToHead);


m=Synth("modulator", [\outbus, 20, \freq, {rrand(40,1000)}, \modPartial, 0.129, \index, 3.13],~modsources,\addToHead);

n=Synth("modulator", [\outbus, 20, \freq, {rrand(40,1000)}, \modPartial, 4.619, \index, 2.70],~modsources,\addToHead);




SystemClock.sched(0.0,{Synth("carrier", [\inbus, 14, \outbus, 7, \freq, {rrand(40,1000)}, \carPartial, 1],~carsources); rrand(4,10);});

SystemClock.sched(0.0,{Synth("carrier", [\inbus, 15, \outbus, 8, \freq, {rrand(40,1000)}, \carPartial, 0.97],~carsources);rrand(4,10);});

SystemClock.sched(0.0,{Synth("carrier", [\inbus, 16, \outbus, 9, \freq, {rrand(40,1000)}, \carPartial, 0.82],~carsources);rrand(4,10);});

SystemClock.sched(0.0,{Synth("carrier", [\inbus, 17, \outbus, 10, \freq, {rrand(40,1000)}, \carPartial, 1.24],~carsources);rrand(4,10);});

SystemClock.sched(0.0,{Synth("carrier", [\inbus, 18, \outbus, 11, \freq, {rrand(40,1000)}, \carPartial, 0.81],~carsources);rrand(4,10);});

SystemClock.sched(0.0,{Synth("carrier", [\inbus, 19, \outbus, 12, \freq, {rrand(40,1000)}, \carPartial, 0.60],~carsources);rrand(4,10);});

SystemClock.sched(0.0,{Synth("carrier", [\inbus, 20, \outbus, 13, \freq, {rrand(40,1000)}, \carPartial, 1.87],~carsources);rrand(4,10);});




SynthDef("preDelay", { arg inbus = 7;
    ReplaceOut.ar(
        21,
        DelayN.ar(In.ar(inbus, 7), 0.048, 0.048)
    )
}).add;

SynthDef("combs", {
    ReplaceOut.ar(
        28,
        Mix.arFill(7, { CombL.ar(In.ar(21, 7), 0.1, LFNoise1.kr(Rand(0, 0.1), 0.04, 0.05), 15) })
    )
}).add;

SynthDef("allpass", { arg gain = 0.2;
    var source;
    source = In.ar(28, 7);
    4.do({ source = AllpassN.ar(source, 0.050, Rand(0, 0.05),  1) });
    ReplaceOut.ar(
        35,
        source * gain
    )
}).add;



~preDelay=Synth("preDelay",0,~effects,\addToHead);
~combs=Synth("combs",0,~preDelay,\addAfter);
~allpass=Synth("allpass",0,~combs,\addAfter);



SystemClock.sched(0.0,{a.set(\freq,{rrand(40,1000)},\index,{rrand(1,10)});rrand(5,10);});
SystemClock.sched(0.0,{b.set(\freq,{rrand(40,1000)});rrand(5,10);});
SystemClock.sched(0.0,{c.set(\freq,{rrand(40,1000)},\index,{rrand(1,10)});rrand(5,10);});
SystemClock.sched(0.0,{d.set(\freq,{rrand(40,1000)});rrand(5,10);});
)