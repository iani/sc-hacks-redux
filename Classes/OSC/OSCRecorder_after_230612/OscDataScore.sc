/* 20 Jun 2023 23:29

Subclass of OscData that introduces 2 differences:

1. treats relative time values instead of absolute ones:

//:--[0.1]
// Here 0.1 is a time relative to the time of the previous message.

2. The contents of the message are treated implicitly as code.
//:--[1.5]
{ WhiteNoise.ar(0.1).dup } +> \test;

Is equivalent to:
//:--[1.5]
['/code', "{ WhiteNoise.ar(0.1).dup } +> \test;"]

*/

OscDataScore : OscData {

	convertTimes { times = times.integrate }

	makePlayFunc {
		var addr;
		addr = LocalAddr();
		^{ addr.sendMsg('/code', ~message) }
	}
}
