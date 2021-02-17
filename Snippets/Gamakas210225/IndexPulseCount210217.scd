/* 17 Feb 2021 14:46
Using pulse count to access values from a buffer with Index.
*/
Mediator.push;
// b = Buffer.alloc(Server.default, 8, 1);
v = ((20..30) * 3).midicps  rotate: 1;
// Since Stepper/Index start at 1, rotate the data to make 1 be the
// first element. 
b = Buffer.alloc(Server.default, v.size, 1);
b.setn(*(v.collect({ | v, c | [c, v]}).flat));
// b.setn(*v);
(0..7) do: { | i | b.get(i, { | v | postf("got value: %\n", v)}); };
//:
{
	SinOsc.ar(
		Index.kr(b.bufnum, PulseCount.kr(Impulse.kr(1)) % v.size),
		0, 0.1).dup
} +> \test;
//:as above, with stepper
{ arg out=0;
        SinOsc.ar(
			Index.kr(
				b.bufnum,
				Stepper.kr(Impulse.kr(2), 0, 0, v.size - 1, 1),
			),
			0, 0.05
    ).dup
} +> \test;
//:
v.rotate(-1);