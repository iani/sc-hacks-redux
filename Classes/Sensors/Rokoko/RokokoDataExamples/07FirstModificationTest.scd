//金  9  5 2025 02:00 first test modifying and sending
// playback data
// load session data
~rokoko = \session1.rdata; // load some data in advance
//:start a playback loop
~rokoko.loopSegment; // loop playback over entire data
//:Start polling
~rokoko.poll;
//:test adding values to x of rightHand, and sending
~rokoko.addSumSend;
//:Start a synth writing to bus 0, used by addSumSend
~mod = {
	Out.kr(
		~rokoko.buses.index,
		LFSaw.kr(0.1).range(0.0, 0.1)
	)
}.play;
//:stop modifying
~mod.free;
//:set bus index 0 back to 0
Bus(\control, ~rokoko.buses.index, 1, Server.default).set(0);
