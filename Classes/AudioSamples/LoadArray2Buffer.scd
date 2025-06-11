// 水  4  6 2025 12:07
// Playback of animation data on server.
// Each array of pose-data is a single frame.
// The number of channels in the frame is equal to the size of the array.
// So if a frame of data received from Rokoko MOCAP has 161 numbers,
// then the buffer containing an animation has 161 channels.
// To play back such a buffer into control buses, one must calculate
// the playback rate.  The target sampling rate for playback is
// 30 frame/second.  The calculation of the playback rate is:
//:
~audioSample = 1 / Server.default.sampleRate;
~audioSample * Server.default.sampleRate;
~controlSample = ~audioSample * Server.default.options.blockSize;
Server.default.sampleRate * ~audioSample;
1 / ~audioSample;
~audioSample.reciprocal;
1 / ~controlSample;
~controlSample.reciprocal;
~playbackRate = 1 / (~controlSample.reciprocal / 30);
//: if sampleRate is 48000 and blockSize is 64, then playback rate is 0.04.
~playbackRate = 0.04;
//:Measure the playback duration of a test sequence of 300 frames
//This should be 10 seconds
// Step 1: make the buffer;
a = Array.fill(30, { | i | sin(i * 2pi / 30) });
a = a.dup(10).flat;
a.size; // 300
b = Buffer.loadCollection(Server.default, a);
b.numFrames; // 300
//: Step 2: play the buffer
s = { PlayBuf.kr(1, b.bufnum, BufRateScale.kr(b.bufnum) * 0.04,
	loop: 0, doneAction: Done.freeSelf)
}.play;
s onStart: { ~started = Clock.seconds };
s onEnd: { ~ended = Clock.seconds; ~dur = Clock.seconds - ~started; };
//: Step 3: read the recorded duration. Should be 10 seconds
~dur; // ca 9.9626627080142
//:Redo the timing with preloaded SynthDef:
//:Step 1b: Load the synthdef
SynthDef(\animation, { | bufnum |
	var src;
	src = PlayBuf.kr(1, bufnum, BufRateScale.kr(bufnum) * 0.04,
		loop: 0, doneAction: Done.freeSelf);
	Out.kr(0, src)
}).add;
//:Step 2b: Time the playback
s = Synth(\animation, [bufnum: b.bufnum]);
s onStart: { ~started = Clock.seconds };
s onEnd: { ~ended = Clock.seconds; ~dur = Clock.seconds - ~started; };
//:Step 3b: duration?
~dur; // 9.9626605839876
// Step 1c: Try this with a buffer of 3000 frames
a = a.dup(10).flat;
a.size; // 3000
b = Buffer.loadCollection(Server.default, a);
//:Step 2c: duration:
~dur // 99.967628499988
// Conclusion: duration is consistently ca 0.033 sec less than
// calculated rate. This does not change when playing larger buffers.
// The playback rate of 0.04 is correct.
//: ========== For playback with Dbufrd.==========
//:MUST lace nested array to 1-dimensional array.
a = ({1.0.rand2} ! 200) ! (48000 * 5.0) ;
b = Buffer.loadCollection(Server.default, a.lace, 200);
//:b has 200 channels:
b.numChannels; // 200
b.numFrames;
b.dur;
//:write b to file
b.write("~/audiofiles/multichannelWrite200_250603.aiff".standardizePath);
//:
c = Buffer.read(Server.default,
	"~/audiofiles/multichannelWrite200_250603.aiff".standardizePath
);
//:
c; // 200 channels, 220500 frames
//:Try playing with sines to confirm that the signals are as required
a = [[1], [1, 1], [1, 1, 1], [1, 1, 1, 1]] collect: { | i |
	Array() addAll: Signal.sineFill(1000, 1.0/i);
};
//:
a.size;
a.first.size;
b = Buffer.loadCollection(Server.default, a.lace, a.first.size);
b.write("~/audiofiles/multichannelSines4Chan_250603.aiff".standardizePath);
//:view these
b.plot; // ???
a.plot; // makes sense
//:play b as control signal with Dbufrd
~bus = Bus.control(Server.default, 4);
//:
{
	var index, bufrd, sig;
	index = Dseries(0, 1, inf);
	bufrd = Dbufrd(b.bufnum, index, loop: 1);
	sig = Demand.kr(Impulse.kr(30), 0, bufrd);
	Out.kr(0, sig);
}.play;
//:
~bus;
b.plot;
c.plot;