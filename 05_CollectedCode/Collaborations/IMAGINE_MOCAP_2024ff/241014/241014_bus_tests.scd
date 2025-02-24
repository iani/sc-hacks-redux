// Prep
OscMonitor.gui;
//:
OscGroups.enable;
//:
PosePerson.gui;

//: show values of busses for 2 persons:
//:
PosePerson.getBusses(0) do: { | b | b.get.postln; };
PosePerson.getBussesKr(0) do: { | b | [b, b.rate].postln; };
PosePerson.getBussesAr(0) do: { | b | [b, b.rate].postln; }
//:
PosePerson.bar;


PosePerson.at(0)

PosePerson.getBussesAr(0)

//:
//: Test of K2A.ar linlin
{ SinOsc.ar(K2A.ar(LFSaw.kr(1).linlin(0, 1, 400, 4000)), 0, 0.01).dup }.play;

//: test bus ar collection

{
	PosePerson.bar(0)[..10].collect({ | b | b.linlin(0, 1, 1000, 5000) })
	.collect({ | f | SinOsc.ar(f, 0, 0.01)}.sum; );
}.play;
//:
{
	var src;
	src = PosePerson.bak(0)[..50] collect:
	{ | f |  SinOsc.ar(Lag.kr(f.linlin(0, 1, 200, 15000), 2.5), 0, 0.01) };

	Mix(src);

} +> \test;
//:
{
	var src;
	src = PosePerson.bak(0)[..50].clump(3) collect:
	{ | f |
		var x, y, z;
		#x, y, z = f;
		SinOsc.ar(Lag.kr(x.linlin(0, 1, 200, 15000), 2.5), 0, 0.01);
;
	};
	Mix(src);

} +> \test;
//:
{
	var src;
	src = PosePerson.bak(0)[..50] collect:
	{ | f |
		var x,y,z;
		#x, y, z = f;

		SinOsc.ar(Lag.kr(x.linlin(0, 1, 200, 1000), 0.5), 0, 0.01) };

	Mix(src.flat);

} +> \test;
//:
{
	var src;
	src = PosePerson.bak(0)[..50].clump(3).collect(
	{ | f |
		#x, y, z = f;
		LFPulse.ar(
			Lag.kr(x.linlin(0, 1, 100, 500), 1.5),
			0, 0.5, 0.1)
			// 0,
			// 0.5, y),
		)
	}).flat;

	Mix(src);

} +> \test;
//:
{
	var src;
	src = PosePerson.bak(0)[..50].clump(3).collect(
	{ | f |
		#x, y, z = f;
		[SinOsc.ar(Lag.kr(x.linlin(0, 1, 100, 500), 1.5), 0, 0.01),
			//	LFPulse.ar(Lag.kr(y.linlin(0, 1, 1000, 1500), 1.5), 0, 0.5, 0.01),
			// LFSaw.ar(Lag.kr(z.linlin(0, 1, 1500, 2000), 1.5), 0, 0.01),
		];

	}).flat;

	Mix(src);

} +> \test;
//:
Server.default.scope(16);
//:
PosePerson.all[1].busses do: { | b | b.get.postln; };
//:collect the values for 2 busses:
~pb = PosePerson.all collect: _.busses;
//: post the collected busses
~pb
//: put the 2 persons' busses into different env vars:
~pp1 = PosePerson.all[0];
~pp2 = PosePerson.all[1];
~pb1 = ~pp1.busses;
~pb2 = ~pp2.busses;
//: CHeck pb1, pb2:
~pb1.postln;
~pb2.postln;
//:simple sonification of pb1
{ ~pb1 collect: { WhiteNoise.ar(0.01) } }  +> \pb1s
//:simple sonification of pb1
{ ~pb1 collect: { | i |
	Ringz.ar(
		WhiteNoise.ar(0.01),
		i.index.linlin(0, 120, 100, 5000) * 2
	)
}
}  +> \pb1s
//:simple sonification of pb1
{ ~pb1 collect: { | i |
	Ringz.ar(
		WhiteNoise.ar(0.01),
		In.kr(i.index).linlin(0, 120, 100, 5000).a2k
	)
}
}  +> \pb1s
//:simple sonification of pb2
~pb1[1].index;
//;


//:
{ In.kr(~pb[1].index.postln) } +> \test;


~pb1

.busses[1]