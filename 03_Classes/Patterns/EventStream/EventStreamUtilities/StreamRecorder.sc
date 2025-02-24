/* 24 Jan 2021 20:41
Record events played from a stream, with timestamps.
Playback using those timestamps.
*/

StreamRecorder {
	var <stream; // the stream from which the 
	var <recordings; // array of EventHistory
	var <currentRecording; // recording currently 

	*new { | stream |
		// use Registry to only allow one recorder per Stream.
		^Registry(stream.class, stream, { this.newCopyArgs(stream).init;});
	}

	start {
		
		
	}

	stop {
		
		
	}
}

EventHistory {
	var <stream;
	var <timestamp;
	var <events;

	*new { | stream |
		
	}
}