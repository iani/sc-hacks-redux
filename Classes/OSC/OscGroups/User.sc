// 土 19  4 2025 02:30
// Enable private Environments for multiple users on OscGroups

User {
	classvar all, <stack;
	classvar <localId; // local id
	classvar <currentEvaluator; // user who sent message to evaluate.
	// The local user will not push a User environment if the request
	// to do so was received from a remote user.
	var <id; // this (possibly remote) user's id.
	var envir; // currentEnvironment of this user
	// envir of local user must always be identical to currentEnvironment
	// var <stack; // environment stack; -
	var envirs; // private envirs
	var <document;

	stack { ^stack } // make stack available from User instances

	*initClass {
		StartUp add: {
			// Following only runs when evaluating code manually from the user
			// on a document.
			// It does not run when aString.interpret is called
			// from a preprogrammed function:
			// Document.initClass; DO NOT DO THIS!
			var localUser, currentEnvir;
			stack = Stack();
			localId = (this.readUserId ?? { this.makeDefaultId }).asSymbol;
			localUser = this.new(localId);
			this.all[localId] = localUser;
			currentEvaluator = localId;
			currentEnvir = localUser.envir;
			stack push: currentEnvir;
			currentEnvir.push;
			this.push(localId, localId);
			thisProcess.interpreter.preProcessor = { | code |
				this.forward(code, localId);
				code;
			}
		}
	}

	*new { | argId |
		var new;
		argId = (argId ? localId).asSymbol;
		new = this.all[argId];
		new ?? {
			new = this.newCopyArgs(argId).init;
			// at recompile Document.allDocuments is nil;
			// could not find how to force initialization of that
			// so I just defer !for the first time only!:
			if (Document.allDocuments.size == 0) {
				{ new.makeDocumentIfNeeded; }.defer(1);
			}{
				new.makeDocumentIfNeeded;
			};
			all[argId] = new;
		};
		^new;
	}

	init {
		envir = Mediator(id);
		envir[\user] = this;
		envirs = ();
		envirs[id] = envir;
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
		// remove old user instance if present:
		this.all[localId] = nil;
		// store new local user instance
		this.new(argId); // this also stores to all
		// set new localId;
		localId = argId;
		// save localId to file.
		// Paths.saveAtKey(this.idFromSystem, localId);
		this.writeUserId;
		currentEvaluator = localId;
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
		this.use({
			postf("========= user % runs: ========= \n\(\n\%\n\)\n", argId, code);
			code.asString.interpret.postln;
		}, argId ? localId);
		currentEvaluator = localId;
	}

	evaluatorIsLocal { ^this.class.evaluatorIsLocal }
	*evaluatorIsLocal { ^currentEvaluator === localId; }
	// user of the currentEnvironment in the local machine:
	*currentUser { ^currentEnvironment[\user]; }

	// Evaluate code received via OscGroups inside the current
	// environment of the user who sent it.
	*use { | func, argId | this.new(argId.asSymbol).use(func); }
	use { | func | envir use: func; }

	makeDocumentIfNeeded {
		var theDoc;
		theDoc = this.getDocument;
		if (theDoc.isNil) {
			// "The doc is nil. I will make a new one".postln;
			document = Document.new(id.asString);
		}{
			// "the doc exists. I will do nothing".postln;
		}
	}

	getDocument {
		var documents;
		documents = Document.allDocuments;
		document = documents detect: { | d | d.name.asSymbol === id };
		// postln("Found Document:" + document);
		^document;
	}

	*showDocument { this.local.showDocument }
	showDocument { document.front }


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
		^this.new(userName).push(envirName);
	}

	push { | envirName |
		var theEnvir;
		theEnvir = this envir: envirName;
		if (envir === theEnvir) {
			postln("!!!! Envir" + theEnvir.name + "is already current. I will not push.");
			^theEnvir;
		};
		envir = theEnvir;
		if (this.evaluatorIsLocal) {
			stack push: theEnvir;
			theEnvir.push;
			postln("currentEnvironment is now" + currentEnvironment);
		};
		^theEnvir;
	}

	*pop { this.new(currentEvaluator).pop }

	pop {
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

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << id.asString;
		stream << ">" ;
	}
}