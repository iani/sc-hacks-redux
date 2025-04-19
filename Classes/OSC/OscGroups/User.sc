// 土 19  4 2025 02:30
// Enable private Environments for multiple users on OscGroups

User {
	classvar all;
	classvar localId; // local id
	var id; // this user's id
	var <stack; // environment stack;
	var envir; // currentEnvironment

	*run { | code, argId |
		this.use({ code.interpret.postln; }, argId ? localId);
	}

	*use { | func, argId |
		this.new(argId.asSymbol).use(func);
	}

	*new { | argId |
		var new;
		argId = (argId ? localId).asSymbol;
		new = this.all[argId];
		new ?? {
			new = this.newCopyArgs(argId, argId.envir);
			all[argId] = new;
		};
		^new;
	}

	*all { ^all ?? { all = () } }

	use { | func | envir use: func; }

	envir { ^envir ?? { envir = Environment() } }

	*localId { ^localId ?? { localId = this.getIdFromFile } }
	*localId_ { | argId |
		localId = argId.asSymbol;
		Paths.saveAtKey(this.idPathKey, localId);
		postln("Saved userId" + localId + "to file"
			+ this.idPath.fileName);
	}

	*getIdFromFile {
		^localId = Paths.getPathFromKey(this.idPathKey, this.makeId);
	}

	*idPathKey { ^("oscGroups_" ++ this.systemUserName).asSymbol; }

	*userIdPath {
		^Paths.makePathLocation(Paths.baseDirectory +/+ "oscGroups_" ++ this.userName)
	}

	*makeId {
		^(this.systemUserName ++ "_" ++ Date.getDate.stamp
			++ "_" ++ 1000.rand.asString).asSymbol;
	}
	*systemUserName { ^Platform.userHomeDir.fileName }

	*readUserId {

	}

	*writeUserId {

	}
}