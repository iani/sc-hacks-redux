//:
TsrPoem.incipits;
 [
	 ~busy1, ~whyDost1, ~through1, ~must1, ~saucy1, ~late1, ~go1, ~call1,
	 ~love1, ~nor1,

	 ~thy1, ~whyShouldst1, ~i1, ~but1, ~if1, ~look1, ~whether1,
	 ~be1, ~ask1, ~and1,

	 ~shes1, ~nothing1, ~princes1,
	 ~all1, ~thou1, ~in1, ~thine1, ~to1, ~shine1, ~this1
 ];
//: SynthDef grainFroth
SynthDef(\grainFroth1, { | out = 0, buf = 0, trate = 0.5, pos = 0, amp = 0.2 |
	var dur, clk, src, pan;
	trate = trate.linexp(0, 1, 1, 400);
	dur = 8 / trate;
	clk = Impulse.kr(trate);
	pos = pos * BufDur.kr(buf);
	pan = WhiteNoise.kr(0.8);
	src = TGrains.ar(2, clk, buf, 2 ** WhiteNoise.kr(2), pos, dur, pan, amp, 4);
	Out.ar(out, src * amp.f);
}).add;
//:!!!!!!!!!!!!
a = Synth(\grainFroth1, [buf: ~v4]);
a.ctl(\pos, { LFSaw.kr(0.01) });
a.ctl(\trate, { LFNoise1.kr(0.1).range(0.1, 0.9) });
a release: 10;
a.freeCtls;

//: 3c GrainFroth3 TGrain with resonance with 4 controled frequencies
SynthDef(\grainFroth3, { | out = 0, buf = 0, trate = 0.5, pos = 0, amp = 0.05,
	f1 = 100, f2 = 200, f3 = 300, f4 = 400 |
	var dur, clk, src, ksrc, freqs, amps, rings, pan;
	trate = trate.linexp(0, 1, 1, 400);
	dur = 8 / trate;
	clk = Impulse.kr(trate);
	pos = pos * BufDur.kr(buf);
	pan = WhiteNoise.kr(0.8);
	src = TGrains.ar(2, clk, buf, 2 ** WhiteNoise.kr(2), pos, dur, pan, amp, 4);
	freqs = [f1, f2, f3, f4];
    amps = #[1, 0.3, 0.2, 0.05];
    rings = #[0.05, 0.05, 0.05, 0.05];
	ksrc = DynKlank.ar(`[freqs, amps, rings], src * amp);
	Out.ar(out, ksrc.f);
}).add;
//:!!!!!!!!
a = Synth(\grainFroth3, [buf: ~v2, amp: 0.05]);
a.ctl(
	\pos, { LFSaw.kr(0.01) },
	\trate, { LFNoise1.kr(0.1).range(0.1, 0.9) },
	\f1, { LFNoise1.kr(1.2).range(100, 2000) },
	\f2, { LFNoise1.kr(2.3).range(100, 2000) },
	\f3, { LFNoise1.kr(1.4).range(100, 2000) },
	\f4, { LFNoise1.kr(2).range(100, 2000) },
);
//:
a release: 20;
a.freeCtls;