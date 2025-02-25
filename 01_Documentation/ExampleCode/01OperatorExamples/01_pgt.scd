// +>

// ===== Function +> : Play function as synth =====

//: Example 1: Play one sound
{ SinOsc.ar(400 rrand: 4000, 0, 0.05).dup } +> \test;

//: Example 2: Starting a new sound stops the previous one
{
	10 do: {
		{ SinOsc.ar(400 rrand: 4000, 0, 0.05).dup } +> \test;
		0.5.wait;
	};
	// nil +> stops the sound;
	nil +> \test;
}.fork

//: Example 3: Cross fade
{
	\test.push; // play in \test
	~fadeTime = 3; // modifies Mediator \test
	10 do: { | i |
		postln(i + 1);
		{ SinOsc.ar(400 rrand: 520.0, 0, 0.05).dup } +> \test;
		3.5.wait;
	};
	nil +> \test;
}.fork

// ===== Event +> : Play Event as EventStream =====

//: Example 4: Play a single Event as Event Stream
(degree: Pbrown(-10, 10, 1), dur: 0.05) +> \test;

//: Example 5: Alternate between EventStream and Synth
{
	var e = (degree: Pbrown(-10 rrand: -1, 4 rrand: 10, 1 rrand: 3), dur: 0.05);
	var f = { SinOsc.ar(400 rrand: 520.0, 0, 0.05).dup };
	10 do: {
		[e, f].choose +> \test;
		3.rrand(7.0).wait;
	};
	nil +> \test;
}.fork;