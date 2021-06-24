/*  9 Jun 2021 15:59
Test Function +> \envir;
*/

//: First test underneath the hood:
~a = { WhiteNoise.ar(0.1).dup }.asSynth;

~a = { SinOsc.ar(\freq.kr(1400) * [1, 1.1], 0, 0.6) }.asSynth;


currentEnvironment.dependants;

currentEnvironment.changed(\x, \y);

~a.changed(\x, \u);

~freq = 2000;

~freq = Bus.control;
~freq.set(1400);