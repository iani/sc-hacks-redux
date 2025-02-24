/*  8 Sep 2021 14:06
Utility method for IdeFantasy.
*/

+ Array {
	allBut { | toRemove |
		var copy;
		copy = this.copy;
		copy remove: toRemove;
		^copy;
	}
}