// =============================
// "Judges" by Michael Noonan
//
// Composition and code by Michael Noonan
// Final Project for MUSC 115 - Experimental Sound Design
// Bruno Ruviaro, instructor
// Santa Clara University
// Fall 2019
//
// Recording available here: https://soundcloud.com/officialnoonan/judges
// =============================

(
s.waitForBoot({
	SynthDef("pulsey", { arg freq = 440, amp = 0.5, rel = 0.5, att = 0.5, pan = 0;
		var snd, env;

		env = Env.perc(
			attackTime: att,
			releaseTime: rel,
			level: amp
		).kr(doneAction: 2);

		snd = Pulse.ar(
			freq: freq * LFNoise2.kr([4, 5, 7]).range(0.988, 1.011),
			mul: env
		);

		snd = BPF.ar(
			in: snd,
			freq: 500,
			rq: 5
		);

		snd = Splay.ar(snd);

		snd = snd * In.kr(55);
		snd = Pan2.ar(snd, pan);

		Out.ar(0, snd);

		//DetectSilence.ar(snd, doneAction: 2);
	}).add;

	SynthDef("pulsey2", { arg freq = 440, amp = 0.5, rel = 0.5, att = 0.5, pan = 0;
		var snd, env;

		env = Env.perc(
			attackTime: att,
			releaseTime: rel,
			level: amp
		).kr;

		snd = Pulse.ar(
			freq: freq * LFNoise2.kr([4, 5, 7]).range(0.988, 1.011),
			mul: env
		);

		snd = BPF.ar(
			in: snd,
			freq: 600,
			rq: 4
		);

		snd = Splay.ar(snd);

		//snd = snd * In.kr(55);
		snd = Pan2.ar(snd, pan);

		Out.ar(0, snd);

		DetectSilence.ar(snd, doneAction: 2);
	}).add;

	SynthDef("bass", { arg freq = 440, amp = 0.6, att = 0.01, rel = 0.4, pan = 0, gate = 1;
		var snd, snd2, env, env2, harmonics, amplitudes;

		env = Env.asr(att, amp, rel).kr(gate: gate, doneAction: 2);

		harmonics = [1, 2.011, 2.01, 2.98, 2.97, 4.01, 4.98, 6.02];
		amplitudes = [0.9, 0.25, 0.25, 0.2, 0.2, 0.15, 0.1, 0.05];

		snd = Saw.ar(freq * harmonics, amplitudes*amp);
		snd2 = SinOsc.ar(freq); // * LFTri.ar(5).range(0, 1);

		snd = (snd * 0.85) + (snd2 * 0.15);

		snd = HPF.ar(snd, 50);

		snd = snd * env;
		snd = Mix.ar(snd);
		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);

		// 'doneAction: 2' here
		//DetectSilence.ar(snd, 0.001, 0.2, doneAction: 2);
	}).add;

	SynthDef("harmony", { arg freq = 440, amp = 0.6, att = 0.01, rel = 0.4, pan = 0, gate = 1;
		var snd, env, harmonics, amplitudes;

		env = Env.asr(att, amp, rel).kr(gate: gate, doneAction: 2);

		harmonics = [1, 2.01, /*2.98,*/ 2.97, 4.01, 4.98, 6.02];
		amplitudes = [0.9, 0.25, /*0.2,*/ 0.2, 0.15, 0.1, 0.05];

		snd = Saw.ar(freq * harmonics, amplitudes*amp);
		//snd2 = SinOsc.ar(freq); // * LFTri.ar(5).range(0, 1);

		//snd = (snd * 1) + (snd2 * 0.2);

		snd = BPF.ar(snd, freq, 8, 2, 0);
		snd = LPF.ar(snd, 8000);

		snd = snd * env;
		snd = Mix.ar(snd);
		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);

		// 'doneAction: 2' here
		//DetectSilence.ar(snd, 0.001, 0.2, doneAction: 2);
	}).add;

	SynthDef("sawverb", { arg freq = 440, amp = 0.2, pan = 0, att = 0.01, rel = 0.1, gate = 1, spacing, beatFactor = 1;
		var snd1, snd2, snd, env1, env2, beatings; // variables snd, env

		env1 = Env.adsr(
			attackTime: att,
			decayTime: 0.2,
			sustainLevel: amp,
			releaseTime: rel
		).kr(gate: gate);

		env2 = Env.adsr(
			attackTime: att * 1/2,
			decayTime: 0.2,
			sustainLevel: amp*0.6,
			releaseTime: rel*1/2
		).kr(gate: gate);


		snd1 = Saw.ar(
			freq: freq,//[freq, freq*2, freq*2.005, freq*3],
			mul: amp//[amp, amp*0.1, amp*0.1, amp*0.1]
		);

		snd2 = Pulse.ar(
			freq: freq,
			mul: amp
		);

		snd1 = snd1 * env1;
		snd2 = snd2 * env2;

		snd = snd1 + snd2;

		snd = BPF.ar(
			in: snd,
			freq: 100,
			rq: 0.5,
			mul: 2
		);

		snd = FreeVerb.ar(
			in: snd,
			mix: 0.4,
			room:  0.1,
			damp: 0.9
		);

		//snd = snd.distort.distort;
		snd = Mix.ar(snd);
		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);

		// 'doneAction: 2' here
		DetectSilence.ar(snd, 0.001, 0.2, doneAction: 2);
	}).add;

	SynthDef("plucking", {arg amp = 0.1, freq = 440, decay = 5, dampen = 0.1, mix = 0.5;

		var env, snd;
		env = Env.linen(0.2, decay, 0).kr(doneAction: 2);
		snd = Pluck.ar(
			in: WhiteNoise.ar(amp),
			trig: Impulse.kr(0),
			maxdelaytime: 0.1,
			delaytime: freq.reciprocal,
			decaytime: decay,
			coef: dampen
		);
		snd = FreeVerb.ar(snd, mix, 0.4, 0.8).clip;
		Out.ar(0, [snd, snd]);
		//DetectSilence.ar(in: snd, amp: 0.0001, time: 0.01, doneAction: 2);
	}).add;

	SynthDef("snare", {arg amp = 0.1, freq = 180, att = 0.01, rel = 0.2, ffreq = 2000, pan = 0;
		var env, snd1, snd2, sum;
		env = Env.perc(att, rel, amp).kr;
		snd1 = HPF.ar(
			in: WhiteNoise.ar,
			freq: ffreq
		);
		snd2 = SinOsc.ar(freq: freq);
		sum = snd1 + snd2;
		sum = sum * env;


		Out.ar(0, Pan2.ar(sum, pan));
		DetectSilence.ar(sum, doneAction: 2);
	}).add;

	SynthDef("sosKick", {
		arg
		//Standard Values
		out = 0, pan = 0, amp = 1, curve = -4, drumAmp = 1, beaterAmp = 0.02,
		//drum Controls
		drumFreq = 50, drumHarmonic = 2, drumSweep = 0.02, drumAtt = 0.005, drumRel = 0.4,
		drumFilter = 1000, modIndex = 6.5, modFreq = 5,
		//beater Controls
		beaterFreq = 500, beaterHarmonic = 12, beaterSweep = 0.03, beaterAtt = 0.01, beaterRel = 0.3;

		var drumEnv, drumContour, drum, beaterContour, beaterEnv, beater, snd;

		drumEnv = Env.perc(attackTime: drumAtt, releaseTime: drumRel, level: drumAmp, curve: curve).kr;

		drumContour = Line.kr(start: drumFreq * drumHarmonic, end: drumFreq, dur: drumSweep);

		drum = PMOsc.ar(
			carfreq: drumContour,
			modfreq: modFreq,
			pmindex: modIndex);

		drum = LPF.ar(in: drum, freq: drumFilter, mul: drumEnv);

		beaterEnv = Env.perc(attackTime: beaterAtt, releaseTime: beaterRel, level: beaterAmp, curve: curve).kr;

		beaterContour = Line.kr(start: beaterFreq * beaterHarmonic, end: beaterFreq, dur: beaterSweep);

		beater = HPF.ar(in: WhiteNoise.ar, freq: beaterFreq);

		beater = LPF.ar(in: beater, freq: beaterContour, mul: beaterEnv*2);

		snd = Mix.ar(drum + beater) * amp;

		//snd = snd.distort.distort;

		DetectSilence.ar(in: snd, doneAction: 2);

		Out.ar(out, Pan2.ar(snd, pan));

	},
	metadata: (
		credit: "Renick Bell",
		category: \drums,
		tags: [\pitched, \kick, \sos]
	)
	).add;

	SynthDef("hihat", {arg amp = 0.5, att = 0.01, rel = 0.2, ffreq = 6000, pan = 0;
		var env, snd;
		env = Env.perc(
			attackTime: att,
			releaseTime: rel,
			level: amp*1.2
		).kr;
		snd = WhiteNoise.ar;
		snd = HPF.ar(in: snd, freq: ffreq);
		snd = snd * env;
		Out.ar(0, Pan2.ar(snd, pan));
		DetectSilence.ar(snd, doneAction: 2);
	}).add;

	SynthDef("myBell", { arg freq = 440, amp = 0.1, att = 0.01, rel = 0.5, pan = 0;
		var snd, env, spectrum, partials, amplitudes;

		spectrum = [
			[1, 0.5, Env.perc(att, rel, amp, -4.0).kr],

			[2, 0.2, Env.perc(att, rel*0.7, amp, -4.0).kr],

			[3.011, 0.1, Env.perc(att, rel*0.4, amp, -4.0).kr]
		];

		partials = spectrum.flop[0];
		amplitudes = spectrum.flop[1];

		env = spectrum.flop[2];

		snd = SinOsc.ar(freq: freq * partials, mul: amplitudes);
		snd = snd * env;
		snd = Mix.ar(snd);
		snd = Limiter.ar(snd);
		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);
		DetectSilence.ar(in: snd, amp: 0.001, time: 0.1, doneAction: 2);

	}).add;

	SynthDef(\cheapPiano1, {
		arg
		//Standard values
		out = 0, freq = 440, amp = 0.1, att = 0.001, rel = 1, pan = 0,
		//These range from 0 to 1
		tone = 0.25, hollowness = 0;

		var hammer, snd, delay, tonefreq, env;

		env = Env.perc(att, rel*0.6, amp * 4, -1).ar(doneAction: 2);

		// Delay line times: freq, freq - 4 cents, freq + 3 cents. In the original this was done by converting freq to midi.
		delay = (1 / (freq * [2.pow(-0.04/12), 1, 2.pow(0.03/12)]));
		tonefreq = tone.linlin(0, 1, 1000, 5000);

		hammer = Decay2.ar(
			in: Impulse.ar(0.001),
			attackTime: 0.008,
			decayTime: 0.04,
			mul: LFNoise1.ar(freq: amp.linlin(0, 1, tonefreq, 2 * tonefreq), mul: 0.25)
		); //Try LFNoise1, LFNoise0, or even LFClipNoise above for a slightly grainier sound.

		snd = CombL.ar(hammer, delay, delay, 50 * amp);
		snd = BPF.ar(snd, 400, 2.5);
		snd = snd * env;
		snd = Limiter.ar(snd);
		Out.ar(out, Pan2.ar(snd, pan));
	},
	metadata: (
		credit: "based on something posted 2008-06-17 by jeff, based on an old example by james mcc",
		category: \keyboards,
		tags: [\casio, \piano, \pitched]
	)
	).add;

	SynthDef("marimba1", {arg freq = 440, amp = 0.4, pan = 0;
		var snd, env;
		env = Env.linen(
			attackTime: 0.015,
			sustainTime: 1,
			releaseTime: 0.5,
			level: amp*2
		).kr(doneAction: 2);
		snd = BPF.ar(
			in: Saw.ar(0),
			freq: freq,
			rq: 0.02
		);
		snd = BLowShelf.ar(
			in: snd,
			freq: 220,
			rs: 0.81,
			db: 12
		);

		snd = HPF.ar(snd, 500);

		snd = snd * env;
		Out.ar(0, Pan2.ar(snd, pan));
	},
	metadata: (
		credit: "unknown",
		category: \keyboards,
		tags: [\percussion, \marimba, \pitched, \keyboard]
	)
	).add;

	SynthDef("FM2", {arg freq = 440, cmRatio = 3, modIndex = 0, amp = 0.01, att = 0.01, dec = 0.05, sus = 0.9, rel = 0.1, pan = 0, gate = 1;
		var carrier, modulator, modFreq, freqDev, snd, env;
		env = Env.adsr(
			attackTime: att,
			decayTime: dec,
			sustainLevel: sus,
			releaseTime: rel,
			peakLevel: amp
		).kr(gate: gate);

		// C:M = freq:modFreq, thus modFreq = freq * B/A
		// When C:M is a simple integer ratio, FM generates harmonic spectra
		// When C:M is NOT a simple integer ratio, FM generates inharmonic spectra
		modFreq = freq * cmRatio.reciprocal;

		// Using Modulation Index to calculate freqDev
		// from formula index = freqDev/modfreq:
		freqDev = modIndex * modFreq;

		modulator = SinOsc.ar(freq: modFreq, mul: freqDev);
		carrier = SinOsc.ar(freq: freq + modulator);
		snd = carrier * env;

		snd = LPF.ar(snd, 300, 1.8);
		/*snd = FreeVerb.ar(snd, 0.2, 0.5, 1);*/

		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);
		DetectSilence.ar(snd, doneAction: 2);
	}).add;

	SynthDef("melodyFM", {arg freq = 440, cmRatio = 3, modIndex = 0, amp = 0.01, att = 0.01, dec = 0.05, sus = 0.9, rel = 0.1, pan = 0, gate = 1;
		var carrier, modulator, modFreq, freqDev, snd, env;
		env = Env.adsr(
			attackTime: att,
			decayTime: dec,
			sustainLevel: sus,
			releaseTime: rel,
			peakLevel: amp
		).kr(gate: gate);

		// C:M = freq:modFreq, thus modFreq = freq * B/A
		// When C:M is a simple integer ratio, FM generates harmonic spectra
		// When C:M is NOT a simple integer ratio, FM generates inharmonic spectra
		modFreq = freq * cmRatio.reciprocal;

		// Using Modulation Index to calculate freqDev
		// from formula index = freqDev/modfreq:
		freqDev = modIndex * modFreq;

		modulator = SinOsc.ar(freq: modFreq, mul: freqDev);
		carrier = SinOsc.ar(freq: freq + modulator);
		snd = carrier * env;
		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);
		DetectSilence.ar(snd, doneAction: 2);
	}).add;

	SynthDef("organTonewheel0", {

		arg
		// Standard Values
		out = 0, freq = 440, amp = 0.7, att = 0.001, rel = 0.01, pan = 0, curve = -4, gate = 1,
		//organ voices (drawbars) amplitudes
		bass = 1, quint = 1, fundamental = 1, oct = 1, nazard = 1, blockFlute = 1, tierce = 1, larigot = 1, sifflute = 1,
		//vibrato arguments
		vrate = 3, vdepth = 0.008;

		var snd, env, vibrato;

		vibrato = SinOsc.kr(vrate).range((1 / (1 + vdepth)), (1 + vdepth));

		env = Env.asr(attackTime: att, sustainLevel: amp, releaseTime: rel, curve: curve).ar(gate: gate);

		vibrato = DynKlang.ar(
			specificationsArrayRef: Ref.new([
				[1/12,  1/7, 1, 12, 19, 24, 28, 31, 36].midiratio,
				[bass, quint, fundamental, oct, nazard, blockFlute, tierce, larigot, sifflute].normalizeSum
			]),
			freqscale: vibrato * freq);

		snd = vibrato * env;

		snd = FreeVerb.ar(snd, 0.5, 0.3, 0.9);
		snd = BPF.ar(snd, 200, 8);

		Out.ar(out, Pan2.ar(snd, pan));
		DetectSilence.ar(snd, doneAction: 2);

	},
	metadata: (
		credit: "Zé Craum",
		category: \organ,
		tags: [\pitched]
	)
	).add;

	SynthDef(\strings, {
		arg
		//Standard Definitions
		out = 0, freq = 440, amp = 1, gate = 1, pan = 0, freqLag = 0.2, att = 0.001, dec = 0.1, sus = 0.75, rel = 0.3,
		//Other Controls (mix ranges from 0 - 1)
		rq = 0.001, combHarmonic = 4, sawHarmonic = 1.5, mix = 0.33;

		var env, snd, combFreq;

		combFreq = 1 / (Lag.kr(in: freq, lagTime: freqLag / 8) * combHarmonic);

		env = Env.adsr(att, dec, sus, rel, amp).kr(gate: gate, doneAction: 2);

		snd = SyncSaw.ar(syncFreq: freq * WhiteNoise.kr().range(1/1, 1), sawFreq: freq * sawHarmonic, mul: 8);
		snd = (snd * (1 - mix)) + PinkNoise.ar(180 * mix);
		snd = CombL.ar(snd, combFreq, combFreq, -1); //Try positive 1 for decay time as well.
		snd = Resonz.ar(snd, Lag.kr(in: freq, lagTime: freqLag), rq).abs;
		snd = snd * env;
		snd = BPF.ar(snd, SinOsc.ar(8).range(400, 1000), 2);
		snd = Limiter.ar(snd, amp);

		Out.ar(out, Pan2.ar(snd, pan));
	},
	metadata: (
		credit: "Original from Julian Rohrhuber, 2007",
		category: \strings,
		tags: [\pitched]
	)
	).add;

	SynthDef("myBellOriginal", { arg freq = 440, amp = 0.1, att = 0.01, rel = 1, pan = 0;
		var snd, env, spectrum, partials, amplitudes;

		spectrum = [
			[0.5, 0.4, Env.perc(att, rel*1.5, amp, -4.0).kr],
			[1, 0.5, Env.perc(att, rel, amp, -4.0).kr],
			[1.183, 0.3, Env.perc(att, rel*0.9, amp, -4.0).kr],
			[1.506, 0.3, Env.perc(att, rel*0.8, amp, -4.0).kr],
			[2, 0.2, Env.perc(att, rel*0.7, amp, -4.0).kr],
			[2.514, 0.2, Env.perc(att, rel*0.6, amp, -4.0).kr],
			[2.662, 0.2, Env.perc(att, rel*0.5, amp, -4.0).kr],
			[3.011, 0.1, Env.perc(att, rel*0.4, amp, -4.0).kr],
			[4.166, 0.1, Env.perc(att, rel*0.3, amp, -4.0).kr],
			[5.433, 0.05, Env.perc(att, rel*0.2, amp, -4.0).kr],
			[6.796, 0.01, Env.perc(att, rel*0.1, amp, -4.0).kr],
			[8.215, 0.01, Env.perc(att, rel*0.05, amp, -4.0).kr]
		];

		partials = spectrum.flop[0];
		amplitudes = spectrum.flop[1];

		env = spectrum.flop[2];

		snd = SinOsc.ar(freq: freq * partials, mul: amplitudes);
		snd = snd * env;
		snd = Mix.ar(snd);
		snd = Limiter.ar(snd);
		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);
		DetectSilence.ar(in: snd, amp: 0.001, time: 0.1, doneAction: 2);

	}).add;

	SynthDef("Wobbly Organ", { arg freq = 440, amp = 0.1, att = 0.05, rel = 0.01, pan = 0, vibratoSpeed = 4, vibratoAmount = 0.125, filterVel = 8;
		var snd, env, harmonics, amplitudes, midinote, vibrato;
		env = Env.perc(
			attackTime: att,
			releaseTime: rel,
			level: amp
		).kr(doneAction: 2);

		harmonics = [1, 2, 4, 6, 8];
		amplitudes = [-9, -11, -11, -25, -14].dbamp;

		vibrato = SinOsc.kr(vibratoSpeed).range(vibratoAmount.neg, vibratoAmount);
		midinote = (freq * harmonics).cpsmidi + vibrato;

		snd = SinOsc.ar(freq: midinote.midicps, mul: amplitudes);

		snd = LPF.ar(
			in: snd,
			freq: SinOsc.kr(freq: filterVel*2).range(freq*3/5, freq*5/3)+400
		);

		snd = FreeVerb.ar(
			in: snd,
			mix: 0.3,
			room: 0.9,
			damp: 0.6
		);

		snd = Mix.ar(snd) * env;
		snd = Pan2.ar(snd, pan);
		snd = Limiter.ar(snd);
		Out.ar(0, snd);
	}).add;

	SynthDef( "odd1", { arg freq = 440, amp = 0.6, att = 0.01, rel = 1, pan = 0, gate = 1, oddFactor;
		var snd, env;

		env = Env.asr(att, amp, rel).kr(gate: gate, doneAction: 2);

		snd = Saw.ar([freq, freq+3]);

		snd = snd * env;

		snd = BPF.ar(snd, freq, 8, 2);

		snd = FreeVerb.ar(snd, 0.7, 0.3, 0.9);

		snd = Mix.ar(snd);
		snd = Limiter.ar(snd, 1);
		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);
	}).add;

	SynthDef( "odd2", { arg freq = 440, amp = 0.6, att = 0.01, rel = 1, pan = 0, gate = 1, oddFactor;
		var snd, env;

		env = Env.asr(att, amp, rel).kr(gate: gate, doneAction: 2);

		snd = Pulse.ar([freq, freq+(oddFactor/2), (freq*2)+oddFactor]);

		snd = snd * env;

		snd = BPF.ar(snd, freq, 8, 1.5);

		snd = FreeVerb.ar(snd, 0.7, 0.3, 0.9);

		//snd = snd * In.kr(56);

		snd = Mix.ar(snd);
		snd = Limiter.ar(snd, 1);
		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);
	}).add;

	SynthDef(\organTonewheel2, {
		arg
		//Standard Values
		out = 0, pan = 0, freq = 440, amp = 0.45, att = 0.001, rel = 0.1, gate = 1,
		//Other controls (blend goes from 0 to 1)
		vibRate = 8.0, vibHarmonic = 1.027, filterHarmonic = 8.04, rq = 1, blend = 0.83;

		var snd, env, vibrato;

		env = Env.asr(attackTime: att, sustainLevel: amp, releaseTime: rel).kr(gate: gate, doneAction: 2);

		vibrato = SinOsc.ar(freq: vibRate).range(freq, freq * vibHarmonic);

		snd = LFPulse.ar(freq: freq, width: 0.5, mul: 1 - blend) + LFPulse.ar(freq: freq + vibrato, width: 0.18, mul: blend);

		snd = RLPF.ar(in: snd, freq: filterHarmonic * freq, rq: rq, mul: env);

		snd = BPF.ar(snd, 400, 1).clip;

		snd = LeakDC.ar(snd);

		Out.ar(out, Pan2.ar(snd, pan));

	},
	metadata: (
		credit: "Zé Craum",
		category: \organ,
		tags: [\pitched]
	)
	).add;

	SynthDef("FM-perc", {arg freq = 440, cmRatio = 3, modIndex = 0, amp = 0.01, att = 0.01, rel = 0.1, pan = 0, dur;
		var carrier, modulator, modFreq, freqDev, snd, env;
		env = Env.perc(
			attackTime: att,
			releaseTime: rel,
			level: amp
		).kr;

		// C:M = freq:modFreq, thus modFreq = freq * B/A
		// When C:M is a simple integer ratio, FM generates harmonic spectra
		// When C:M is NOT a simple integer ratio, FM generates inharmonic spectra
		modFreq = freq * cmRatio.reciprocal;

		// Using Modulation Index to calculate freqDev
		// from formula index = freqDev/modfreq:
		freqDev = modIndex * modFreq;

		modulator = SinOsc.ar(freq: modFreq, mul: freqDev);
		carrier = SinOsc.ar(freq: freq + modulator);
		snd = carrier * env;
		snd = LPF.ar(
			in: snd,
			freq: SinOsc.ar(2, 1).range(XLine.ar(1600, 1800, dur), XLine.ar(2000, 2200, dur))// * XLine.ar(2, 1, dur)
		);
		/*snd = CombC.ar(
		in: snd,
		maxdelaytime: 1,
		delaytime: 0.5,
		decaytime: 3
		);*/
		snd = snd.distort.distort;
		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);
		DetectSilence.ar(snd, doneAction: 2);
	}).add;

	SynthDef("raygun", {arg amp = 0.5, sinfreq = 400, glissf = 0.0001, att = 0.01, rel = 0.9, pan = 0;
		var gliss = XLine.kr(sinfreq, sinfreq*glissf, rel);
		var snd = SinOsc.ar(gliss).clip.clip;
		var env = EnvGen.kr(Env.perc(att, rel), doneAction: 2);
		snd = snd * env * amp;
		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);
	}).add;

	SynthDef("uglylead", { arg amp = 0.5, freq = 330, att = 0.1, dec = 0.1, rel = 0.1, pan = 0, gate = 1, detune = 0.05;
		var snd1, snd2, snd, env;

		env = Env.adsr(att, dec, amp*0.8, rel, amp).kr(gate: gate);

		snd1 = Saw.ar(freq);
		snd2 = Pulse.ar(freq*SinOsc.ar(4).range(1-detune, 1+detune), 0.4);
		snd = snd1 + snd2;

		snd = snd*env;

		snd = BPF.ar(snd, SinOsc.ar(64).range(400, 650), 4);

		snd = LPF.ar(snd, 3000);

		snd = Mix.ar(snd);
		snd = Pan2.ar(snd, pan);
		Out.ar(0, snd);

		DetectSilence.ar(snd, doneAction: 2);
	}).add;

	//=====================
	// Pbinds
	//=====================

	~chordsA = Pbind(
		\instrument, "pulsey",
		\midinote, Pseq([
			[60, 63, 67, 70, 72], [60, 63, 67, 70, 72], [60, 63, 67, 70, 72], [60, 63, 67, 70, 72], [60, 63, 65, 67, 70], [60, 63, 65, 67, 70], [60, 63, 65, 67, 70], [60, 63, 65, 67, 70]
		], inf),
		\dur, Pseq([2, 2, 2, 1, 1], inf)*0.5,
		\amp, 0.225,
		\att, 0.15,
		\legato, 0.6,
		\rel, Pkey(\dur) - Pkey(\att),
		\pan, Pseq([-0.25, 0.25], inf),
		\strum, 0.4
	).play.stop;

	~bassA = Pbind(
		\instrument, "bass",
		\scale, Scale.aeolian,
		\midinote, Pseq([60, 56, 60, 56, 60, 56, 53, 62], inf),
		\dur, 4,
		\rel, 1,
		\ctranspose, [-36, -24, -12],
		\amp, [0.15, 0.45, 0.25],
		\pan, -0.2,
		\legato, 0.8
	).play.stop;

	~bassD = Pbind(
		\instrument, "bass",
		\scale, Scale.aeolian,
		\midinote, 60,
		\dur, 1,
		\rel, 3,
		\ctranspose, [-36, -24, -12],
		\amp, [0.16, 0.46, 0.26],
		\pan, -0.2,
		\legato, 0.8
	).play.stop;

	~harmonyA = Pbind(
		\instrument, "harmony",
		\scale, Scale.aeolian,
		\midinote, Pseq([63, 75, 60, 72, 63, 75, 60, 72, 63, 75, 60, 72, 56, 68, 65, 77], inf),
		\dur, 2,
		\rel, 0.5,
		\ctranspose, 12,
		\amp, 0.325,
		\pan, 0.25
	).play.stop;

	~harmonyD = Pbind(
		\instrument, "harmony",
		\scale, Scale.aeolian,
		\midinote, 63,
		\dur, 2,
		\rel, 0.5,
		\ctranspose, 12,
		\amp, 0.325,
		\pan, 0.25
	).play.stop;

	~hihatA = Pbind(
		\instrument, "hihat",
		\dur, Pseq([0.5, 0.25, 0.25, 0.25, 0.25, 0.25, 0.25], inf)*0.4,
		\rel, Pseq([0.1, 0.4, 0.1, 0.1, 0.1, 0.4, 0.1], inf),
		\pan, Pseq([-0.5, 0.5, 0, -0.5, 0, 0.5, 0], inf),
		\amp, 0.2,
		\ffreq, Pwhite(11000, 15000)
	).play.stop;

	~hihatD = Pbind(
		\instrument, "hihat",
		\dur, Pseq([0.25, 0.25, 0.25, 0.25, 0.25, 0.25, 0.125, 0.125, 0.25, 0.25, 0.25], inf)*0.4,
		\rel, Pseq([0.1, 0.1, 0.4, 0.1, 0.1, 0.1, 0.1, 0.1, 0.4, 0.1, 0.1], inf),
		\pan, Pseq([-0.5, 0.5], inf),
		\amp, 0.175,
		\ffreq, Pwhite(11000, 15000)
	).play.stop;

	~kickA = Pbind(
		\instrument, "sosKick",
		\modFreq, [5, 6],
		\modIndex, Pwhite(0.1, 8.5),
		\beaterHarmonic, Pwhite(1.5, 8),
		\dur, 0.4,//Pseq([1, 1, 0.25, 0.75, 1], inf),
		\amp, 0.775,
		\pan, -0.25
	).play.stop;

	~kickD = Pbind(
		\instrument, "sosKick",
		\modFreq, [5, 6],
		\modIndex, Pwhite(0.1, 8.5),
		\beaterHarmonic, Pwhite(1.5, 8),
		\dur, 0.4,//Pseq([1, 1, 0.25, 0.75, 1], inf),
		\amp, 0.775,
		\pan, -0.25
	).play.stop;

	~snareA = Pbind(
		\instrument, "snare",
		\dur, Pseq([Rest(1.5), 1.5, 1], inf)*0.4,
		\att, 0.01,
		\rel, Pseq([\rest, 0.15, 0.4], inf),
		\freq, Pseq([\rest, 300, 100], inf),
		\ffreq, [1500, 2000],
		\amp, 0.375,
		\pan, 0.25,
	).play.stop;

	~snareD = Pbind(
		\instrument, "snare",
		\dur, Pseq([Rest(0.75), 0.75, 0.5, 1, Rest(1), 1], inf)*0.4,
		\att, 0.01,
		\rel, Pseq([\rest, 0.15,0.15, 0.4, \rest, 0.4], inf),
		\freq, Pseq([\rest, 300, 300, 100, \rest, 100], inf),
		\ffreq, [1500, 2000],
		\amp, 0.375,
		\pan, 0.25,
	).play.stop;

	~snareD2 = Pbind(
		\instrument, "snare",
		\dur, Pseq([Rest(0.75), 0.75, 0.5, Rest(1.5), 0.25, 0.25, 0.5, 0.5, Rest(0.75), 0.75, 0.5, Rest(1), 1/3, 1/3, 1/3, 1/3, 1/3, 1/3])*0.4,
		\att, 0.01,
		\rel, Pseq([\rest, 0.15,0.15, \rest, 0.15, 0.15, 0.15, 0.15, \rest, 0.15,0.15, \rest, 0.15, 0.15, 0.15, 0.15, 0.15, 0.15]),
		\freq, Pseq([\rest, 300, 300, \rest, 300, 300, 300, 300, \rest, 300, 300, \rest, 300, 300, 300, 300, 300, 300]),
		\ffreq, [1500, 2000],
		\amp, 0.375,
		\pan, 0.25,
	).play.stop;

	~bassB = Pbind(
		\instrument, "sawverb",
		\scale, Scale.aeolian,
		\degree, Pseq([
			0, 0, 6, 7, 0, 0, 2, 3, 4, 5
		], inf),
		\ctranspose, [-12, -24, -36],
		\dur, Pseq([0.75, 0.25, 0.25, 0.75, 0.75, 0.25, 0.25, 0.25, 0.25, 0.25], inf),
		\amp, [0.8, 0.3, 0.1]*1.4,
		\att, Pkey(\dur)*0.25,
		\legato, 0.4,
		\beatFactor, 0.5,
	).play.stop;

	~guitarB = Pbind(
		\instrument, "plucking",
		\midinote, Pseq([
			[60, 63, 67], [60, 63, 67], [60, 63, 67], [60, 63, 67], [60, 63, 67], [60, 63, 67], [60, 63, 68], [60, 63, 68],
		], inf),
		\dur, 0.25,
		\decay, 5,
		\mix, 0.6,
		\ctranspose, 0,
		\dampen, 0.6,
		\strum, Pwhite(0.01, 0.02),
		\amp, Pseq([0.4, 0.4, 0.4, 0.8, 0.4, 0.4, 0.6, 0.8], inf)*1.2,
		\pan, Pseq([-0.25, 0.25], inf)
	).play.stop;

	~hihatB = Pbind(
		\instrument, "hihat",
		\dur, Pseq([0.25, 0.25, 0.25, 0.25, 0.25, 0.25, 0.125, 0.125, 0.25], inf),
		\rel, Pseq([0.45, 0.12, 0.11, 0.42, 0.13, 0.16, 0.14, 0.15, 0.35], inf),
		\pan, Pseq([-0.5, 0.5, 0, -0.5, 0, 0.5, 0], inf),
		\amp, Pwhite(0.225, 0.325),
		\ffreq, Pwhite(11000, 15000)
	).play.stop;

	~kickB = Pbind(
		\instrument, "sosKick",
		\modFreq, [5, 6, 7],//Pwhite(5, 8),
		\modIndex, Pwhite(4, 8.5),
		\beaterHarmonic, 2,//Pwhite(1.5, 4),
		\dur, Pseq([0.75, 1.25], inf),//Pseq([1, 1, 0.25, 0.75, 1], inf),
		\amp, 0.6,
		\pan, -0.5
	).play.stop;

	~snareB = Pbind(
		\instrument, "snare",
		\dur, Pseq([Rest(1), 1], inf),
		\att, 0.01,
		\rel, 0.2,
		\freq, 200,
		\ffreq, [2000, 2200],
		\amp, 0.5,
		\pan, 0.5,
	).play.stop;

	~melodyB = Pbind(
		\instrument, "pulsey2",
		\midinote, Pseq([68, 67, 63, 60, 55, \rest], 8),
		\dur, Pseq([0.75, 0.75, 0.5, 1, 1, Rest(4)], 8),
		\amp, 0.3*1.4,
		\att, 0.05,
		\legato, 0.6,
		\rel, Pkey(\dur) - Pkey(\att),
		\pan, 0.5, //Pseq([1, 0.5, 0, -0.5, -1, \rest], inf),
		\ctranspose, 0
	).play.stop;

	~bellB = Pbind(
		\instrument, "myBell",
		\dur, Pseq([Rest(4), 4], inf),
		\midinote, Pseq([\rest,[72, 75]],  inf),
		\rel, 3,
		\ctranspose, 12,
		\amp, 0.2,
		\pan, -1/3
	).play.stop;

	~pianoB = Pbind(
		\instrument, "cheapPiano1",
		\ctranspose, -12,
		\legato, 0.2,
		\dur, Pseq([0.25, 0.25, 0.25, 0.5, 0.25, 0.25, 0.25, 0.25, 0.25, 0.25, 1.25], inf),
		\midinote, Pseq([60, 56, 58, 60, 60, 63, 62, 60, 56, 58, 60], inf),
		\amp, 0.25,
		\pan, Pwhite(-0.1, 0.1)
	).play.stop;

	~marimbaB = Pbind(
		\instrument, "marimba1",
		\midinote, Pseq([\rest, 63, 62, 63, 62, 60, 63, 61, 63, 61, 60, 63, 62, 63, 62, 60, 63, 61, 63, 61, 60], inf),
		\dur, Pseq([Rest(4), 0.125, 0.125, 0.125, 0.125, 0.5, 0.125, 0.125, 0.125, 0.125, 0.5, 0.125, 0.125, 0.125, 0.125, 0.5, 0.125, 0.125, 0.125, 0.125, 0.5], inf),
		\amp, 1.5,
		\ctranspose, Pseq([\rest, 24, 24, 24, 24, 24, 12, 12, 12, 12, 12, 0, 0, 0, 0, 0, -12, -12, -12, -12, -12], inf),
		\pan, Pseq([\rest, 1, 1, 1, 1, 1, 1/3, 1/3, 1/3, 1/3, 1/3, -1/3, -1/3, -1/3, -1/3, -1/3, -1, -1, -1, -1, -1], inf)*0.5
	).play.stop;

	~secondMelodyB1 = Pbind(
		\instrument, "odd1",
		\midinote, Pseq([60, 61, 60, 58, 60, 58, 56, 53, 55], inf)+12,
		\dur, Pseq([0.75, 0.25, 0.5, 0.5, 0.75, 0.25, 0.5, 0.5, 4], inf),
		\amp, 0.23,
		\att, 0.01,
		\rel, 0.6,
		\legato, 0.8,
		\pan, -0.3
	).play.stop;

	~secondMelodyB2 = Pbind(
		\instrument, "odd2",
		\midinote, Pseq([60, 61, 60, 58, 60, 58, 56, 53, 55])+12,
		\dur, Pseq([0.75, 0.25, 0.5, 0.5, 0.75, 0.25, 0.5, 0.5, 4]),
		\amp, 0.23,
		\att, 0.01,
		\rel, 0.6,
		\legato, 0.8,
		\oddFactor, 4,
		\pan, -0.3
	).play.stop;

	~secondMelodyB3 = Pbind(
		\instrument, "odd2",
		\midinote, Pseq([60, 61, 60, 58, 60, 58, 56, 54, 55])+12,
		\dur, Pseq([0.75, 0.25, 0.5, 0.5, 0.75, 0.25, 0.5, 0.5, 4]),
		\amp, 0.22,
		\att, 0.01,
		\rel, 0.6,
		\legato, 0.8,
		\oddFactor, 10,
		\pan, -0.3
	).play.stop;

	~secondMelodyB4 = Pbind(
		\instrument, "odd2",
		\midinote, Pseq([60, 61, 60, 58, 60, 61, 57, 58, 54, 55])+12,
		\dur, Pseq([1/3, 1/3, 1/3, 0.5, 0.5, 0.75, 0.25, 0.5, 0.5, 4]),
		\amp, 0.21,
		\att, 0.01,
		\rel, 0.6,
		\legato, 0.8,
		\oddFactor, 14,
		\pan, -0.3
	).play.stop;

	~secondMelodyB5 = Pbind(
		\instrument, "odd2",
		\midinote, Pseq([60, 61, 60, 59, 60, 61, 57, 58, 54, 55])+12,
		\dur, Pseq([1/3, 1/3, 1/3, 0.5, 0.5, 0.75, 0.25, 0.5, 0.5, 4]),
		\amp, 0.20,
		\att, 0.01,
		\rel, 0.6,
		\legato, 0.8,
		\oddFactor, 20,
		\pan, -0.3
	).play.stop;

	~hit = Pbind(
		\instrument, "organTonewheel2",
		\midinote, Pseq([\rest, [63, 67, 72], [63, 67, 72], [63, 67, 70], \rest], inf),
		\amp, 0.75,
		\dur, Pseq([Rest(3.25), 0.25, 0.25, 0.25, Rest(4)], inf),
		\ctranspose, 12,
		\strum, 0.01,
		\dampen, 0.5,
		\pan, 0.75
	).play.stop;

	~bTocTransition = Pbind(
		\instrument, "melodyFM",
		\midinote, Pseries(84, -1, 32),
		\dur, 0.125,
		\ctranspose, 12,
		\cmRatio, 2,
		\modIndex, 10,
		\amp, 0.1,
		\att, 0.01,
		\rel, 2,
		\pan, 0
	).play.stop;

	~bassC = Pbind(
		\instrument, "FM2",
		\dur, Pseq([0.5, 0.5, 0.5, 0.25, 0.25, 0.5, 0.5, 0.25, 0.25, 0.25, 0.25], inf),
		\midinote, Pseq([56, 56, 56, 60, 61, 56, 56, 63, 65, 60, 61], inf),
		\dec, 0.1,
		\sus, 0.6,
		\legato, Pseq([0.4, 0.4, 0.4, 0.9, 0.9, 0.4, 0.4, 0.9, 0.9, 0.9, 0.9], inf),
		\cmRatio, 2, // try integers vs non-integers
		\modIndex, 6, // increasing modulation
		\amp, 0.675,
		\ctranspose, -12,
		\pan, -0.25
	).play.stop;

	~bassC2 = Pbind(
		\instrument, "FM2",
		\dur, 0.5,
		\midinote, Pseq([56, 56, 56, 56, 56, 56, 56, 56, 60, 60, 60, 60, 60, 60, 60, 60], inf),
		\dec, 0.1,
		\sus, 0.6,
		\legato, 0.4,
		\cmRatio, 2, // try integers vs non-integers
		\modIndex, 6, // increasing modulation
		\amp, 0.675,
		\ctranspose, -12,
		\pan, -0.25
	).play.stop;

	~bassC3 = Pbind(
		\instrument, "FM2",
		\dur, 0.5,
		\midinote, Pseq([
			60, 60, 60, 60, 60, 60, 60, 60,
			56, 56, 56, 56, 56, 56, 56, 56,
		], inf),
		\dec, 0.1,
		\sus, 0.6,
		\legato, 0.4,
		\cmRatio, 2, // try integers vs non-integers
		\modIndex, 6, // increasing modulation
		\amp, 0.675,
		\ctranspose, -12,
		\pan, -0.25
	).play.stop;

	~organC1 = Pbind(
		\instrument, "organTonewheel0",
		\midinote, Pseq([
			63, 61, 60,
			61, 60, 58,
			60, 58, 56,
			51], inf),
		\dur, Pseq([1.75, 0.125, 0.125, 1.75, 0.125, 0.125, 1.5, 0.25, 0.25, 2], inf),
		\legato, 1,
		\amp, 0.575,
		\pan, 0.25
	).play.stop;

	~organC2 = Pbind(
		\instrument, "organTonewheel0",
		\midinote, Pseq([
			49, 51, 53, 51, 49, 51, 55, 56, 49, 51, 53, 51, 49, 48, 46
		], inf),
		\dur, Pseq([1, 0.5, 0.5, 2, 1, 0.5, 0.5, 2, 1, 0.5, 0.5, 1, 0.5, 0.5, 4], inf),
		\legato, 1,
		\amp, 0.575,
		\pan, 0.25
	).play.stop;

	~otherOrganC1 = Pbind(
		\instrument, "Wobbly Organ",
		\midinote, Pseq([
			[56, 60, 63],
			68,
			[56, 61, 65],
			68,
			[56, 60, 63],
			68,
			[56, 60, 63],
			68
		], inf),
		\dur, 1,
		\att, Pkey(\dur)*0.5,
		\rel, Pkey(\dur)*0.5,
		\amp, Pseq([0.5, 0.4], inf),
		\ctranspose, 0,
		\filterVel, 8,
		\pan, Pseq([-0.5, 0.5], inf)
	).play.stop;

	~otherOrganC2 = Pbind(
		\instrument, "Wobbly Organ",
		\midinote, Pseq([
			[56, 61, 65],
			68,
			[56, 60, 63],
			68,
			[56, 61, 65],
			68,
			[56, 60, 63],
			68,
			[56, 61, 65],
			68,
			[56, 60, 63],
			68,
			[55, 58, 63],
			70,
			[55, 58, 63],
			70,
		], inf),
		\dur, 1,
		\att, Pkey(\dur)*0.5,
		\rel, Pkey(\dur)*0.5,
		\amp, Pseq([0.5, 0.4], inf),
		\ctranspose, 0,
		\filterVel, 8,
		\pan, Pseq([-0.5, 0.5], inf)
	).play.stop;

	~snareC = Pbind(
		\instrument, "snare",
		\dur, Pseq([0.75, 0.125, 0.125, 0.25, 0.25, 0.25, 0.25, 0.75, 0.125, 0.125, 1/6, 1/6, 1/6, 1/6, 1/6, 1/6], inf),
		\att, 0.01,
		\rel, 0.4,
		\freq, 200,
		\ffreq, 3000,
		\amp, 0.2,
		\pan, Pwhite(-0.2, 0.2),
	).play.stop;

	~stringsC = Pbind(
		\instrument, "strings",
		\ctranspose, -12,
		\legato, 0.9,
		\dur, 2,
		\att, 0.1,
		\rel, 0.6,
		\midinote, Pseq([[60, 67, 70, 75], [60, 67, 70, 75], [53, 68, 72, 75], [53, 68, 72, 75]], inf),
		\amp, 0.35,
		\pan, [-1/4, -1/12, 1/12, 1/4]
	).play.stop;

	~bombsFire = Pbind(
		\instrument, "raygun",
		\dur, Pseq([0.25, 3.75], inf),
		\amp, 0.5,
		\pan, -0.5
	).play.stop;

	~bombsBlast = Pbind(
		\instrument, "FM-perc",
		\dur, Pseq([Rest(2), 1, 1], inf),
		\freq, Pseq([\rest, [1000, 1125, 1250, 1375, 1500, 1625, 1750], [1000, 1125, 1250, 1375, 1500, 1625, 1750]-176], inf),
		\modIndex, Pseq([13.5, 20], inf),
		\cmRatio, Pseq([10.37, 15.3], inf),
		\amp, Pseq([\rest, 0.4, 0.15], inf)*0.5,
		\att, 0.01,
		\rel, 1.5,
		\pan, 0.5
	).play.stop;

	~bell = Pbind(
		\instrument, "myBellOriginal",
		\dur, Pseq([2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2]),
		\midinote, Pseq([60], 15),
		\rel, 3,
		\amp, Pseq([0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6, 0.65, 0.7, 0.7, 0.7, 0.7])
	).play.stop;

	~synthD = Pbind(
		\instrument, "uglylead",
		\midinote, Pseq([
			60, 62, 63, 65, 67, 70, 72,
			68, 72, 68, 67, 63, 62, 58,
			60, 62, 63, 60, 62, 63, 64,
			65, 63, 62, 60, 56, 63, 62,
			60, 62, 63, 65, 67, 70, 72,
			68, 72, 68, 67, 63, 62, 58,
			60, 62, 63, 60, 62, 63, 64,
			65, 67, 65, 62, 58, 55, 58
		], inf),
		\dur, Pseq([1.2, 0.8, 0.4, 0.4, 0.4, 0.4, 0.4], 16),
		\ctranspose, 24,
		\amp, 0.4,
		\detune, 0.015,
		\att, 0.2,
		\dec, 0.3,
		\pan, Pwhite(-1/2, 1/2)
	).play.stop;

	// ~bassA.reset.play(~bpm);

	//=====================
	// Score
	//=====================

	~bpm = TempoClock.new(60/60).permanent_(true);

	{
		// first section
		Ndef(\fade, { Out.kr(55, Line.kr(0, 1, 9)) }).play;
		1.wait;
		~chordsA.reset.play(~bpm);
		16.wait;
		~bassA.reset.play(~bpm);
		32.wait;
		~harmonyA.reset.play(~bpm);
		32.wait;
		~hihatA.reset.play(~bpm);
		~kickA.reset.play(~bpm);
		~snareA.reset.play(~bpm);
		32.wait;
		~bpm.tempo=64/60;
		16.wait;
		~bpm.tempo=68/60;
		8.wait;
		~bpm.tempo=70/60;
		Ndef(\fade, { Out.kr(55, Line.kr(1, 0, 7)) }).play;
		4.wait;
		// ~chordsA.stop;
		// Ndef(\fade, { Out.kr(55, Line.kr(1, 0, 4)) }).play;
		4.wait;
		~bpm.tempo=72/60;
		~bassA.stop;
		~harmonyA.stop;
		~kickA.stop;
		~hihatA.stop;
		~snareA.stop;
		~chordsA.stop;
		//second section
		~bassB.reset.play(~bpm);
		8.wait;
		~guitarB.reset.play(~bpm);
		8.wait;
		~kickB.reset.play(~bpm);
		~hihatB.reset.play(~bpm);
		~snareB.reset.play(~bpm);
		16.wait;
		~melodyB.reset.play(~bpm);
		~bellB.reset.play(~bpm);
		~marimbaB.reset.play(~bpm);
		32.wait;
		~melodyB.stop;
		~bellB.stop;
		~marimbaB.stop;
		~bassB.stop;
		~snareB.stop;
		~kickB.stop;
		~pianoB.reset.play(~bpm);
		16.wait;
		~pianoB.stop;
		~bassB.reset.play(~bpm);
		~snareB.reset.play(~bpm);
		~kickB.reset.play(~bpm);
		~melodyB.reset.play(~bpm);
		~bellB.reset.play(~bpm);
		~marimbaB.reset.play(~bpm);
		~hit.reset.play(~bpm);
		16.wait;
		~secondMelodyB1.reset.play(~bpm);
		16.wait;
		~secondMelodyB1.stop;
		~secondMelodyB2.reset.play(~bpm);
		8.wait;
		~secondMelodyB2.stop;
		~secondMelodyB3.reset.play(~bpm);
		8.wait;
		~secondMelodyB3.stop;
		~secondMelodyB4.reset.play(~bpm);
		8.wait;
		~secondMelodyB4.stop;
		~secondMelodyB5.reset.play(~bpm);
		8.wait;
		~secondMelodyB5.stop;
		~melodyB.stop;
		~bellB.stop;
		~marimbaB.stop;
		~bassB.stop;
		~hihatB.stop;
		~snareB.stop;
		~kickB.stop;
		~hit.stop;
		~bell.reset.play(~bpm);
		16.wait;
		~bpm.tempo = 36/60;
		2.wait;
		~bpm.tempo = 60/60;
		~guitarB.stop;
		//third section
		~bTocTransition.reset.play(~bpm);
		2.wait;
		~bassC.reset.play(~bpm);
		16.wait;
		~organC1.reset.play(~bpm);
		16.wait;
		~organC1.stop;
		~organC2.reset.play(~bpm);
		16.wait;
		~organC2.stop;
		~organC1.reset.play(~bpm);
		~otherOrganC1.reset.play(~bpm);
		~snareC.reset.play(~bpm);
		16.wait;
		~organC1.stop;
		~organC2.reset.play(~bpm);
		~otherOrganC1.stop;
		~otherOrganC2.reset.play(~bpm);
		16.wait;
		~organC2.stop;
		~otherOrganC2.stop;
		~bombsFire.reset.play(~bpm);
		~bombsBlast.reset.play(~bpm);
		16.wait;
		~bassC.stop;
		~bassC2.reset.play(~bpm);
		16.wait;
		~stringsC.reset.play(~bpm);
		~bassC2.stop;
		~bassC3.reset.play(~bpm);
		32.wait;
		~bassC3.stop;
		~snareC.stop;
		~bombsFire.stop;
		~bombsBlast.stop;
		~bassA.reset.play(~bpm);
		16.wait;
		~stringsC.stop;
		16.wait;
		~harmonyA.reset.play(~bpm);
		14.wait;
		Ndef(\fade, { Out.kr(55, Line.kr(0, 1, 0.01)) }).play;
		~bassA.stop;
		~harmonyA.stop;
		2.wait;
		~bassA.reset.play(~bpm);
		~harmonyA.reset.play(~bpm);
		~chordsA.reset.play(~bpm);
		~hihatD.reset.play(~bpm);
		~snareD.reset.play(~bpm);
		~kickD.reset.play(~bpm);
		32.wait;
		~synthD.reset.play(~bpm);
		60.wait;
		~snareD.stop;
		~snareD2.reset.play(~bpm);
		4.wait;
		Ndef(\fade, { Out.kr(55, Line.kr(1, 0, 0.01)) }).play;
		~hihatD.stop;
		~chordsA.stop;
		~kickD.stop;
		~synthD.stop;
		~snareD2.stop;
		~harmonyA.stop;
		~bassA.stop;
		~bassD.reset.play(~bpm);
		1.wait;
		~bassD.stop;
	}.fork(~bpm);

}); // end of waitForBoot

)