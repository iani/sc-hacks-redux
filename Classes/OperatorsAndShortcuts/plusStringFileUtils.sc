+ String {
	fileMatch { | fileType = "scd" |
		^(this +/+ "*." ++ fileType).pathMatch
	}

	doIfExists { | yesAction, noAction |
		^File.doIfExists(this, yesAction, noAction)
	}

	// return true if the file corresponding to me as path
	// has a header equal to "//code".
	isCode {
		var isCode;
		File.use(this, "r", { | f |
		var h;
			h = f.getLine(1024); // .postln;
			if (h == "//code") {
				isCode = true;
			}{
				isCode = false;
			};
		});
		^isCode;
	}

	hasCode { // true if the snippet contains "\n[ 'code'"
		^this contains: "\n[ '/code'";
	}
	hasTimestamps {
		var h;
		File.use(this, "r", { | f | h = f.readAllString; });
		^h contains: "\n//:--[";
	}
}

+ File {
	*doIfExists { | path, yesAction, noAction |
		if (this exists: path) {
			^yesAction.(path)
		}{
			^noAction.(path);
		}
	}	
}