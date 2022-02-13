/* 13 Feb 2022 15:56
Stores a single item of code sent / received by OscGroups.
*/

OscCodeEntry {
	var <code, <result, <time;

	*new { | code, result, time |
		^this.newCopyArgs(code, result, time);
	}
}