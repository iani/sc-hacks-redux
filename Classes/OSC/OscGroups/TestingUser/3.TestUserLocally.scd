var recvPort, sendPort, sendAddress, oscMessage = \test, senderRoutine;
Platform.ideName.asSymbol.postln.switch(
	\scqt, {
		"This is scide (probably on a mac)".postln;
		#recvPort, sendPort = [57125, 57126];
		postln("Setting recvport to" + recvPort + "and sendport to" + sendPort);
		User.oscSendPort = sendPort;
		User.oscRecvPort = recvPort;
		OscMonitor.gui;
		{
			"Starting Session for scel on ScIDE".postln;
			User.enable("the_sun_rising_ScIDE", nil, \iani, \geodia, \vasilis, \kosmas, \peny);
			 // change local user, but do not save:
			//	{
				User.localId_(\geodia, false);
			// }.defer(1);
			// new user name to file.
		}.defer(2)
	},
	\scel, {
		"This is scel (on EMACS)".postln;
		#recvPort, sendPort = [57126, 57125];
		User.oscSendPort = sendPort;
		User.oscRecvPort = recvPort;
		postln("Setting recvport to" + recvPort + "and sendport to" + sendPort);
		{
			"Starting Session for scel on EMACS".postln;
			User.enable("the_sun_rising_EMACS", nil, \iani, \geodia, \vasilis, \kosmas, \peny);

		}.defer(2)
	},
	{ "I did not recognize the ide".postln; }
);

// postln("Setting recvPort to" + recvPort + "and sendPort to" + sendPort);
// thisProcess openUDPPort: recvPort;
// User.oscSendPort = sendPort;
// sendAddress = NetAddr("127.0.0.1", sendPort);
// senderRoutine = {
// 	var ide;
// 	ide = Platform.ideName;
// 	inf do: { | i |
// 		// postln("Sending" + oscMessage + "with arg" + i + "to" + sendAddress);
// 		sendAddress.sendMsg(oscMessage, i);
// 		sendAddress.sendMsg(\c, format("% + %",
// 			Platform.ideName.asCompileString, "1000.rand;"
// 		), User.localId);
// 		1.wait;
// 	}
// }.fork;
// senderRoutine.addNotifier(\senderRoutine, \stop, { | n |
// 	n.listener.stop;
// 	"Stopped osc sending test routine.".postln;
// });