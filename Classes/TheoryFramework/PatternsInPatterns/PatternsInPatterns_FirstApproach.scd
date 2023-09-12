/* 12 Sep 2023 13:49
Play a pattern stored in one of the values of the stream of the already playing pattern.
This can be called a sub-pattern. It could be useful for arpeggios, ornaments etc.

Here we test how to customize the play function to achieve this.
*/
//: This is the way to go.
//: Approach from https://doc.sccode.org/Overviews/Event_types.html

//: =============== WORKING EXAMPLE ==============
//:================= subpatterns ===============
Event.addEventType(\trill, { (degree: (~degree + [0, 1]).pseq(3), dur: ~dur / 6).splay; });
Event.addEventType(\arpeggio, { (degree: (~degree + [0, 2, 4, 7]).pseq(1), dur: ~dur / 6).splay; });
Event.addEventType(\darpeggio, { (degree: (~degree - [0, 2, 4, 7]).pseq(1), dur: ~dur / 6).splay; });
(type: [\trill, \arpeggio, \darpeggio].pseq, degree: (1..10).pseq, dur: [0.5, 3, 2, 1].pseq) +> \test;
//: =============== EARLIER EXPLORATIONS ==============
//:===== basics ======
a = (play: { ~word.scramble.postln }, word: "hello word");
a.play;
//:
a = (type: \note, freq: [1310, 1321]); // choosing a play function by specifying type
a.play;

Event.addEventType(\test, { "Your word is: ".post; ~word.scramble.postln });
(type: \test, word: "annahme").play;
//:===== Pbind ====
Pbind(\type, [\test, \play, \play].pseq, \degree, (1..10).pseq, \word, ["annahme", "eins"].pseq).play;
//:=== EventStream ======
a = (type: [\test, \play, \play].pseq, degree: (1..10).pseq, word: ["annahme", "eins"].pseq).splay;
//:test status of Eventstream.
a.isPlaying;
//:
a = (type: [\test, \play, \play].pseq(1), degree: (1..10).pseq(1), word: ["annahme", "eins"].pseq).splay;
//:
a.isPlaying;
//:================= subpatterns ===============
Event.addEventType(\trill, { (degree: (~degree + [0, 1]).pseq(3), dur: ~dur / 6).splay; });
(type: \trill, degree: (1..10).pseq, dur: [0.1, 0.5, 3].pseq) +> \test;
//:
//:======== THIS IS NOT THE WAY TO GO: ========
//:Test default play function of event
//This does not work: Synths are not freed. Why?
p = { // yeah. it's that bad...

	var server, latency, group, addAction;
	var instrumentName, synthLib, desc, msgFunc;
	var msgs, cvs;
	var bndl, ids;
	~finish.value;
	~server = server = ~server ?? { Server.default };
	~sustain = ~sustain.value;
	group = ~group.asControlInput;
	addAction = Node.actionNumberFor(~addAction);
	synthLib = ~synthLib ?? { SynthDescLib.global };
	instrumentName = ~instrument.asDefName;
	desc = synthLib.synthDescs[instrumentName];
	if (desc.notNil) {
		msgFunc = desc.msgFunc;
		~hasGate = desc.hasGate;
	} {
		msgFunc = ~defaultMsgFunc;
	};

	msgs = msgFunc.valueEnvir.flop;
	ids = Event.checkIDs(~id, server);
	if (ids.isNil) { ids = msgs.collect { server.nextNodeID } };
	bndl = ids.collect { |id, i|
		[9 /* \s_new */, instrumentName, id, addAction, group]
		++ msgs[i]
	};

	if ((addAction == 0) || (addAction == 3)) {
		bndl = bndl.reverse;
	};
	bndl = bndl.asOSCArgBundle;
	if (~lag !=0) {
		server.sendBundle(server.latency ? 0 + ~lag, *bndl);
	} {
		server.sendBundle(server.latency, *bndl);
	};
	~id = ids;
	~isPlaying = true;
	~isRunning = true;
	"Debugging custom play functions".postln;
	postln("prestored current environment is:" + ~currentEnvironment);
	postln("closure current environment is:" + currentEnvironment);
	NodeWatcher.register(~currentEnvironment);
};
// none of the following work.  Synths are not freed.
// (play: p, degree: (1..4).pseq) +> \test;
Pbind(\play, p, \degree, (1..4).pseq).play;
//:
Pbind(\degree, (1..4).pseq).play;