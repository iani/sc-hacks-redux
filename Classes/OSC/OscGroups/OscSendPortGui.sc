// 月  5  5 2025 07:44
// Set oscSendPort of User via gui.
// By setting the port to an invalid number you can mute
// User to perform internal operations without affecting other
// users connected to OscGroups.
/*
OscSendPortGui.gui;
*/
OscSendPortGui {
	classvar <localport, <otherport;
	classvar <defaultSendPort, allports;

	*allports { // lazy is safest
		defaultSendPort = 22244;
		localport = NetAddr.localAddr.port;
		otherport = localport + 1;
		^allports = [defaultSendPort, otherport, localport];
	}
	*gui {
		var window, text;
		window = this.vlayout(
			ListView().items_(this.allports)
			.action_({ | me |
				if (me.item === localport) {
					postln(
						"I refuse to set sendport to localport" + localport
					);
				}{
					User.oscSendPort = me.item;
					postln("I set User oscSendPort to" + me.item);
					me.item.postln;
					me.item.class.postln;
					text.string = me.item.asString
				};
			}),
			HLayout(
				StaticText().string_("User send port:"),
				text = TextField();
			)
		);
		{
			text.string = defaultSendPort.asString;
			window.bounds = Rect(
				0, Window.screenBounds.height - 100,
				150, 100
			);
		} defer: 0.1;
	}
}