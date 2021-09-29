/* 17 Feb 2021 14:22
Comment to https://scsynth.org/t/cybernetic-music-with-supercollider/3184/2
by Geoffroy.
*/
(
Ndef(\test, {
	var in, sig, osc1, osc2;
	in = LocalIn.ar(1);
	osc1 = SinOsc.ar(freq: \osc1_freq.kr(150)) * \osc1_amp.kr(0.4);
	osc2 = SinOsc.ar(freq: \osc2_freq.kr(448)) * \osc2_amp.kr(-0.3);
	osc1.max(osc2).distort;
}).play;

Spec.add(\osc1_freq, [0.1, 14000, 'exp', 0, 440, "Hz"].asSpec);
Spec.add(\osc2_freq, [0.1, 14000, 'exp', 0, 440, "Hz"].asSpec);
Spec.add(\osc1_amp, [-1, 1, 'lin', 0, 0].asSpec);
Spec.add(\osc2_amp, [-1, 1, 'lin', 0, 0].asSpec);

Ndef(\test).gui;
)