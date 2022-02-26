/* 26 Feb 2022 12:04

*/

AudioFileGui {

	*list {
		this.window({ | w |
			w.layout = VLayout(
				StaticText().string = "Buffers loaded:",
				ListView()
				.items_(Buffer.all)
				.enterKeyAction_({ | me |
					me.item.postln;
					me.item.class.postln;
				});
			)
		}, \list)
	}
	*allFiles {
		^Buffers.all;
	}
}