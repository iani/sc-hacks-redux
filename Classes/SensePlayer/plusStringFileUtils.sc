+ String {
	fileMatch { | fileType = "scd" |
		^(this +/+ "*." ++ fileType).pathMatch
	}

	doIfExists { | yesAction, noAction |
		^File.doIfExists(this, yesAction, noAction)
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