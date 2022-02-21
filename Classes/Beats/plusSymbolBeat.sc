/* 21 Feb 2022 08:10
Shortcuts for using BeatCounter.
*/

+ Symbol {
	beat { | dt |
		var bc;
		bc = Registry(BeatCounter, this, {
			BeatCounter()
		});
		dt !? { bc.dt = dt };
		^bc;
	}

	startBeat { ^this.beat.start }
	stopBeat { ^this.beat.stop }
}

+ Function {
	beat { | beatName, actionName, dt |
		^beatName.beat(dt).add(actionName ? beatName, this);
	}
}

+ EventStream {
	addBeat { | beat |
		beat.beat.add(this,
			{ this.postln; "this works".postln; this.next.play; }
		);
	}
	removeBeat { | beat |
		beat.beat.remove(this);
	}
	// update { this.next.play; }
	// value { this.next.play; }
}

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