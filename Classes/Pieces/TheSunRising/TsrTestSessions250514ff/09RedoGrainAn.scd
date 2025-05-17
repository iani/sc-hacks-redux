	*loadSynthGrainA {
		"Loading synthGrainA...".post;
		SynthDef(\grainAn, {
			| out = 0, gate = 1, carfreq = 1000, modfreq = 122, ind = 0.5, rate = 1, pos = 0.5, in = 1, bufnum, trig = 1, dur = 1, amp = 1, fadein = 1, fadeout = 1, sndbuf, envbuf = 1, cutoff = 6000, pan = 0, vol = 0.5, sample, shape |

		var env, index, modulator, zoro, outbus, trigger;

		modulator= SinOsc.kr(modfreq,0,10*modfreq*ind, carfreq);
		env = EnvGen.kr(
        Env([0, 1, 0], [shape, shape]),
        gate,
		levelScale: amp, doneAction: 2);
		trigger = LPF.ar(GrainBuf.ar(2, Dust.kr(trig), dur, sndbuf, BufRateScale.kr(sndbuf)*(modulator/440)*rate, pos, 2, pan, envbuf), cutoff);

     outbus = Out.ar(out, trigger * env * vol)!2
		}).add;

		"synthGrainA loaded".postln;

	}

//:
SynthDef(\grainAn1, {
	| out = 0, gate = 1, carfreq = 1000, modfreq = 122, ind = 0.5, rate = 1, pos = 0.5, in = 1, bufnum, trig = 1, dur = 1, amp = 1, fadein = 1, fadeout = 1, sndbuf, envbuf = 1, cutoff = 6000, pan = 0, vol = 0.5, sample, shape = 1 |

	var env, index, modulator, zoro, outbus, trigger;

	modulator= SinOsc.kr(modfreq,0,10*modfreq*ind, carfreq);
	env = EnvGen.kr(
        Env([0, 1, 0], [shape, shape]),
        gate,
		levelScale: amp, doneAction: 2);
	trigger = LPF.ar(GrainBuf.ar(2, Dust.kr(trig), dur, sndbuf, BufRateScale.kr(sndbuf)*(modulator/440)*rate, pos, 2, pan, envbuf), cutoff);

	outbus = Out.ar(out, trigger * env * vol)!2
})