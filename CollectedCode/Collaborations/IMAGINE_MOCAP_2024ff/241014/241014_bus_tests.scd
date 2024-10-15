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

//: Test of K2A.ar linlin
{ SinOsc.ar(K2A.ar(LFSaw.kr(1).linlin(0, 1, 400, 4000), 0, 0.1)) }.play;

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