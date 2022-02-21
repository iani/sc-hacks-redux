// 25 Jan 2021 10:29. Some useful event methods

+ Event {
	+> { | key |
		//
		var atKey, new;
		atKey = currentEnvironment[key];
		atKey.stop;
		new = this.splay(key);
		currentEnvironment[key] = new;
		^new;
	}

	splay { | key  | ^EventStream(this).start; }

	+>! { | key |
		var new;
		new = EventStream(this);
		currentEnvironment.put(key, new);
		^new;
	}

	++> { | key |
		var e;
		e = currentEnvironment[key];
		if (e isKindOf: EventStream) {
			^e addEvent: this;
		}{
			^this +> key
		}
	}

	@> { | eventKey, beatsKey |
		this.addBeat(eventKey, beatsKey)
	}

	addBeat { | eventKey, beatKey |
		var new;
		beatKey = beatKey ? eventKey;
		new = this +>! eventKey;
		beatKey.beat.addDependant(new);
		^new;
	}
}

/*
+ Event {
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	// FIXME: debug this. Do not add new eventsstreams if same name event name
	@> { | beat, name = \eventStream |
		var es;
		es = ~eventStream;
		if (es.isNil) {
			es = EventStream(this)
		}{
			es.add(this)
		};
		es addBeat: beat;
	}

	@>> { | beat, name = \eventStream |
		var es;
		es = ~eventStream;
		if (es.isNil) {
			es = EventStream(this)
		}{
			es.add(this)
		};
		es addBeat: beat.beat.start;
	}
}
*/