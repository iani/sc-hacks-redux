/*  1 Jul 2023 11:50

Browse loaded buffers.
Open buffer gui on selected buffer.


Only a prototype.

See SoundFileGui, SoundFileSettings.

*/

BufferGui {
	classvar selectedBuffer;
	*new { this.gui }
	*gui {
		// var selectedBuffer;
		this.br_(200, 400).vlayout(
			Button().states_([["open"]])
			.action_({ | me |
				// SoundBufferGui((selectedBuffer ?? { Buffer.all.first }).buf);
				this.openSelected;
			}),
			ListView()
			.items_(Buffer.all)
			.action_({ | me |
				selectedBuffer = Buffer.all[me.value]
			})
			.enterKeyAction_({ | me |
				// SoundBufferGui.gui(Buffer.all[me.value].buf);
				this.openSelected;
			})
			.addNotifier(Buffer, \loaded, { | n |
				n.listener.items = Buffer.all
			})
		);
	}

	*openSelected {
		SoundBufferGui((selectedBuffer ?? { Buffer.all.first }).buf).gui;
	}
}