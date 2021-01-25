/* 25 Jan 2021 10:29
Some useful event methods
*/
+ Event {
	splay { | quant, clock |
		^EventStream(this).play()	
	}

	makeStream { | argParent |
		// return new event containing all my contents as streams
		^().parent_(argParent.asParent) addStreams: this;
	}

	addStreams { | argEvent |
		// add argEvent's contents as streams to myself
		argEvent keysValuesDo: { | key, value | this[key] = value.asStream };
	}
	
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

	asParent { ^this.copy }

	addEvent { | argEvent |
		// add all key-value pairs of argEvent to self.
		argEvent keysValuesDo: { | key, value | this[key] = value; }
	}

}

+ Nil {
	asParent { ^EventStream.defaultParent.asParent }
}