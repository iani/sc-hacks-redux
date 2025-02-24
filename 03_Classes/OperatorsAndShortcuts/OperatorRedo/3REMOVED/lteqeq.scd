/* Sun 23 Feb 2025 04:13
	experimental
	If object at key is a Bus, then set its value.
	Else store value at key in envir.

	Mon 24 Feb 2025 01:32 This code has been transferred to
	operator <@ of Symbol.

*/
/*
+ Symbol {
	<== { | argValue, envir |
		var storedObject;
		if (envir.isNil) {
			envir = currentEnvironment
		}{
			envir = envir.envir
		};
		storedObject = envir at: this;
		if (storedObject isKindOf: Bus) {
			storedObject.set(argValue);
		}{
			envir.put(this, argValue);
		}
	}
}
*/