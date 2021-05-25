// 25 Jan 2021 10:29. Some useful event methods

+ Event {
	+> { | key | this.splay(key) }
	+>! { | key |
		currentEnvironment.put(key, EventStream(this));
	}

	++> { | key |
		if (currentEnvironment[key] isKindOf: EventStream) {
			currentEnvironment[key].play add: this;
		}{
			this +> key
		}
	}

	splay { | key = \default, parent, quant, clock |
		var stream;
		stream = EventStream(this, parent, quant, clock).play();
		// key !? { currentEnvironment.put(key, stream); };
		currentEnvironment.put(key, stream);
		^stream;
	}
	eventStream { | parent, quant, clock | ^EventStream(this, parent, quant, clock)}
	makeStream { | argParent |
		// return new event containing all my contents as streams
		^().parent_(argParent.asParent) addStreams: this;
	}

	// add argEvent's contents as streams to myself
	addStreams { | e | e keysValuesDo: { | key, value | this[key] = value.asStream };}

	// add all key-value pairs of argEvent to self.
	addEvent { | e | e keysValuesDo: { | key, value | this[key] = value; }}

	// return event whose contents are the 'next' values of this events values
	// return nil if the next value of any value in self is nil
	makeNext { | argParent |
		var outValue, nextEvent;
		nextEvent = ().parent = parent;
		this keysValuesDo: { | key, value |
			outValue = value.next(this);
			if (outValue.isNil) { ^nil };
			nextEvent[key] = outValue;
		};
		^nextEvent;		
	}

	asParent { ^this.copy } // 'copy' also copies the parent!

}

+ Nil { asParent { ^EventStream.defaultParent.asParent } }