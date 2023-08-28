/* 27 Aug 2023 23:56

Smooth OSC input in the server before sending to of.

==== The data sources in busses: ====

The busses used are:

\x1, \y1, \z1, \x2, \y2, \z2 ... \x12, \y12, \z12

~busses = (1..12) collect: { | i | [\x, \y, \z] collect: { | s | i.sensor(s) } };

Format for sending:

[/minibee id, x, y, z]

1.sensor(\x)

Use SendReply:
{
	var sensors;
	sensors = (1..12).collect({ | i | [\x, \y, \z]
	collect: { | s | i.sensor(s).lag(0.5) }
	}).flat;
	SendReply.ar(Impulse.kr(50), cmdName: '/minibeesmooth', values, replyID: -1)
}

'/minibeesmooth' >>> { | ... args | LocalAddr().sendMsg('/minibeesmooth', *args) }

// New draft, Mon 28 Aug 2023 09:59
(1..12).collect(_.slag).flat;

\x >>> { | n, args | args.postln; };

*/
//: send everything all the time
{
	var sensorlags;
	sensorlags = (1..12).collect(_.slag).flat;
	SendReply.kr(Impulse.kr(50), '/minibeesmooth', sensorlags);
} +> \minibeesmooth_test;
//: Send only when sensor values change - first without the change test
{
	var sensorlags, changectl;
	sensorlags = (1..12).collect(_.slag);
	changectl = 1;
	SendReply.kr(Impulse.kr(50) * changectl,
		'/minibeesmooth', sensorlags[4]);
} +> \minibeesmooth_test;

//: Send only when sensor values change - now with the change test
//: this does not seem to have any effect.
{
	var sensorlags, changectl;
	sensorlags = (1..12).collect(_.slag);
	changectl = (sensorlags[4] collect: Changed.kr(_)).sum;
	SendReply.kr(Impulse.kr(50) * changectl,
		'/minibeesmooth', sensorlags[4]);
} +> \minibeesmooth_test;

//:
\minibeesmooth >>> { | n, args |
	args[3..].postln
};
//:
\minibeesmooth >>> { | n, args |
	args[3..].size.postln };
//:
\minibeesmooth >>> nil;
//:
20.reciprocal;
//:
{
	SinOsc.ar(
		[
			LFSaw.kr(0.1).range(400, 1600),
			LFPulse.kr(10).range(0, 50),
			LFNoise0.kr(10).range(-50, 50)
		].sum, 0, 0.1
	)
} +> \test;