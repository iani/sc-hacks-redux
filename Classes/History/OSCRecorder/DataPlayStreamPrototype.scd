/* 24 Oct 2022 11:38

Note: To loop around subsectios of the data use Pindex. Example:

(degree: Pindex((0..70), Pser((5..(5-10)).abs, inf)), dur: 0.15) +> \test;

*/
Project.gui;

["1x", "x2"] do: { | x | { x.interpret; }.try({ | it | \blah.postln; }); };


["z1", "x2"] do: { | x | x.interpret.postln; }



OscDataReader.openDialog;
//:
~dataSource = OscDataReader.allData;
~dataSource.size;
~score = ~dataSource.flop;
~score.size;
~score[0] = ~score[0].differentiate.put(0, 0).rotate(-1);
~score = ~score.flop;
~score.size;
//:
(score: Pindex(~score, Pseries(0, -1, 30) + 1000),
// dur: Pfunc({ postln("===== Dur" + ~score[0] + "======="); 1 }),
play: { ~score.postln; ~dur = ~score[0] * 50 }) +> \test;
//:
Buffer.all;
\birdcarman.buf.play;

Pseries(0, 1, 3).asStream.nextN(10)

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

//:
a = (1..10).clump(2);
b = a.flop;
b[0] = b[0].differentiate;
b[0].differentiate;
a
b