
\test.push;
~freq = 3700;

{ SinOsc.ar(\freq.kr(400 rrand: 1000), 0, 0.01).dup } +> \test;

~freq = ~freq2;
~freq.set(1000 rrand: 1100);
~freq = Bus.control;
{ Out.kr(~freq.index, LFNoise0.kr(3).range(200, 2000)) }.play;

~freq = nil;
//:
{
	100 do: {
		~freq = 200 rrand: 2000;
		0.1.wait;
	}
}.fork
//:
\test.envir.synthArgs;
~fadeTime = 5.0;
~test.release;
\test.envir;
currentEnvironment
~a = 1;

\test2.envir;

~bustest = Bus.control;
~bustest = 123;
~bustest;
Bus.control

\b <== 1000.rand;

currentEnvironment


~b.get({ | ... args | args.postln; });

a = Synth(\default);
a.onStart({ "synth started".postln; })

a.onEnd({ "synth ended".postln; })