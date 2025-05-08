// 木  8  5 2025 23:59 poll busses with a synth
// ============ prelim: design polling synthdef ==========
//:
// =========== The interface ========
// The user can write to these busses with any kr Synth
~rokoko = \session1.rdata; // load some data in advance
//:start a playback loop
~rokoko.loopSegment; // loop entire data
~rokoko.addPostData; // post
~rokoko.removePostData; // stop posting
//:
~rokoko.poll; // start polling
~rokoko.addSumSend;
~rokoko.removeSumSend;
~rokoko.buses.index;
//:
{
	Out.kr(
		~rokoko.buses.index,
		LFSaw.kr(0.1).range(0.0, 0.1)
	)
}.play;

~rokoko.entry;