/* 26 Jan 2021 12:48
Simple examples for Mediator
*/
Mediator.push; // initialize the system with this
// then execute any of the following lines in different order:
~b = { WhiteNoise.ar(0.05).dup }.play;
~b = { SinOsc.ar(50.rrand(90).midicps, 0, 0.1).dup }.play;
~b = Pbind(\dur, 0.05.exprand(0.5), \degree, Pwhite(0.rrand(20), 21.rrand(30))).play;
//stop using Mediator
Mediator.pop;