Ndef(\ambient2, {
    var freqs = Array.series(4, 100, 100);
    var trig = Impulse.kr(1) + Impulse.kr(4/8);
    var blip = SinOsc.ar(Demand.kr(trig, 0, Drand(freqs, inf)), mul: 0.9) * Env.perc(0, 0.3).kr(gate: trig);
    var bass = Pan2.ar(LPF.ar(
        Saw.ar(50 * {LFNoise1.kr(1).bipolar(1/4).midiratio}!8, mul: LFTri.kr(2).range(0.2, 0.5)),
        SinOsc.kr(1/4).range(300, 1000),
    ).sum * 0.4, 0);
    var sig = 0;
    freqs.do{ |freq|
        var detune = {LFNoise1.kr(1/4).bipolar(1/2).midiratio}!8;
        var voice = PMOsc.ar(
            freq * detune,
            LFNoise1.kr(2/9).range(0, 3)
        ) * 0.1;
        voice = LPF.ar(voice, LFNoise0.kr(LFNoise0.kr(1/4).range(1, 8)).exprange(300, 9000));
        sig = sig + Pan2.ar(voice.sum, SinOsc.kr(1/3), 0.3);
    };
    sig = sig + bass;
    sig = sig + Pan2.ar(CombL.ar(blip, 1, 1/2, 3), LFNoise1.kr(1));
}).play(fadeTime: 5);

(
Ndef(\fdb1, {
	var src, loc, freq, rt;
	rt = 0.35;
	freq = Duty.ar(16/rt,0,Dseq([60,68,67,59,61,64,65].midicps,inf));

	src = Saw.ar(freq)*Decay.ar(Impulse.ar(rt),1/rt,0.25);
	loc = LocalIn.ar(2)+src;
	loc = FreqShift.ar(loc,-1);
	loc = loc+DelayC.ar(loc,0.2,freq.reciprocal);
	loc = DelayC.ar(loc,4,LFNoise1.ar(rt!2).range(0.25,2));
	loc = DelayC.ar(loc,4,2);
	loc = loc+AllpassC.ar(loc,0.1,LFNoise0.ar(rt!2).range(0.05,0.1),4);
	loc = HPF.ar(loc,100);

	LocalOut.ar(loc*1.55);

	Out.ar(0,Limiter.ar(loc)*0.5);
}).play
)