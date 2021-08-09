/*  9 Aug 2021 11:14
From: https://scsynth.org/t/what-kind-of-synthesis-is-this/4318
*/

(
{
	var trig, snd, freqs;
	trig = Impulse.ar(LFNoise2.kr(1).linexp(-1, 1, 1, 100));
	freqs = (60.5 + [0, 2, 4, 5, 7, 9, 10]).midicps;
	snd = Pluck.ar(Hasher.ar(Sweep.ar(trig)) * -10.dbamp, trig, freqs.reciprocal, freqs.reciprocal, 0.9, 0.5);
	snd = LeakDC.ar(snd).sum;
	snd = MoogFF.ar(snd, LFNoise2.kr(1).linexp(-1, 1, 500, 16000), 0);
	snd = snd ! 2;
	snd;
}.play(fadeTime: 0);
)