/* 16 Feb 2021 09:03
Copy of post on scsynth.org
https://scsynth.org/t/cybernetic-music-with-supercollider/3184

Cybernetic music with SuperCollider
Resources
SynthDefs
Geoffroy
19d

Hello

Hope you’re all going well.

Like many, I stumbled upon the works of Roland Kayn 14 and the whole cybernetics movement with the 2008 reissue of his 3 LPs long Simultan 5.

I was amazed at how different, richer, more diverse it sounded compared to the works of early electronic pionneers using east or west coast synthesis.
The boxset includes diagrams and photos of the analog computer used for these compositions, and the liner notes mention the use of feedback loops.

I would recommend anyone interested to read the article on Wikipedia 7 about Cybernetics, because it’s an example of transdisciplinary movement with researchs on IT, biology, psychology (Palo Alto), among others. I wish there would be more transdisciplinary approaches now !

Anyway, to get back to cybernetics in music, there was also a recent thread on Lines forum 6, which linked to videos on Youtube by composer La Synthèse Humaine 5. He uses a Serge modular synthesizer but I thought it would be nice to try and translate his experiments to SuperCollider.

I mentionned that yesterday at the wonderful Notam SC Meetup and people suggested we would start a thread on scsynth.org.
So here we are !

I’ve just started with the first video of La Synthèse Humaine.

*/

//:    Feedback loop with a filter only.
//    https://youtu.be/MnYkqlSIr_I?t=177 20

(
Ndef(\test, {
	var in, sig;
	in = LocalIn.ar(1) * -60.dbamp;
	sig = DFM1.ar(in, \filter_freq.kr(1000, 0.1), \filter_res.kr(0.1, 0.1));
	LocalOut.ar(sig);
	sig.tanh;
}).play;

Spec.add(\filter_freq, [0.1, 14000, 'exp', 0, 440, "Hz"].asSpec);
Spec.add(\filter_res,[0, 1.5, 'lin', 0.001, 0.1].asSpec);
)
//:
Ndef(\test).gui;
//:Feedback loop with sine oscillator
//    https://youtu.be/MnYkqlSIr_I?t=268 9

(
Ndef(\test, {
	var freq, sig;
	freq = LocalIn.ar(1);
	sig = DFM1.ar(SinOsc.ar(freq: freq * \sin_freq.kr(1.0, 0.1)), \filter_freq.kr(1000, 0.1), \filter_res.kr(0.1, 0.1));
	LocalOut.ar(sig);
	sig.tanh;
}).play;

Spec.add(\freq, [0.1, 14000, 'exp', 0, 440, "Hz"].asSpec);
Spec.add(\sin_freq, [0.1, 14000, 'exp', 0, 440, "Hz"].asSpec);
Spec.add(\filter_freq, [0.1, 14000, 'exp', 0, 440, "Hz"].asSpec);
Spec.add(\filter_res,[0, 1.5, 'lin', 0.001, 0.1].asSpec);

Ndef(\test).gui
)

//: Feedback loop with two sine oscillators and FM synthesis
// https://youtu.be/MnYkqlSIr_I?t=501 4

(
Ndef(\test, {
	var in, sig, sine1, sine2;
	in = LocalIn.ar(1);
	sine2 = SinOsc.ar(\sin2_freq.kr(440.0));
	sine1 = SinOsc.ar(freq: \sin_freq.kr(440.0) * (1 + (\fm_amount.kr(0) * sine2)) * in);
	sig = DFM1.ar(
		sine1,
		\filter_freq.kr(1000),
		\filter_res.kr(0.1)
	);
	LocalOut.ar(sig);
	LeakDC.ar(sig.tanh);
}).play;

Spec.add(\freq, [0.1, 14000, 'exp', 0, 440, "Hz"].asSpec);
Spec.add(\sin_freq, [0.1, 14000, 'exp', 0, 440, "Hz"].asSpec);
Spec.add(\sin_freq2, [0.1, 14000, 'exp', 0, 440, "Hz"].asSpec);
Spec.add(\fm_amount, [0, 10, 'lin', 0, 0].asSpec);
Spec.add(\filter_freq, [0.1, 14000, 'exp', 0, 440, "Hz"].asSpec);
Spec.add(\filter_res,[0, 1.5, 'lin', 0.001, 0.1].asSpec);

Ndef(\test).gui;
)

//:
/*
I am not sure my SC code is faithfull to the videos.
I don’t really know why I’m using the “*” operation with the feedback loop, and not the “+” operation (which was my first guess).
Also, I imagine that the delay introduced by LocalIn / LocalOut has an effect on the sound (phase delay ?) but I can’t really notice it.

Please discuss !

Best

Geoffroy
*/