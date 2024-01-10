/*  4 Dec 2022 14:19
Modify your stream at will.

(degree: -10, dur: 5,  x: Pmod({ ~freq = [50, 62, 64, 123].choose.midicps; ~dur = 0.1 exprand: 0.5 })) +> \test;
*/

Pmod {
	var <>func;

	*new { | func | ^this.newCopyArgs(func) }

	asStream { | event, stream |
		// TODO:
		// Provide initial values for the stream
		// take any values that you need from the event
		// Make these into streams and get their first element!
		// Must be done after all non-Pmods. ... ???
		// to avoid overwriting any values that we initialized here

	}
	next { | in |
		// postln("the event is:" + in);
		in.use(func);
	}
}