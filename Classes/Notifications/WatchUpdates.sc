//: 18 Jan 2021 23:27
/* Utility to monitor changed notifications issued with anObject.changed

*/

WatchChanges {
	*update { | changer, changeMessage ... args |
		postf("% brodcast '%' with args: %\n", changer, changeMessage, args);
	}
}

+ Object {
	watchChanges {
		this addDependant: WatchChanges;		
	}
}