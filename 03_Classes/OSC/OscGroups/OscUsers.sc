/* 13 Feb 2022 15:47
Records osc messages issued by one user.
Is stored as entry in OscHistory:users.
*/

OscUser {
	var <name, codeEntries;

	*new { | name |
		^this.changed(\users).newCopyArgs(name);
	}

	addCodeEntry { | code, result, time |
		codeEntries = codeEntries add: OscCodeEntry(code, result, time);
		this.changed(\newCodeEntry); // use \codeEntry instead?
	}
}
