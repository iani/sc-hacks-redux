/* 26 Jun 2023 13:00
Add a string to an array only if it does not contain it.
*/

+ Array {
	addString { | string |
		if (this.detect({|s| s == string}).isNil) { this add: string }
	}
}