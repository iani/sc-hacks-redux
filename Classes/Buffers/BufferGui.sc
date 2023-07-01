/*  1 Jul 2023 11:50
Browse loaded buffers.
Open buffer gui on selected buffer.
*/

BufferGui {
	*gui {
		this.vlayout(
			Button().states_([["open"]])
			.action_({ | me |
				me.value.postln;
				Buffer.all[me.value].postln;
			}),
			ListView()
			.items_(Buffer.all)
			.enterKeyAction_({ | me |
				Buffer.all[me.value].postln;
				Buffer.all[me.value].buf.postln;
				Buffer.all[me.value].buf.gui;
			})
			.addNotifier(Buffer, \loaded, { | n |
				"buffer loaded msessage recdived by buffer gui".postln;
				n.listener.items = Buffer.all
			})
		);
		// Buffer.all.postln;
	}
}