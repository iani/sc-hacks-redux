// 土 19  4 2025 02:30
// Enable private Environments for multiple users on OscGroups
// Fix again. 日 27  4 2025 12:34:
// Things to fix:
// 1. Create and use the users prescribed by arguments to enable, and
// find localId from system user name and DO NOT create a timestamped user
// 2. Files for users should be created and written.  Correct error:
// nil - file not found / cannot open file
// 3. Evaluations from remote user should not run inside the currentEnvironment
// of the local user.  Instead, they should strictly run inside the
// environment of the remote user that sent the remote code.

// ======= Important modification 日 27  4 2025 12:36 =======
// User no longer uses OscGroups class to send/receive/run code messages
// Instead, all OSC code sending/receiving/evaluation is handled by the
// User class itself.
// User uses a different message for sending/receiving/evaluating code,
// so that it will be entirely seprate from the mechanism of OscGroups.
// OscGroups remains in its original version.
User {
	classvar all;
	classvar <localId; // local id
	classvar <currentEvaluator; // user who sent message to evaluate
	classvar <localUser;
	classvar <enabled = false;
	classvar <sessionName;
	classvar <sessionPath;
	classvar <oscSendPort = 22244, <oscRecvPort = 22245;
	classvar <sendAddress;
	classvar <codeMessage = '/c';
	classvar <>verbose = false;
	var <id; // this (possibly remote) user's id.
	var envir; // currentEnvironment of this user
	var envirs; // user evaluates code only within one of these private envirs
	var document, <file;
	var <stack;
	var <isActive = false;
	var <makeDocument = false;

	*enable { | argSessionName = "session", waitTime ... users |
		// if waitTime is given, defer the enabling by wait seconds (default: 3).
		// Use this to enable User from a startup script.
		// This ensures that startup scripts loaded locally will not be
		// forwarded to other users.
		// if users are given, then activate them.
		if (enabled) { "User class is already enabled.".postln; ^this; };
		postln("Enabling User session" + argSessionName + "with users" + users);
		if (waitTime.notNil) {
			postln("Deferring User enable by" + waitTime + "seconds");
			{
				this.doEnable(argSessionName);
				this.activate(*users);
			} defer: waitTime;
		}{
			this.doEnable(argSessionName);
			this.activate(*users);
		};
	}

	*makeDocumentsForLocalUser { | argId = \iani |
		"\n========= User.makeDocumentsForLocalUser was CALLED =========\n".postln;
		this doAfterActivate: {
			if (argId === localId) {
				postln("\nI will make documents for users:" + this.allUserKeys +"\n");
				this.allUserKeys do: { | k | this.all[k].makeDocument = true; };
			}
		}
	}
	*doAfterActivate { | action |
		this.addDependant({ | who, what |
			if (what === \activated) { action.value }
		});
	}

	*doEnable { | argSessionName |
		var currentEnvir;
		postln("\n===== Enabling User session" + argSessionName + "=====\n");
		localId = (this.readUserId ?? { this.makeDefaultId }).asSymbol;
		argSessionName ?? { sessionName = argSessionName };
		this.makeSessionFolder(argSessionName);
		localUser = this.new(localId);
		localUser.activate; // !!!!!!!!!!!!!!!!!!!!!!!!!!!!
		this.all[localId] = localUser;
		currentEvaluator = localId;
		// currentEnvir = localUser.envir;
		localUser.stack push: currentEnvir;
		currentEnvir.push;
		this.push(localId, localId);
		enabled = true;
		// OscGroups.enable;
		// this.oscSendPort = oscSendPort; // create sendAddress
		this.makeSendAddress;
		this.openRecvUDPPort;
		this.enableCodeForwarding;
		OSC.add(codeMessage, { | n, msg |
			User.run(msg[1].asString, msg[2]);
		});
		postln("Enabled User class for session" + argSessionName);
		postln("Session folder is:" + sessionPath);
		this.changed(\enabled);
	}

	*sendMessage { | message, action |
		OSC.add(message, action);
	}

	enableCodeForwarding { this.class.enableCodeForwarding }
	*enableCodeForwarding {
		thisProcess.interpreter.preProcessor = { | code |
			localUser.code2doc(code);
			// this.forward(code, localId);
			// OscGroups.sendAddress.sendMsg(codeMessage, code, localId);
			this.sendCode(code);
			code;
		};
	}

	disableCodeForwarding { this.class.disableCodeForwarding }
	*disableCodeForwarding {
		thisProcess.interpreter.preProcessor = { | code |
			localUser.code2doc(code);
			// this.forward(code, localId);
			// OscGroups.sendAddress.sendMsg(codeMessage, code, localId);
			// this.sendCode(code);
			code;
		}
	}

	*interpretSend { | argCode |
		// When interpreting code from inside an action Function of a View,
		// The code is NOT sent to OscGroups. The interpreter preprocessor function
		// is NOT run.  Therefore, use the present function to
		// run code + send it to other users from any View on a GUI window.
		argCode.interpret;
		this.sendCode(argCode);
	}

	*sendCode { | argCode | // when active, send code before interpreting
		// also for sending code manually, for tests
		if (verbose) { postln("user" + localId + "sending code to OscGroups.") };
		sendAddress.sendMsg(codeMessage, argCode, localId);
	}

	*sendToAll { | ... message |
		this.forwardMessage(*message);
		this.sendToSelf(*message);
	}

	*forwardMessage { | ... message |
		// postln("Forwarding message" + message);
		sendAddress.sendMsg(*message);
	}

	*sendToSelf { | ... message |
		NetAddr.localAddr.sendMsg(*message);
	}

	*oscSendPort_ { | argPort = 22244 |
		oscSendPort = argPort;
		this.makeSendAddress;
	}

	*makeSendAddress {
		sendAddress = NetAddr("127.0.0.1", oscSendPort);
	}

	*oscRecvPort_ { | argPort = 22245 |
		oscRecvPort = argPort;
		this.openRecvUDPPort;
	}

	*openRecvUDPPort {
		var didOpen = false;
		didOpen = thisProcess openUDPPort: oscRecvPort;
		if (didOpen) {
			postln("User successfully opened port" + oscRecvPort + "for listening");
		}{
			postln("User COULD NOT open port" + oscRecvPort + "for listening");
		}
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

	*activate { | ... argUsers |
		postln("\nActivating Users:" + argUsers ++"\n");
		if (argUsers.size == 0) { argUsers = this.allUserKeys; };
		argUsers do: { | u | this.new(u.asArray[0]).activate(u.asArray[1]) };
		this.changed(\activated);
	}

	activate {
		if (isActive) {
			postln("User" + id + "is active. Skipping activate.");
			^this;
		};
		this.makeDocumentAndFile;
		isActive = true;
		this.loadStartupFiles;
	}

	makeDocumentAndFile { // make document and file for recording session code.
		this.makeFile;     // ALWAYS make new file for recording user code
		this.document; // create document for this user only if not already existent
		// Both document and file record the same code.
		// I use a file because I could not find how to save
		// code from a ScelDocument (EMACS IDE) on sclang shutdown
	}

	makeFile {
		var thePath;
		if (file.notNil) { ^file };
		thePath = this.filePath;
		postln("User" + id + "made path" + thePath);
		postln("Filename is" + thePath.fileName);
		postln("The folder is" + thePath.pathOnly);

		// ^file = File.use(this.filePath, "w", { | f | f.write(this.documentHeader); });
		file = File(thePath, "w");
		file.write(this.documentHeader) ;
		^file;
	}

	filePath { ^sessionPath +/+ id.asString ++ ".scd"; }

	documentHeader {
		^format("//Document for % created at %\n", id, Date.getDate.stamp);
	}

	document { ^document ?? { document = this.getDocument; }; }

	makeDocument_ { | bool |
		makeDocument = bool;
		if (bool) { this.getDocument };
	}

	getDocument {
		var documents;
		if (makeDocument.not) { ^nil }; // skip making document
		documents = Document.allDocuments;
		// document = documents detect: { | d | d.name.asSymbol === id };
		document = documents detect: { | d | d.title.asSymbol === id };
		if (document.isNil) {
			postln("Making document for User" + id);
			document = Document.new(id.asString);
			document.string_(this.documentHeader);
		}{
			postln("Returning existing user document" + id);
		};
		^document;
	}

	loadStartupFiles { 	// from Platform:loadStartupFiles
		this.startupFiles do: { | afile |
			afile = afile.standardizePath;
			if (File.exists(afile)) {
				postln(
					"--- Loading startup file"
					+ afile.fileName + "for user" + id
				);
				this load: afile;// load file locally!!!
			};
		}
	}

	startupFiles {
		^(Platform.userAppSupportDir +/+ "Users" +/+ id +/+ "*.scd").pathMatch
		// ^[]; // disable for now
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
		// foundDoc.text.postln;
	}

	saveAndCloseDocument {
		this.saveDocument;
	}

	findDocument { // find document if it exists in user or in IDE
		// used for writing documents to disk at end of session.
		document ?? { ^document };
		^Document.allDocuments detect: { | d | d.name.asSymbol === id };
	}

	*deactivate { | ... argUsers |
		if (argUsers.size == 0) { argUsers = this.allUserKeys; };
		argUsers do: { | u | this.new(u).deactivate };
	}

	deactivate { isActive = false; }

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
			// postln("Returning already existing User:" + new);
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
		if (File.exists(path)) { ^File.readAllString(path) }{ ^nil }
	}

	*makePathLocation { | argId |
		^Paths.makePathLocation("oscGroups_" ++ this.systemUserName);
	}

	*systemUserName { ^Platform.userHomeDir.fileName }

	*makeDefaultId {
		// do not use this because it requires the user to edit a file:
		// ^(this.systemUserName ++ "_" ++ Date.getDate.stamp
		// 	++ "_" ++ 1000.rand.asString).asSymbol;
		// Instead, use the simpler version:
		^this.systemUserName.asSymbol;
	}

	*localId_ { | argId, writeId = true |
		argId = argId.asSymbol;
		// ! replace User instance in all from old id to new id.
		this.all[localId] = nil; // remove old user instance if present
		this.new(argId);  // create new user and store it in all
		localId = argId;  // set new localId;
		if (writeId) { this.writeUserId }; // optionally save new localId to file.
		currentEvaluator = localId; // default evaluator to new local argId
		postln("Debugging localId_. argId was" + argId + "localId is" + localId);
	}

	*writeUserId {
		var path;
		path = this.makePathLocation;
		// File.use(path, "w", { | f | f.write(localId.asString)});
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
		postf("========= user % at envir % runs: ========= \n\(\n\%\n\)\n", id, envir.name, code);
		this.code2doc(code);
		envir use: { code.interpret.postln; };
	}

	code2doc { | code |
		var codeEntry;
		codeEntry = format(
			"//:[%] % % %\n%\n",
			Main.elapsedTime, id, envir.name, Date.localtime.stamp, code
		);
		// 100000000:
		// -- ScIDE (qt) Documents append AT END OF DOCUMENT
		// -- EMACS (scel) Documents append AT POINT
		if (makeDocument) { this.document.string_(codeEntry, 100000000); };
		file !? { file.write(codeEntry) };
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
		// Return envir named argId from this users' envirs
		// If argId is nil, return the current envir (or the default envir!)
		// Do NOT set envir! (Other methods do that.)
		var result;
		argId ?? { ^this.currentEnvir; };
		result = this.envirs[argId];
		result ?? {
			result = Mediator(argId);
			result[\user] = this;
			envirs[argId] = result;
		};
		^result;
	}

	currentEnvir { ^envir ?? { envir = this.defaultEnvir }; }

	defaultEnvir {
		var result;
		result = this.envirs[id];
		result ?? {
			result = Mediator(id);
			result[\user] = this;
			envirs[id] = result;
		};
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
		// ^this.new(userName).activate.push(envirName);
		// ACTIVATE ONLY WHEN REQUESTED EXPLICITLY
		^this.new(userName).push(envirName);
	}

	/*  IMPORTANT:
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
	postEnvir { postln("envir of" + id + "is" + envir.name ); }
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
		postln("User: <" ++ id ++ "> runs pushRemote: <" ++ argEnvir.name ++ ">");
		postln("User: <" ++ id ++ "> envir is <" ++ envir.name ++ "> and new envir is <" ++ argEnvir.name ++ ">");
		// postln("The two envirs are equal?" + (argEnvir === envir));
		if (argEnvir === envir) {
			"The envirs are equal. I will not push".postln;
		}{
			"The envirs are different. I will push".postln;
		};

		if (envir === argEnvir) {
			postln("envir" + argEnvir.name + "of" + this + "is already current.");
			"Will not push.".postln;
		}{
			postln("pushing envir <" ++ argEnvir.name ++ "> for user <" ++ id ++ ">");
			stack push: argEnvir;
			postln("I am now setting envir to the new argEnvir:" + argEnvir.name);
			envir = argEnvir;
			postln("argEnvir received was" + argEnvir.name +
				"and after pushing the envir is" + envir.name);
		};
		^envir;
	}

	*pop { | argUserId | // pop environment from any User, or currentEvaluator
		^this.new(argUserId ? currentEvaluator).pop
	}

	// Like with push: Local-evaluations DO change currentEnvironment.
	// Remote evaluations DO NOT change currentEnvironment.
	pop {
		if (this.evaluatorIsLocal) { // old code!!! - check it above.
			^this.popLocal;
		}{
			^this.popRemote;
		};
	}

	popLocal { // pop remote + set currenEnvironment to new envir.
		this.popRemote.push; // push popped user envir to local currentEnvironment
	}

	popRemote {
		// always keep the bottom of the stack
		if (stack.size == 1) {  // do not pop. return top of stack
			postln("Stack size 1. Pop does nothing and returns stack top:" + stack.top.name);
		}{ // pop and return pop of stack.
			stack.pop;
			postln("Popped. New top of stack is" + stack.top.name);
		};
		^envir = stack.top;
	}

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << id.asString;
		stream << ">" ;
	}

	*load { | argPath, argEnvir |
		// load a file without forwarding the code to other users.
		^localUser.load(argPath, argEnvir);
	}

	load { | argPath, argEnvir |
		// load a file without forwarding the code to other users.
		var result;
		this.disableCodeForwarding;
		postln(
			"--- Loading file"
			+ argPath.fileName + "for user" + id
		);
		postln("The environment I will use is:" + envir);
		// Load  in rrgEnvir or in this User's current envir.
		(argEnvir ? envir) use: { result = argPath.load; };
		this.enableCodeForwarding;
		^result;
	}
}