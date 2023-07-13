/*  1 Jul 2023 11:50

Browse loaded buffers.
Open buffer gui on selected buffer.


Only a prototype.

See SoundFileGui, SoundFileSettings.

*/

BufferGui {
	*new { this.gui }
	*gui {
		var selectedBuffer;
		this.br_.vlayout(
			Button().states_([["open"]])
			.action_({ | me |
				SoundBufferGui.gui((selectedBuffer ?? { Buffer.all.first }).buf);
			}),
			ListView()
			.items_(Buffer.all)
			.action_({ | me |
				selectedBuffer = Buffer.all[me.value]
			})
			.enterKeyAction_({ | me |
				SoundBufferGui.gui(Buffer.all[me.value].buf);
			})
			.addNotifier(Buffer, \loaded, { | n |
				n.listener.items = Buffer.all
			})
		);
	}
}