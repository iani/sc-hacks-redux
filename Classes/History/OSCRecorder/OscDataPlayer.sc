/* 24 Oct 2022 11:17

TODO: Construct event stream for playing data.
This could be returned by OscDataReader.
Therefore there may be no need for OscDataPlayer.

*/

OscDataPlayer {
	var <>data, <>addr;
	// var <routine;

	init { | argData, argAddr |
		data = argData;
		addr = argAddr ?? { NetAddr.localAddr; };
	}

	*fromReader { | addr |
		^this.fromLib(\fromReader, OscDataReader.allData, addr);
	}

	// isPlaying { ^routine.notNil }

}