/* 14 Jun 2023 01:03

*/

OscDataGui {
	classvar >fileListHistoryPath;
	classvar fileListHistory;
	classvar <selectedList, <selectedPath;

	*gui {
		fileListHistory ?? { this.readFileListHistory; };
		this.vlayout(
			Button().states_([["Add Filelist"]])
			.action_({ this.addListFromUser }),
			HLayout(
				ListView()
				.addNotifier(fileListHistory, \history, { | n |
					// "updating filelist".postln;
					n.listener.items = fileListHistory.lists collect: _.asString;
					selectedList = fileListHistory.lists.first;
					this.changed(\selectedList);
				})
				.action_({ | me |
					selectedList = fileListHistory.lists[me.value];
					fileListHistory.changed(\fileList, me.value);
					this.changed(\selectedList);
				})
				.enterKeyAction_({ | me |
					selectedList = fileListHistory.lists[me.value];
					fileListHistory.changed(\fileList, me.value);
					this.changed(\selectedList);
				}),
				ListView()
				.addNotifier(this, \selectedList, { | n |
					// selectedList.postln;
					// selectedList.class.postln;
					// selectedList.
					n.listener.items = selectedList.paths collect: _.basename;
					selectedPath = selectedList.paths.first;
				})
			)
		);
		{ fileListHistory.changed(\history) }.defer(0.1);
	}

	*addListFromUser {
		this.fileListHistory.addListFromUser;
	}

	*fileListHistory {
		fileListHistory ?? { this.readFileListHistory };
		^fileListHistory;
	}

	*readFileListHistory {
		fileListHistory = Object.readArchive(this.fileListHistoryPath);
		fileListHistory ?? {
			fileListHistory = FileListHistory(this.fileListHistoryPath);
			this.writeFileListHistory;
		}
	}

	*fileListHistoryPath {
		fileListHistoryPath ?? {
			fileListHistoryPath = Platform.userAppSupportDir +/+ "OscDataFileLists.scd";
		};
		^fileListHistoryPath;
	}

	*writeFileListHistory {
		fileListHistory.writeArchive(this.fileListHistoryPath);
	}

	// not yet used.
	*fileListHistoryPathDialog {
		FileDialog({ | path |
			fileListHistoryPath = path;
			this.readFileListHistory;
		})
	}

}