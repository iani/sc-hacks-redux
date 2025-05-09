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
