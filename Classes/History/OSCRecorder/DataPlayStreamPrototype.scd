/* 24 Oct 2022 11:38

*/

OscDataReader.openDialog;
~data = OscDataReader.allData;
~times = ~data.flop[0].differentiate.put(0, 0).rotate(-1);  // !!!!!
~times do: _.postln;

(dur: ~times.pseq(1), degree: (0..10).prand) +> \test;
//:
(dur: ~times.pseq(1), data: ~data.pseq, count: Pseries(0, 1, inf),
addr: NetAddr.localAddr,
play: {
~count.postln;
~dur.postln;
	~data.postln;
	~addr.sendMsg(*~data[1])
}) +> \test;
//:
OSC.trace;

//OscGroups.enable;
\code >>>.test { | a, code |
	postln("I should evaluate the following code:" + code[1]);
	code[1].asString.interpret;
};