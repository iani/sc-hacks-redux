// 13 Aug 2020 09:07
//:trace messages from server
OSCFunc.trace(true, true);
//: test if booting happens reliably:
Queue add: { Synth(\default) };
//:Compare setting arguments of synth with and without sync
//:Without sync:
// Synth.new works without sync, both for set and for map.
b = Bus.control;
b.set(3000);
a = Synth(\default, [freq: 1000, amp: 0.5]);
a.map(\freq, b.index);
//:
// a.set(\freq, 3000)
//: here we get a problem: /n_set Node ... not found
c = { SinOsc.ar(\freq.kr(1000), 0, 0.3).dup }.play;
c.set(\freq, 3000);
//:Try with sync
Queue add: { a = { SinOsc.ar(\freq.kr(1000), 0, 0.3).dup }.play; };
{ a.set(\freq, 3000) }.sync; // this is now OK
//:alternative of above using sync instead of add:
{ a = { SinOsc.ar(\freq.kr(1000), 0, 0.3).dup }.play; }.sync;
{ a.set(\freq, 3000) }.sync; // this is now OK
//:variation using map instead of set
b = Bus.control;
{ { Out.kr(b.index, LFNoise0.kr(15).range(1000, 2000)) }.play }.sync;
{ a = { SinOsc.ar(\freq.kr(1000), 0, 0.3).dup }.play; }.sync;
{ a.map(\freq, b.index) }.sync; // OK!
// a.set(\freq, 3000); // this does not work.!!!
//:================================================================
//:Test creating multiple synths synced.
{ | i | { Synth(\default, [freq: i * 100 + 1000, amp: 0.8]) }.sync; } ! 1;
//:
{ | i | { Synth(\default, [freq: i * 100 + 1000, amp: 0.8]) }.sync; } ! 2;
//:
{ | i | { Synth(\default, [freq: i * 100 + 1000, amp: 0.8]) }.sync; } ! 5;	
//: test if adding an action without server async will not block
Queue add: { "just testing".postln; };
//: test adding an action several times.
3 do: { | i |
	Queue add: { postf("Testing. Iteration count is: %\n", i + 1) };
}
//:
{ postf("Testing. Iteration count is: %\n", 1)}.sync;

//: test adding an action several times, large number of iterations.
300 do: { | i |
	Queue add: { postf("Testing. Iteration count is: %\n", i + 1) };
}
//:Buffer.readSync test 1
var path;
path =
"/home/iani/.local/share/SuperCollider/downloaded-quarks/Dirt-Samples/808/*.WAV"
.pathMatch.first;
~buffer = Buffer.readSync(Server.default, path);
// postf("buffer before sync: %\n", ~buffer);
// { postf("BUFFER AFTER SYNC: %\n", ~buffer) }.sync;
{ ~buffer.play }.sync;

//:+More buffer tests: load and play several buffers
var paths;
paths =
"/home/iani/.local/share/SuperCollider/downloaded-quarks/Dirt-Samples/808/*.WAV"
.pathMatch;
paths do: { | path |
	var buffer;
	buffer = Buffer.readSync(Server.default, path);
	{ buffer.play }.sync;
	[0.25, 0.5].choose.wait; // optional. also tested without wait.
};
//:More buffer tests: load+collect several buffers, then play them.
var paths, buffers;
paths =
"/home/iani/.local/share/SuperCollider/downloaded-quarks/Dirt-Samples/808/*.WAV"
.pathMatch;
buffers = paths collect: { | path |
	var buffer;
	buffer = Buffer.readSync(Server.default, path);
};
{
	{
	loop {
		buffers.choose.play;
		[0.25 / 2, 0.25, 0.25 * 2].choose.wait;
	};
	}.fork;
}.sync;
//:Even More buffer tests: load and play all .wav (sic) Dirt-Sample folders
// NOTE; Quit default buffer before running this, to permit large number of buffers
var folders, buffers;
Server.default.options.numBuffers = 2048; // override default max number of buffers
folders =
"/home/iani/.local/share/SuperCollider/downloaded-quarks/Dirt-Samples/*"
.pathMatch;
folders do: { | folder |
	var paths, buffers;
	// folder.postln;
	paths = (folder +/+ "*.wav").pathMatch;
	buffers = paths collect: { | path |
		var buffer;
		buffer = Buffer.readSync(Server.default, path).postln;
	};
	{
		{
			loop {
				buffers.choose.play;
				([0.25 / 2, 0.25, 0.25 * 2] * 24).choose.wait;
			};
		}.fork;
	}.sync;
};
//:Even More buffer tests: load and check all .wav (sic) Dirt-Sample folders
// DEBUG: SEE IF ANY BUFFERS HAVE UNINITIALIZED INFO
// NOTE; Quit default buffer before running this, to permit large number of buffers
var folders, buffers;
Server.default.options.numBuffers = 2048; // override default max number of buffers
folders =
"/home/iani/.local/share/SuperCollider/downloaded-quarks/Dirt-Samples/*"
.pathMatch;
~count = 0;
folders do: { | folder, i |
	var paths, buffers;
	// folder.postln;
	paths = (folder +/+ "*.wav").pathMatch;
	buffers = paths collect: { | path |
		var buffer;
		~count = ~count + 1;
		//	buffer = Buffer.readSync(Server.default, path);
			buffer = Buffer.read(Server.default, path);
		~allbuffers = ~allbuffers add: buffer;
	};
	postf("buffer count is now: %\n", ~allbuffers.size);
	//	"waiting for 10 seconds".postln;
	// 10.wait;
	postf("loading folder %\n", i);
	/*
	{
		buffers do: _.postln;
	}.sync;
	*/
};
//:
~allbuffers.size;

//:
Server.default.sendMsg('/sync', 1);
Que.sendSync;
//:
Server.killAll;
//:
~buffer;
//:
~count;
//:
~allbuffers.last;
~allbuffers.first;
//:
~allbuffers detect: { | b | b.numFrames.isNil };
//:
~buffer.play;
//:
var path;
path =
"/home/iani/.local/share/SuperCollider/downloaded-quarks/Dirt-Samples/808/*.WAV"
.pathMatch.first;
~buffer2 = Buffer.readSync(Server.default, path);
~buffer2 // .play;
//:
var path;
path =
"/home/iani/.local/share/SuperCollider/downloaded-quarks/Dirt-Samples/808/*.WAV"
.pathMatch.first;
~buffer2 = Buffer.readSync(Server.default, path);
{ ~buffer2.play }.sync;
//:
~buffer2.play;
//:
Server.default.addr.sendMsg('b_query', 0);