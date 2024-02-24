//:Rhythm - add
SynthDef(\add, {arg freq=440, gate=1, mul=0.1, out=0;
	var sin1, sin2, sin3, sin4, sin5, sin6, allsin, hpf, lpf,env;

	env=Env.perc(0.001, 0.5, 0.1, -5).kr(2,gate);

	sin1=SinOsc.ar(freq, 0, env).distort!3;
	sin2=SinOsc.ar(freq+80.589, 0, env).distort*5;
	sin3=SinOsc.ar(freq+150.522, 0, env).distort*10.5;
	sin4=SinOsc.ar(freq+220.983, 0, env).distort*20.2;
	sin5=SinOsc.ar(freq+313.134, 0, env).distort*30.47;
	sin6=SinOsc.ar(freq+429.134, 0, env).distort*40.17;
	allsin=sin1+sin2+sin3+sin4+sin5+sin6;
	hpf=HPF.ar(allsin, freq+50, 1);
	lpf=LPF.ar(hpf, freq-50, 1);

	Out.ar(out, lpf*mul);
}).add;
//:Rhythm - addnoise
SynthDef(\addnoise, {arg freq=440, gate=1, mul=0.4, out=0;
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
//:Rhythm - distortion
SynthDef(\newsynth, {arg freq1=440, freq2=440, freq3=440, freq4=440, filfreq1=640, filfreq2=440,
	gate=1, mul=0.1, mul1=0.25, mul2=0.15, mul3=300, mul4=0.25, out=0, hpffreq=1;
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

	Out.ar(out, fil2 * mul);

}).add;
//:Rhythm - fmbass
SynthDef(\fmbass, {arg freq=440, modfreq=1.5, modfreq2=1.5, gate=1, mul=1, modmul=10, out=0;
	var env, oscmod, oscmod2, osccar, fil;

	env=Env.linen(0.01, 0.1, 6, 1, -50).kr(2, gate);
	oscmod=SinOsc.ar(freq*modfreq, 0, modmul);
    oscmod2=SinOsc.ar(freq*modfreq2, 0, modmul);
	osccar=SinOsc.ar(freq+oscmod+oscmod2, 0, env*mul)!7;

	Out.ar(0, osccar+SinOsc.ar(freq,0,env*mul));
}).add;
//:Rhythm - hi
SynthDef(\hi, {arg freq=440, gate=1, mul=0.1, out=0, rq=0.1;
	var osc, env, fil, fil2, mod, las, lim;
	env=Env.linen(0.01, 0.04, 0.03, 100, -4).kr(2,gate);
	osc=Dust2.ar(400,env*200).distort*5!2;
	fil=HPF.ar(osc,freq-500,env).distort*0.4;
	fil2=BPF.ar(osc,freq, rq, 1000).distort*0.9;

	Out.ar(out,fil2*mul);
}).add;
//:Rhythm - sequance - [kick]
SynthDef(\kick, {arg freq=50, gate=1, mul=0.2, out=0;
	var env, env2, env3,  env4, imp, imp2, sin, sin2, pul2, saw, white, bpf, lpf, lpf2, lpf3, clip, limiter, comp;

	env=Env.perc(0, 0.01, 3, -4).kr;
	env2=Env.perc(0.0, 0.15, 1.5, -1).kr(2,gate);
	env3=Env.perc(0.0, 0.06, 1, 0).kr;
	env4=Env.perc(0.0, 0.05, 1, 0).kr;


	imp=Impulse.ar(1, 1, 0.5, env, 0)!2;

	sin=SinOsc.ar(freq, 3, env2, 0)!2;

	sin2=SinOsc.ar(100, 3, env3, 0)!2;
	clip=Clip.ar(sin2, -0.6, 0.6);

	imp2=Dust2.ar(20000, env4, 0)!2;
	bpf=BPF.ar(imp2, 5000, 0.35, 0.4, 0).distort*0.03;

	Out.ar(out, imp+sin+clip+bpf*0.3 * mul);
}).add

//:Rhythm - kick1
SynthDef(\kick1, {arg freq=50, gate=1, mul=0.2, out=0;
	var env, env2, env3,  env4, imp, imp2, sin, sin2, pul2, saw, white, bpf, lpf, lpf2, lpf3, clip, limiter, comp;

	env=Env.perc(0, 0.01, 3, -4).kr;
	env2=Env.perc(0.0, 0.15, 1.5, -1).kr(2,gate);
	env3=Env.perc(0.0, 0.06, 1, 0).kr;
	env4=Env.perc(0.0, 0.05, 1, 0).kr;


	imp=Impulse.ar(1, 1, 0.5, env, 0)!2;

	sin=SinOsc.ar(freq, 3, env2, 0)!2;

	sin2=SinOsc.ar(100, 3, env3, 0)!2;
	clip=Clip.ar(sin2, -0.6, 0.6);

	imp2=Dust2.ar(20000, env4, 0)!2;
	bpf=BPF.ar(imp2, 5000, 0.35, 0.4, 0).distort*0.03;

	Out.ar(out, imp+sin+clip+bpf*0.3 * mul);
}).add
//:Rhythm - kick2
SynthDef(\kick2, {arg freq=50, gate=1, mul=0.2, out=0;
	var env, env2, env3,  env4, imp, imp2, sin, sin2, pul2, saw, white, bpf, lpf, lpf2, lpf3, clip, limiter, comp, hi;

	env=Env.perc(0, 0.0, 2.5, 0).kr;
	env2=Env.perc(0, 0.15, 2, 0).kr(2,gate);
	env3=Env.perc(0.0, 0.06, 1.1, 1).kr;
	env4=Env.perc(0.0, 0.05, 1, 1).kr;


	imp=Impulse.ar(1, 1, 0.5, env, 0)!2;

	imp2=Crackle.ar(1.95, Env.perc(0, 0.002, 100, 0).kr)!2;
	limiter=Limiter.ar(imp2, 0.5, 0.0001);

	sin=SinOsc.ar(Env.pairs([[0, freq*2],[0.03, freq]]).kr, 2, env2, 0)!2;

	sin2=SinOsc.ar(100, 3, env3, 0)!2;
	clip=Clip.ar(sin2, -0.6, 0.6);

	hi=WhiteNoise.ar(env4)!2;
	bpf=BPF.ar(hi, 1000, 1, 100000, 0).softclip*0.003;

	Out.ar(out, imp+limiter+sin+clip*0.3 * mul );
}).add
//:Rhythm - mid
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
//:Rhythm - noisebomb
SynthDef(\noisebomb, {arg freq=440, gate=1, mul=1, out=0;
	var osc, env, fil1, fil2, fil3;

	env=Env.perc(0.1, 1, 0.5, -6).kr(2, gate);

	osc=PinkNoise.ar(env*0.01)!7;
	fil1=LPF.ar(osc, freq+SinOsc.ar(41, 0, 500)+SinOsc.ar(121,0,500), 0.2);
	fil2=HPF.ar(fil1, freq-20+SinOsc.ar(128, 0, 500), 0.2);
	Out.ar(out, fil2*mul);

}).add
//:Rhythm - rock
SynthDef(\rock, {arg freq=1000, gate=1, mul=1.5, out=0;
	var env, osc, fil, fil2;

	env=Env.perc(0.01, 0.3, 0.5, -4).kr(2,gate);

	osc=BrownNoise.ar(env)!2;
	fil=LPF.ar(osc, freq-10, 1);
	fil2=HPF.ar(fil, freq+20, 1);
	Out.ar(out, fil2*mul);

}).add;
//:addsynth
SynthDef("addsynth",{arg freq = 40, gate=1, mul = 0.1;
var modulator,carrier,env;

modulator=
	SinOsc.ar(100)+SinOsc.ar(200);

carrier=
	SinOsc.ar(freq, 0, 0.1)   +
    SinOsc.ar(freq+10.567, 0, 0.1) +
    SinOsc.ar(freq+40.489, 0, 0.1) +
    SinOsc.ar(freq+60.9174, 0, 0.1) +
	SinOsc.ar(freq+70.1245, 0, 0.1) +
	SinOsc.ar(freq+80.2345, 0, 0.1) !2;
env=
	Env.pairs([[0,0],[10,0.8],[14,0.01],[16,1.0],[20,1.0],[35,0]],-2).kr(2,gate);

	Out.ar(0,modulator*carrier*env * mul * Env.adsr().kr(2, gate));

}).add;
