// 土 19  4 2025 02:30
// Enable private Environments for multiple users on OscGroups

User {
	classvar all, <stack;
	classvar <localId; // local id
	classvar <currentEvaluator; // user who sent message to evaluate.
	// The local user will not push a User environment if the request
	// to do so was received from a remote user.
	var <id; // this (possibly remote) user's id.
	var envir; // currentEnvironment
	// var <stack; // environment stack; -
	var envirs; // private envirs
	var <document;

	stack { ^stack }
	*initClass {
		StartUp add: {
			// Following only runs when evaluating code manually from the user
			// on a document.
			// It does not run when aString.interpret is called
			// from a preprogrammed function:
			// Document.initClass; DO NOT DO THIS!
			stack = Stack();
			localId = (this.readUserId ?? { this.makeDefaultId }).asSymbol;
			this.all[localId] = this.new(localId);
			Mediator.push;
			thisProcess.interpreter.preProcessor = { | code |
				this.forward(code, localId);
				code;
			}
		}
	}

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

	*use { | func, argId |
		this.new(argId.asSymbol).use(func);
	}

	*new { | argId |
		var new;
		argId = (argId ? localId).asSymbol;
		new = this.all[argId];
		new ?? {
			new = this.newCopyArgs(argId, argId.envir, ());
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
	// Operate on own envir
	// Push envir in local environemnt, but without sending to
	// OscGroups: Prevent setting the currentEnvironment of other user
	*push { | userName, envirName |
		userName ?? { userName = this.localId };
		^this.new(userName).push(envirName);
	}

	push { | envirName |
		var theEnvir;
		// should not push if this was received from remote user
		currentEvaluator ?? { currentEvaluator = localId };
		postln("checking push of local user" + localId + "vs. remote id"
			+ currentEvaluator
		);
		if (this.evaluatorIsLocal) {
			"Now doing locally requested push".postln;
			envirName = envirName ? id;
			theEnvir = this.envirs[envirName];
			postln("Preparing to push" + theEnvir);
			if (theEnvir.isNil) {
				postln("Creating and storing new envir" + theEnvir);
				theEnvir = this envir: envirName;
				if (currentEnvironment === theEnvir) {
					"Skipping push of currentEnvironment on itself".postln;
				}{
					postln("now pushing:" + theEnvir);
					theEnvir.push;
					stack push: theEnvir;
				};
				postln("I now pushed" + theEnvir);
			}
		}{
			"not pushing because received remotely".postln;
		};
		^theEnvir ? currentEnvironment;
	}

	*pop { | userName |
		var theUser;
		// "User:pop is not complete. results may be unexpected.".postln;
		if (userName.notNil) {
			theUser = userName.asSymbol;
		}{
			theUser = currentEnvironment[\user];
		};
		this.new(theUser).pop;
	}

	*currentUser {
		^currentEnvironment[\user];
	}
	pop {
		postln("popping from user" + this);
		// "pop not implemented".postln;
		if (this.evaluatorIsLocal) {
			if (stack.isEmpty.not) {
				postln("popping" + currentEnvironment);
				currentEnvironment.pop;
				// push next mediator from stack
				stack.pop;
				stack.top.push;
			}{
				postln("The stack is empty. Keeping currentEnvironment.")
			}
		}{
			postln("Refusing to pop for remote user:" + id);
		}
	}

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << id.asString;
		stream << ">" ;
	}
}