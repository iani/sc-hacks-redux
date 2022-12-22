/* 17 Dec 2022 14:33
Add subfolders of buffers contained in ~/sc-audiofiles/
to be loaded when the buffer is booted.
*/

+ Buffer {
	*require { | ... directories |
		directories do: this.require1(_);
	}

	*require1 { | directory |
		var required;
		required = this.required;
		directory = directory.asSymbol;
		if (required includes: directory) {
			postln("The directory" + directory + "is already required");
		}{
			this.loadRequiredIfExists(required, directory);
		}
	}

	*required { ^Registry(this, \required, { Set() }); }

	*loadRequiredIfExists { | required, directory |
		var thePath;
		thePath = this.makeLoadPathname(required);
		if (File.exist(thePath.fullPath)) {
			required add: directory;
			this.loadRequired(directory);
		}{
			postln("Directory not found:" + thePath.fullPath);
		}
	}

	*loadRequired { | required |
		Server.default.waitForBoot({
			Project.loadAudioFiles(this.makeLoadPathname(required));
		})
	}

	*makeLoadPathname { | required |
		^PathName("~/sc-audiofiles") +/+ required.asString;
	}

	*clearRequired {
		// TODO: free buffers???
		Registry.remove(this, \required);
	}
}