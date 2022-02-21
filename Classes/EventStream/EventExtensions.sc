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
		currentEnvironment.put(key, EventStream(this));
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

}