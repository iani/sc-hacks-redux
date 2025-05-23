//:
// hydraBridge
// doOnVerse
// oscForwarder
// ndefAnalysis
// ------- ~hydra = LocalUserAction(...)
(
//:
~hydraBridge = 	{~hydraBridgeOSCAddr = NetAddr("127.0.0.1", 12000)};
//:
~doOnVerse = {Tsr.doOnVerse({ | index, verse, incipit |
    var fileNumberString;
    // Add 1 to index to get the range 1-30
    // Format as a decimal integer (%d) with 2 digits, zero-padded (%02d)
	"registered!!!".postln;
    fileNumberString = (100 + index + 1).asString[1..];
    ("Formatted index: " ++ fileNumberString).postln; // For testing, remove in final code

    ~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/" ++ fileNumberString ++ ".mp4");
}, \kosmas);
};

//:
~oscForwarder = {(if (OSCdef.all[\hydraCCForwarder].isNil) { // Check if it's already defined
        OSCdef(\hydraCCForwarder, {
            arg msg, time, addr, recvPort;
            // msg[0] is the cmdName ('/hydra/cc')
            // msg[1] is the node ID of the Synth that sent it
            // msg[2] is the replyID (100 in your case)
            // msg[3] is your first value (0 for cc_index)
            // msg[4] is your second value (ampValue)

		// "--- OSCdef \hydraCCForwarder TRIGGERED! ---".postln; // <--- KEY DEBUG
		// "Received message: ".post; msg.postln;
		// "From address: ".post; addr.postln;

            if (msg[0] == '/hydra/cc' && msg.size >= 5) {
                var cc_index = msg[3];
                var value = msg[4];
			// "Condition met. Extracted cc_index: ".post; cc_index.postln;
			// "Extracted value: ".post; value.postln;
			// "Attempting to forward to Node bridge: /hydra/cc ".post; [cc_index, value].postln;
                ~hydraBridgeOSCAddr.sendMsg('/hydra/cc', cc_index, value);
			// "Forwarded.".postln;
            } {
			// "Condition NOT met or msg size too small.".postln;
            }
        }, '/hydra/cc', nil); // Listen for '/hydra/cc' messages coming FROM the SC server
	// "OSCdef \hydraCCForwarder defined and active.".postln;
    } {
	// "OSCdef \hydraCCForwarder was already defined.".postln;
    };
)};
//:
~ndefAnalysis = { Ndef(\analysis, {
	var in = InFeedback.ar(0, 2);
	var amp = in.mdAmp().mean;
	var pitch = in.mdPitch().mean;
	var flat = in.mdFlat().mean;
	var cent=in.mdCent().mean;
	var crest=in.mdCrest().mean;
	var slope=in.mdSlope().mean;
	SendReply.kr(Impulse.kr(10), '/hydra/cc', [0, amp], replyID: 100);
	SendReply.kr(Impulse.kr(10), '/hydra/cc', [1, pitch], replyID: 101);
	SendReply.kr(Impulse.kr(10), '/hydra/cc', [2, flat], replyID: 102);
	SendReply.kr(Impulse.kr(10), '/hydra/cc', [3, cent], replyID: 103);
	SendReply.kr(Impulse.kr(10), '/hydra/cc', [4, crest], replyID: 104);
	SendReply.kr(Impulse.kr(10), '/hydra/cc', [5, slope], replyID: 105);
	Silent.ar;
	}).play}
)
//:
(
~hydra = LocalUserAction(\kosmas, {
	~hydraBridge.value;
	~doOnVerse.value;
	~oscForwarder.value;
	~ndefAnalysis.value;
});
)
//:
~hydra.run;
