/*
25 May 2021 16:08
Nano introduction to trying sc-hacks-redux, 
also as a way of sharing code via OSCGroups.
*/

// ================================================================
/* PART 1
 operators: =+>=, =++>=
*/
// =+>= Send a new sound to a player and start playing

// =+>= sends a Function or an Event to a player, and starts playing it. 

{ PinkNoise.ar(0.1).dup } +> \test; // play a function. creates a synth
(degree: Pseq((0..7), 5), dur: 0.1) +> \test; // play an event. creates EventStream
(degree: Pbrown(-10, 10, 2, dur: 1/3)) +> \test; // play a different event
{ WhiteNoise.ar(0.1).dup } +> \test; // play a different function


/* Here the player is named =\test=
The Event or Function that you send to that player
stops anything currently playing in that player, and starts something new.
*/

// =++>= Add some keys-values to a pattern and start playing

{ PinkNoise.ar(0.1).dup } +> \test; // play a function. creates a synth
(degree: Pseq((0..7))) ++> \test; // start an event, replacing the function
(dur: 0.05) ++> \test; // Add a stream tp the eventstream while it is playing

// Add a stream tp the eventstream while it is playing
(dur: Prand([0.05 !! 20, 0.1 !! 10, 0.2 !! 5, 0.5 !! 2, 1])) ++> \test;

// ================================================================
// PART 2  messages: =stop=, =start=

(degree: Pseq((0..7), inf), dur: 0.1) +> \test; // start a pattern in player \test;
\test.stop // stop player \test
\test.start // start player \test

// Currently works only with patterns. Cannot restart synths.
