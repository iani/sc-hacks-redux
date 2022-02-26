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
					// me.item.postln;
					// me.item.class.postln;
					if (currentEnvironment[me.item].isPlaying) {
						me.item.stop;
					} {
						Mediator.wrap({
							{ me.item.playBuf } +> me.item;
						}, \buffers)
					}
				});
			)
		}, \list)
	}
	*allFiles {
		^Buffers.all;
	}
}