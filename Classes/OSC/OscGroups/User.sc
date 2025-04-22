// 土 19  4 2025 02:30
// Enable private Environments for multiple users on OscGroups

User {
	classvar all;
	classvar <localId; // local id
	classvar <currentEvaluator; // user who sent message to evaluate
	classvar <localUser;
	classvar <enabled = false;
	classvar <sessionName;
	classvar <sessionPath;
	var <id; // this (possibly remote) user's id.
	var envir; // currentEnvironment of this user
	var envirs; // user evaluates code only within one of these private envirs
	var document, <file;
	var <stack;
	var <isActive = false;

	*enable { | argSessionName = "session" |
		if (enabled) { ^"User class is already enabled.".postln; };
		this.doEnable(argSessionName);
	}

	*doEnable { | argSessionName |
		var currentEnvir;
		localId = (this.readUserId ?? { this.makeDefaultId }).asSymbol;
		argSessionName ?? { sessionName = argSessionName };
		this.makeSessionFolder(argSessionName);
		localUser = this.new(localId);
		localUser.activate; // !!!!!!!!!!!!!!!!!!!!!!!!!!!!
		this.all[localId] = localUser;
		currentEvaluator = localId;
		currentEnvir = localUser.envir;
		localUser.stack push: currentEnvir;
		currentEnvir.push;
		this.push(localId, localId);
		thisProcess.interpreter.preProcessor = { | code |
			localUser.code2doc(code);
			this.forward(code, localId);
			code;
		};
		enabled = true;
		OscGroups.enable;
		"Enabled User class".postln;
	}

	*makeSessionFolder { | argSessionName |
		sessionPath = this.makeSessionFolderPath(argSessionName ? sessionName);
		if (File.exists(sessionPath).not) {
			postln("Making session folder" + sessionPath);
			File.mkdir(sessionPath);
		};
	}

	*makeSessionFolderPath { | argSessionName = "session" |
		^Platform.userAppSupportDir +/+ "Sessions" +/+
		(Date.localtime.stamp ++ argSessionName);
	}

	activate {
		this.makeDocumentAndFile;
		isActive = true;
	}

	makeDocumentAndFile { // make document and file for recording session code.
		this.makeFile;     // ALWAYS make new file for recording user code
		this.document; // create document for this user only if not already existent
		// Both document and file record the same code.
		// I use a file because I could not find how to save
		// code from a ScelDocument (EMACS IDE) on sclang shutdown
	}

	makeFile {
		if (file.notNil) { ^file };
		^file = File(this.filePath, "w").write(this.documentHeader);
	}

	filePath { ^sessionPath +/+ id.asString ++ ".scd"; }

	documentHeader {
		^format("//Document for % created at %\n", id, Date.getDate.stamp);
	}

	document { ^document ?? { document = this.getDocument; }; }

	getDocument {
		var documents;
		documents = Document.allDocuments;
		document = documents detect: { | d | d.name.asSymbol === id };
		document ?? {
			postln("Making document for User" + id);
			document = Document.new(id.asString);
			document.string_(this.documentHeader);
		};
		^document;
	}


	*showDocument { | argId | this.new(argId ? localId).showDocument }
	showDocument { this.document.front }

	*initClass { // Close all session files on shutdown;
		ShutDown add: { this.disable };
	}

	*disable {
		if (enabled.not) { ^"User class is not active.".postln; };
		this.doDisable;
	}

	*doDisable {
		thisProcess.interpreter.preProcessor = nil;
		Environment().push;
		enabled = false;
		OscGroups.disable;
		this.saveSession;
		"Disabled User class".postln;
	}

	*saveSession { // save all user documents into session folder
		if (sessionPath.isNil) {
			"No session has been created. Returning without saving.".postln;
			^"Run User.enable to create a session".postln;
		};
		postln("I will save all user documents to folder:" + sessionPath.fileName);
		this.all do: _.saveSessionFile;
	}

	saveSessionFile {
		file !? {
			postln("Saving session file for" + id);
			file.close;
		}
	}

	saveDocumentBuggy {
		var foundDoc;
		foundDoc = this.findDocument;
		foundDoc ?? { ^nil }; // No document to save. Exit silently
		postln("Saving" + id + "on:\n" ++ this.documentPath);
		File.use(this.documentPath, "w", { | f |
			f.write(foundDoc.currentString);
			// f.write(foundDoc.text);
		});
		postln("Saved document for User:" + this);
	}

	documentText {
		var foundDoc;
		foundDoc = this.findDocument;
		foundDoc ?? { ^nil }; // No document to save. Exit silently
		foundDoc.text.postln;
	}

	saveAndCloseDocument {
		this.saveDocument;
	}

	findDocument { // find document if it exists in user or in IDE
		// used for writing documents to disk at end of session.
		document ?? { ^document };
		^Document.allDocuments detect: { | d | d.name.asSymbol === id };
	}

	*activate { | ... argUsers |
		if (argUsers.size == 0) { argUsers = this.allUserKeys; };
		argUsers do: { | u | this.new(u).activate };
	}

	*deactivate { | ... argUsers |
		if (argUsers.size == 0) { argUsers = this.allUserKeys; };
		argUsers do: { | u | this.new(u).deActivate };
	}

	deActivate {

		isActive = false;
	}

	*activeUsers {
		^this.all.select({|u| u.isActive}).collect(_.id).asArray.sort;
	}

	*inactiveUsers {
		^this.all.reject({|u| u.isActive}).collect(_.id).asArray.sort;
	}


	*allUserKeys { ^this.all.keys.asArray.sort }

	enable {
		this.document; // create a document for user to rearrange aforehand.
		enabled = true;
	}
	// *initClass {}

	*new { | argId |
		var new;
		argId = (argId ? localId).asSymbol;
		new = this.all[argId];
		if (new.isNil) {
			new = this.newCopyArgs(argId).init;
			all[argId] = new;
			postln("Created new User:" + new);
		}{
			postln("Returning already existing User:" + new);
		};
		^new;
	}

	init {
		envir = Mediator(id);
		envir[\user] = this;
		envirs = ();
		envirs[id] = envir;
		stack = Stack();
		stack push: envir;
	}

	*localEnvir { ^this.local.localEnvir }
	localEnvir { ^envirs[id] }
	*local { ^this.all[localId] }

	*named { | argId |
		argId ?? { argId = localId };
		^this.all.at(argId) ?? { this.new(argId); }; // stores in all!
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
		this.all[localId] = nil; // remove old user instance if present
		this.new(argId);  // create new user and store it in all
		localId = argId;  // set new localId;
		this.writeUserId; // save new localId to file.
		currentEvaluator = localId; // default evaluator to new local argId
	}

	*writeUserId {
		var path;
		path = this.makePathLocation;
		File.use(path, "w", { | f | f.write(localId.asString)});
	}

	// send to oscgroups, marking the sender as localId
	*forward { | code | OscGroups.forward(code, localId); }

	*run { | code, argId |
		currentEvaluator = argId ? localId;
		code = code.asString;
		(all[currentEvaluator] ?? { this.new(currentEvaluator) })
		.interpret(code, argId);
		this.changed(\run, currentEvaluator, code);
		currentEvaluator = localId;
	}

	interpret { | code |
		postf("========= user % runs: ========= \n\(\n\%\n\)\n", id, code);
		this.code2doc(code);
		envir use: { code.interpret.postln; };
	}

	code2doc { | code |
		var codeEntry;
		codeEntry = format(
			"//:[%] % % %\n%\n",
			Main.elapsedTime, id, envir.name, Date.localtime.stamp, code
		);
		this.document.string_(codeEntry, 1, 1000000);
		file.write(codeEntry)
	}

	evaluatorIsLocal { ^this.class.evaluatorIsLocal }
	*evaluatorIsLocal { ^currentEvaluator === localId; }
	// user of the currentEnvironment in the local machine:
	*currentUser { ^currentEnvironment[\user]; }

	// Evaluate code received via OscGroups inside the current
	// environment of the user who sent it.
	*use { | func, argId | this.new(argId.asSymbol).use(func); }
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
	// push / pop changes the envir of the user who sent it.
	// If the user is local, then also set currentEnvironment to the
	// result environment of the push or pop.
	*push { | userName, envirName |
		userName ?? { userName = currentEvaluator };
		// activate when pushing to track further changes in this user.
		^this.new(userName).activate.push(envirName);
	}

	/*
TODO:
		when push is done in response to code from remote user,
		then currentEnvironment SHOULD NOT BE SET.
		This is because the local user wants to continue in their own
		currentEnvironment.

		When push is done in response to code from local user,
		then currentEnvironment SHOULD !!!!!!!!!!! BE SET!!!!!!
		This is because for the local user, push means changing
		currentEnvironment.

IMPORTANT:
		Local code evaluation (Interpreter.interpret) is always
		in currentEnvironment, according to native SCLang definition of
		Interpreter.

		Remote code evaluation should happen ... ???

TODO: Check that the present User code actually works as described above!

	*/
	push { | envirName |
		if (this.evaluatorIsLocal) { // old code!!! - check it above.
			^this.pushLocal(this envir: envirName);
		}{
			^this.pushRemote(this envir: envirName);
		};

	}

	pushLocal { | argEnvir |
		if (currentEnvironment === argEnvir) {
			postln("environment" + argEnvir.name + "is already current.");
			"Will not push.".postln;
		}{
			argEnvir.push; // makes this currentEnvironment;
			stack push: argEnvir;
		};
		envir = argEnvir; // do this in any case!
		^envir;
	}

	pushRemote { | argEnvir | // do not touch currentEnvironment
		if (envir === argEnvir) {
			postln("envir" + argEnvir.name + "of" + this + "is already current.");
			"Will not push.".postln;
		}{
			stack push: argEnvir;
			envir = argEnvir;
		}
		^envir;
	}

	*pop { | argId |
		^this.new(argId ? currentEvaluator).pop
	}

	// TODO: Like with push: only local-evaluations change
	// currentEnvironment. remote evaluations only change users's envir.
	pop {


	}

	popLocal { | argEnvir |
		var newEnvir;
		if (stack.size == 1) { // always keep the bottom of the stack
			newEnvir = stack.top;
			postln("stack size 1. top stack is:" + newEnvir);
		}{
			stack.pop;
			newEnvir = stack.top;
			// postln("popped new stack is" + newEnvir);
		};
		// postln("envir:" + envir.name + "newEnvir" + newEnvir.name);
		if (envir === newEnvir) {
			"Avoided pop on the same environment".postln;
			^envir;
		};
		envir = newEnvir;
		postln("new envir for user" + id + "is" + envir.name);
		if (this.evaluatorIsLocal) {
			envir.push;
			postln("popped to currentEnvironment:" + currentEnvironment);
		};
		^envir;
	}

	popRemote { | argEnvir |

	}

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << id.asString;
		stream << ">" ;
	}
}