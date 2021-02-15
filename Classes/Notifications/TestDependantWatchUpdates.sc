//: 18 Jan 2021 23:27
/* Utility to monitor changed notifications issued with anObject.changed

*/

TestDependant2 {
	// variant of TestDependant: posts all args.
	*update { arg thing, what ... args;
		postf("% changed with message % and args %\n", thing, what, args);
	}
}

+ Object {
	watchChanges { | switch = true |
		if (switch) {
			this addDependant: TestDependant2;
		}{
			this removeDependant: TestDependant2;
		}
	}
}

