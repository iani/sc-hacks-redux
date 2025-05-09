// 金  9  5 2025 02:53 test osc sending and receiving
// load session data
~rokoko = \session1.rdata; // load some data in advance
//:Test listening and sending
~rokoko.enableListeningTest;
~rokoko.sendTest;

//:start a playback loop
~rokoko.loopSegment; // loop playback over entire data
//:
~rokoko.sendPlaybackLocally;
//:
~rokoko.disableListening;
//:Start polling
~rokoko.poll;
//:test adding values to x of rightHand, and sending
~rokoko.addSumSend;
//:Start a synth writing to bus 0, used by addSumSend
~rokoko.postRokokoOsc;
//:Start a synth writing to bus 0, used by addSumSend
~rokoko.muteRokokoOsc;
//:
OscMonitor.gui
//:
~rokoko.resendRokokoOsc;
//:
~rokoko.muteRokokoOsc;
//:
~rokoko.findIndex;
~rokoko.entry;
~rokoko.data.class;
//:
~mouse = {
	Out.kr(0, MouseX.kr(-1.0, 1.0));
	Out.kr(1, MouseX.kr(-1.0, 1.0));
}.play;
//:
~mouse.free;
//:
Bus(\control, 0, 1, Server.default).getn(1, { | v |
				v.postln;
			});
