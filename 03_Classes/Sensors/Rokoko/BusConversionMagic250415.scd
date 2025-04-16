//:火 15  4 2025 20:49
// Write all data from a single rokoko osc message at once in a 61-channel bus,
// (globas joint data bus).
// For accessing the data of individual joint-variables (e.g. head-x, head-y etc),
// Use Bus.new and calculate the precise index of the bus from the structure
// of the data in the global joint data bus.
// Here: Proof of concept.

//:1. Create a global bus. For an actor, the bus would have 161 channels.
~global = Bus.control(Server.default, 4 /* 161 */);
//:2. Create 2 single channel buses correspoding to individual joint variables
~freq = Bus(\control, ~global.index + 0, 1, Server.default);
~amp = Bus(\control, ~global.index + 1, 1, Server.default);
//:3. Set the values of individual joints separately.
~freq.set(400);
~amp.set(0.1);
//:4. Start a synth to hear the individual buses
~synth = { | freq = 1000, amp = 0.01 |
	SinOsc.ar(freq, 0, amp).dup
}.play;
//:5 map the controls of the synth to the individual buses
~synth.map(\freq, ~freq);
~synth.map(\amp, ~amp);
//:6 Test setting multiple values of the global bus
//(As would be done by Rokoko when receiving OSC messages.)
r = {
	loop {
		~global.setn([100 rrand: 1000, [0, 0.1].choose]);
		0.5.wait;
	}
}.fork;
//:7. Stop the test routine
r.stop;
//:8. Output synth signals to the individual busses
{ Out.kr(~amp, LFPulse.kr(2).range(0.0, 0.1).lag(0.2)) }.play;
{ Out.kr(~freq, LFNoise1.kr(1.5).range(100, 1000)) }.play;
//: