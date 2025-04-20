// 土 19  4 2025 02:30
// Enable private Environments for multiple users on OscGroups

User {
	classvar all;
	classvar <localId; // local id
	var <id; // this (possibly remote) user's id.
	var envir; // currentEnvironment
	var <stack; // environment stack;
	var envirs; // private envirs

	*initClass {
		StartUp add: {
			// Following only runs when evaluating code manually from the user
			// on a document.
			// It does not run when aString.interpret is called
			// from a preprogrammed function:
			this.getLocalUser;
			thisProcess.interpreter.preProcessor = { | code |
				this.forward(code, localId);
				code;
			}
		}
	}

	// *local { ^this.localUser }
	// *localUser { ^this.all[localId] }
	*local { ^this.all[localId] }
	// add { | id | ^this.class.at(id) } // enable syntax User[symbol]
	*named { | argId |
		argId ?? { argId = localId };
		^this.all.at(argId) ?? { this.new(argId); }; // stores in all!
	}

	*getLocalUser {
		localId = (this.readUserId ?? { this.makeDefaultId }).asSymbol;
		this.all[localId] = this.new(localId);
	}

	*readUserId {
		var path;
		path = this.makePathLocation;
		if (File.exists(path)) { ^File.readAllString(path)  }{ ^nil }
	}

	*makePathLocation { | argId |
		^Paths.makePathLocation("oscGroups_" ++ this.systemUserName);
	}

	*systemUserName { ^Platform.userHomeDir.fileName }

	*makeDefaultId {
		^(this.systemUserName ++ "_" ++ Date.getDate.stamp
			++ "_" ++ 1000.rand.asString).asSymbol;
	}

	*localId_ { | argId |
		argId = argId.asSymbol;
		// ! replace User instance in all from old id to new id.
		// remove old user instance if present:
		this.all[localId] = nil;
		// store new local user instance
		this.new(argId); // this also stores to all
		// set new localId;
		localId = argId;
		// save localId to file.
		// Paths.saveAtKey(this.idFromSystem, localId);
		this.writeUserId;
	}

	*writeUserId {
		var path;
		path = this.makePathLocation;
		File.use(path, "w", { | f | f.write(localId.asString)});
	}

	// send to oscgroups, marking the sender as localId
	*forward { | code | OscGroups.forward(code, localId); }

	*run { | code, argId |
		this.use({
			postf("========= user % runs: ========= \n\(\n\%\n\)\n", argId, code);
			code.asString.interpret.postln;
		}, argId ? localId);
	}

	*use { | func, argId |
		this.new(argId.asSymbol).use(func);
	}

	*new { | argId |
		var new;
		argId = (argId ? localId).asSymbol;
		new = this.all[argId];
		new ?? {
			new = this.newCopyArgs(argId, argId.envir, Stack(), ());
			all[argId] = new;
		};
		^new;
	}

	use { | func | envir use: func; }

	envir { | argId |
		var result;
		// postln("envir on" + this + "with arg" + argId);
		argId = argId ? id;
		result = this.envirs[argId];
		result ?? {
			result = Mediator(argId);
			envirs[argId] = result;
		};
		result[\user] = this;
		^result;
	}

	envirs { ^envirs ?? { envirs = () } }

	*all { ^all ?? { all = () } }

	// ========== push - pop ==========
	*push { | userName, envirName |
		userName ?? { userName = this.localId };
		this.new(userName.asSymbol).push(envirName.asSymbol);
	}

	*pop { | userName |
		this.new(userName.asSymbol).pop;
	}

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << id.asString;
		stream << ">" ;
	}
}