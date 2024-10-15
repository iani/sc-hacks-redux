/* 11 Jul 2023 04:25
Generate code template for playing SoundBufferGui, based on current settings.

Open it as a document.
*/

BufCode {
	classvar <templates; // templates of parameters, read from documents
	classvar homefolder; // where the generated documents should be stored.
	// in the same folder as the classes concerned. Loaded lazily (when needed).
	var sbg; // the sound buffer gui that made me.
	var name, path, code;

	*new { | soundBufferGui |
		^this.newCopyArgs(soundBufferGui ?? { SoundBufferGui.default }).init;
	}

	init {
		// first find out where the home folder is.
		this.class.withFolder({ | f |
			homefolder = f;
			postln("BufCode saves scripts in" + homefolder);
			this.makeScript;
		})
	}

	// alternative init: init PlayBufTemplates to get necessary path + templates
	// Under development
	altInit {
		PlayBufTemplates.withFolder({ | f |
			PlayBufTemplates.init;
			this.makeScript;
		})
	}

	*defaultPath {
		^PathName("~/sc-projects" +/+ "BufferPlayers/").fullPath;
	}

	makeScript {
		postln("making script for" + sbg.name);
		path = this.makePath;
		postln("script path is" + path);
		this.makeCode;
		this.writeCode;
		this.openDocument;
	}

	makePath {
		this.makeName;
		^PathName(homefolder +/+ name).fullPath;
	}

	makeName {
		name = format("%%.scd", sbg.name, Date.getDate.stamp);
	}

	makeCode {
		// code = "";
		// code = "" ++ sbg.name.asCompileString;
		// code = code ++ "@@"
		code = format("// - % -\n//:\n% @@.%\n(\n", name, sbg.name.asCompileString, sbg.playfunc);
	}
	writeCode {
		File.use(path, "w", { | f | f.write(code) });
	}

	openDocument {
		{
			Document.open(path);
			// "documenet opened".postln;
		}.defer(0.1)
	}
}