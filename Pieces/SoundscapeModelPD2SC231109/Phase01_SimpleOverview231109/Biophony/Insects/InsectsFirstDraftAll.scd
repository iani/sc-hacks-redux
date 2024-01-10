/*  9 Nov 2023 17:16

*/
//: ----- Cricket1 (Simplest example!?) ------
//:Version 00
{ WhiteNoise.ar(0.1).dup } +> \cricket1;
//:Version 01 : BPF = Band pass filter
{ var src, bpf, freq, bp;
	freq = \freq.kr(8900);
	bp = \bp.kr(0.15);
	src = WhiteNoise.ar(0.01);
	bpf = BBandPass.ar(src, freq, bp);
	bpf.dup;
} +> \cricket1;
//:Version 02 : amplitude envelope with ring modulation.
{
	var src, bpf, freq, bp, env;
	var carrier, modulator, carrfreq, modfreq;
	carrfreq = 20;
	modfreq = 55;
	carrier= SinOsc.kr(carrfreq,0,0.5);
	modulator= SinOsc.kr(modfreq,0,0.5);
	env = carrier*modulator;
	freq = \freq.kr(8900);
	bp = \bp.kr(0.15);
	src = WhiteNoise.ar(0.1);
	bpf = BBandPass.ar(src, freq, bp, env);
	bpf.dup;
} +> \cricket1;
//:Version 03 : envelope for overall amplitude. TARGET
{
	var src, bpf, freq, bp, ringenv, ampenv;
	var carrier, modulator, carrfreq, modfreq;
	carrfreq = 20;
	modfreq = 55;
	carrier= SinOsc.kr(carrfreq,0,0.5);
	modulator= SinOsc.kr(modfreq,0,0.5);
	ringenv = carrier*modulator;
	freq = \freq.kr(8900);
	bp = \bp.kr(0.15);
	ampenv = Env([0, 1, 0, 0, 1, 0], [1, 0.034, 0.030, 0.020, 0.034]);
	src = WhiteNoise.ar(0.1);
	bpf = BBandPass.ar(src, freq, bp, ringenv);
	bpf.dup * ampenv.kr(2);
} +> \cricket1;
//:Version 03 : modulate frequencies of ring modulators.
{
	var src, bpf, freq, bp, env;
	var carrier, modulator, carrfreq, modfreq;
	carrfreq = LFNoise2.kr(3.reciprocal).range(15, 25);
	modfreq = LFNoise2.kr(3.reciprocal).range(50, 60) ;
	carrier= SinOsc.kr(carrfreq,0,0.5);
	modulator= SinOsc.kr(modfreq,0,0.5);
	env = carrier*modulator;
	freq = \freq.kr(8900);
	bp = \bp.kr(0.15);
	src = WhiteNoise.ar(0.01);
	bpf = BBandPass.ar(src, freq, bp, env);
	bpf.dup;
} +> \cricket1;
//:ringmodulation!!!

{

var carrier, modulator, carrfreq, modfreq;

carrfreq= MouseX.kr(440,5000,'exponential');

modfreq= MouseY.kr(1,5000,'exponential');

carrier= SinOsc.ar(carrfreq,0,0.5);

modulator= SinOsc.ar(modfreq,0,0.5);

carrier*modulator;

}.scope



//:
\cricket1.player.set(\bp, 0.15);
//:Cricket2

//:Cricket3

//:Cricket4
