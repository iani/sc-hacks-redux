/* 27 Oct 2022 20:02

*/
/* 24 Oct 2022 11:38

Note: To loop around subsectios of the data use Pindex. Example:

(degree: Pindex((0..70), Pser((5..(5-10)).abs, inf)), dur: 0.15) +> \test;

*/

ScriptDataReader.openDialog;
//:
~dataSource = Library.at(ScriptDataReader, \scriptdata).data;

//:
~dataSource.size;
~score = ~dataSource;

//:
(score: Pindex(~score, Pseries(0, -1, 30) + 1000),
play: { ~score[1].postln; ~dur = ~score[0] }) +> \test;
//:
var envir;
envir = currentEnvironment;
(score: Pindex(~score, Pseries(0, 1, 30) + 1000),
play: { ~score[1].interpretIn(envir); ~dur = ~score[0] }) +> \testasdf;
//:
~score[0][1].interpret;
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
//:i
ScriptDataReader.openDialog;
Library.at(ScriptDataReader, \scriptdata).play(repeats: 5);