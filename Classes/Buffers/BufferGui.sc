/*  1 Jul 2023 11:50

Browse loaded buffers.
Open buffer gui on selected buffer.


Only a prototype.

See SoundFileGui, SoundFileSettings.

*/

BufferGui {
	*new { this.gui }
	*gui {
		this.vlayout(
			Button().states_([["open"]])
			.action_({ | me |
				// me.value.postln;
				// Buffer.all[me.value].postln;
				Buffer.all[me.value].buf.gui;
			}),
			ListView()
			.items_(Buffer.all)
			.enterKeyAction_({ | me |
				// Buffer.all[me.value].postln;
				// Buffer.all[me.value].buf.postln;
				SoundBufferGui.gui(Buffer.all[me.value].buf);
			})
			.addNotifier(Buffer, \loaded, { | n |
				// "buffer loaded msessage recdived by buffer gui".postln;
				n.listener.items = Buffer.all
			})
		);
		// Buffer.all.postln;
	}
}