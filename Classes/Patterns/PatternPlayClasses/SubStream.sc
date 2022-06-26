/* 25 Jun 2022 14:22
SubStream:
I hold an event holding EventStreams as values. When evaluated, I
play the next event from each EventStream value, with this message:


*/

SubStream {
	var <parent, <subStreams;
	*new { | parent |
		^this.newCopyArgs(parent, ()).init;
	}

	init { parent.mergeEvent((play: this)) }

	addSubStream { | eventStream, key |
		eventStream = eventStream.asEventStream;
		eventStream mergeEvent: (key: key, parentStream: parent);
		subStreams[key] = eventStream;
	}

	value { | ... args |
		postln("Still testing SubStream. value with: " + args);
		subStreams do: { | sub |
			sub.playAndNotify(sub.getNextEvent);
		}
	}
}