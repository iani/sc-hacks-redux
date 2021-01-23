//: 23 Jan 2021 20:56
/*
extra methods for EventStream to modify and customize content on the fly.


*/

+ EventStream {
		// ================================================================
	// extensions, features etc.
	// Just a few initial drafts so far:
	/*
	add { | inEvent |
		inEvent keysValuesDo: { | key, value |
			event[key] = value.asStream;
		}
	}

	set { | inEvent |
		// inherit parent
		inEvent.parent = eventStreamParent;
		event = inEvent;
	}
	setParentKey { | key, value |
		eventStreamParent[key] = value;
	}
	*/

	
}