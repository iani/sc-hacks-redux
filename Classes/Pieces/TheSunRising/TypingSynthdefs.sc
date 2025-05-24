// 土 24  5 2025 17:54
// Synthefs for TypingPlayer
// Load when server boots.

TypingSynthdefs {
	*initClass {
		StartUp do: {
			ServerBoot add: { this.loadAll }
		}
	}

	*loadAll {
		"============ Loading TypingPlayer SynthDefs ==============".postln;
		[\sline1, \sline2, \sline3, \sline4, \sline5] do: { | d |
			postln("Loading" + d + "...");
			this.perform(d).post.add.post;
			postln("... loaded" + d);
		};
		"============ Loaded TypingPlayer SynthDefs ==============".postln;
	}

	*sline1 {
		^SynthDef(\sline1, { | out = 0, freq = 400, amp = 0.1, dur = 0.1 |
			var env, line, src, panline;
			env = Env.perc(0.02, dur - 0.02 max: 0.01);
			line = Line.kr(freq, { freq * Rand(0.5, 2) } ! 2, dur, doneAction: 2);
			src = Mix(SinOsc.ar(line)) / 2;
			panline = Line.kr(Rand(-1.0, 1.0), Rand(-1.0, 1.0), dur, doneAction: 2);
			src = Pan2.ar(src, panline, amp);
			Out.ar(out, src * env.kr(1, doneAction: 2));
		})
	}
	*sline2 {
		^SynthDef(\sline2, { | out = 0, freq = 400, amp = 0.1, dur = 0.1 |
			var env, line, src;
			env = Env.perc(0.02, dur - 0.02 max: 0.01);
			line = Line.kr(freq, { freq * Rand(0.5, 2) } ! 5, dur, doneAction: 2);
			src = Mix(SinOsc.ar(line)) / 5;
			Out.ar(out, Pan2.ar(src, Rand(-1.0, 1.0), amp) * env.kr(1, doneAction: 2));
		})
	}
	*sline3 {
		^SynthDef(\sline3, { | out = 0, ffreq=0.3, ascii = 50, dur = 1, amp = 0.1 |
			var src, env, freq, lfo, noise, decay;
			lfo = SinOsc.kr(ffreq).exprange(0.3,0.9);
			noise = WhiteNoise.ar(1);
			ascii = ascii.wrap(1, 127).linlin(1, 127, 1.01, 2);
			decay = ascii.wrap(1, 127).linlin(1, 127, 0.1, 0.9);
			// dur = Rand(0.2, 1.8);
			// amp = Rand(0.01, 0.1);
			freq = Line.kr(Rand(100, 1000), Rand(100, 1000), dur,
				doneAction: 2
			);
			env = Env.perc(0.1, dur - 0.1);
			src = Ringz.ar(
				noise,
				freq * [1, ascii],
				decay
			) * env.kr(1, doneAction: 2);// amp * 10: make default more audible
			Out.ar(0, Lag.ar(src.fold(lfo.neg,lfo)) * amp * 10);
		})
	}
	*sline4 {
		^SynthDef(\sline4, { | ascii = 50 |
			var src, env, freq, dur, amp;
			ascii = ascii.wrap(1, 127).linlin(1, 127, 1.01, 2);
			dur = Rand(0.1, 0.8);
			amp = Rand(0.01, 0.1);
			freq = Line.kr(Rand(100, 1000), Rand(100, 1000), dur,
				doneAction: 2
			);
			env = Env.perc(0.01, dur - 0.01, amp);
			src = SinOsc.ar(freq * [1, ascii]) * env.kr(1, doneAction: 2);
			Out.ar(0, src);
		})
	}

	*sline5 {
		^SynthDef(\sline5, { | out = 0, freq = 400, amp = 0.1, dur = 0.1 |
			var env, line, src, att, dec;
			att = dur * 0.3;
			dec = dur * 0.7;
			env = Env.perc(att, dec);
			line = Line.kr(freq, { freq * Rand(0.5, 2) } ! 5, dur, doneAction: 2);
			src = Mix(SinOsc.ar(line)) / 5;
			Out.ar(out, Pan2.ar(src, Rand(-1.0, 1.0), amp) * env.kr(1, doneAction: 2));
		})
	}
}