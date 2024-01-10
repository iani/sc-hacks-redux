/* 31 Aug 2022 11:57

Play back osc data by sending them to an ip address.

Early prototype.  See OscDataPlayer class for current version 24 Oct 2022 11:15

Approach:

Construct an Event with durations calculated from the dt of the times of the data.
Use as play type a function that sends each data item to the desired address.
Play the Event in an EventStream.

*/

OSCDataPlayerPrototype {
	var <>data, <>from = 0, to, <>rate = 1, <>addr, <routine;

	*play { | data, from = 0, to, rate = 1, addr |
		^this.new(data, from, to, rate, addr).play;
	}

	*new { | data, from = 0, to, rate = 1, addr |
		^this.newCopyArgs(data, from, to, rate, addr ?? { NetAddr.localAddr }).init;
	}

	init {
		CmdPeriod.add({ routine = nil });
	}

	play {
		if (this.isPlaying) { ^postln(this + "already playing. will not restart.") };
		// TODO: Construct and play an EventStream.
		routine = {
			var entry, msg, now, next;
			(from..to) do: { | i |
				entry = data@i;
				now = entry[0];
				msg = entry[1];
				next = data[i + 1];
				addr.sendMsg()
			};
			postln(this + "playback ended.");
			routine = nil;
		}.fork;
	}

	isPlaying { ^routine.notNil }

}