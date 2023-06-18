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
			ListView()
			.addNotifier(fileListHistory, \history, { | n |
				"updating filelist".postln;
				n.listener.items = fileListHistory.lists collect: _.asString;
			})
			.action_({ | me |
				selectedList = me.items[me.value];
				fileListHistory.changed(\fileList, me.value);
			})
			.enterKeyAction_(({ | me |
				selectedList = me.items[me.value];
				fileListHistory.changed(\fileList, me.value);
			}))
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