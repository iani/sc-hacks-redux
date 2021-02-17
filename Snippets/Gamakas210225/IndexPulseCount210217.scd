/* 17 Feb 2021 14:46
Using pulse count to access values from a buffer with Index.
*/
Mediator.push;
b = Buffer.alloc(Server.default, 8, 1);
v = (1..8) * 400;
b.setn(*(v.collect({ | v, c | [c, v]}).flat));
(0..7) do: { | i | b.get(i, { | v | postf("got value: %\n", v)}); };
//:
{
	SinOsc.ar(
		Index.kr(b.bufnum, PulseCount.kr(Impulse.kr(5, (pi/8).neg)) % 8),
		0, 0.1).dup
} +> \test;