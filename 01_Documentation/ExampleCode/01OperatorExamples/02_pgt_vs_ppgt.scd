/*
Test ++> and +> to work as intended
	1. WHen replacing a synth
	3. When restarting (++>) or modifying (+>) an EventSteram
	between the 2 lines
*/

//:Evaluate the following one line at a time
{ PinkNoise.ar(0.02).dup } +> \x;
() +> \x; 				// Event replaces synth with EventStream
(dur: 0.2) +> \x;      	// add dur key to running EventStream
(degree: 10) +> \x;    	// add degree key to running EventStream
(dur: 0.1) +> \x;      	// modifuy dur key in running EventStream
nil +> \x;				// stop current player.  (EventStream)
{ GrayNoise.ar(0.02).dup } +> \x; // restart current player
nil +> \x;				// stop current player.  (Synth)
() +> \x; 				// Event restarts EventStream
(dur: 0.10) !> \x;
() !> \x;
(degree: 10) !> \x;
nil +> \x;				// stop current player.  (Synth)
(degree: 10) !> \x;
//:
{
	{ PinkNoise.ar(0.02).dup } +> \y;
	1.wait;
	() +> \y;
	1.wait;
	(dur: 0.2) +> \y;
	1.wait;
	(degree: 10) +> \y;
	1.wait;
	(dur: 0.2) +> \y;
	1.wait;
	() +> \y;
	nil +> \y;
}.fork;
//:
(degree: 10) +> \x;

//:
() !> \y;
(dur: 0.10) !> \y;
(degree: 10) !> \y;

~x;
~x.stop;
1.stop;
nil.stop;