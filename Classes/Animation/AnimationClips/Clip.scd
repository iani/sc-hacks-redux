// 木 12  6 2025 16:08
// Store a list of clips of an Animation.
// A clip has:
// a name
// path: The path of the soundfile containing the animation data.
// numframes: The total number of frames of the file
// numChannels: The number of channels of the file
// startPos: The frame at which playback should start
// length: The number of frames to be included
// loop: Whether to loop or not
// speed: The playback speed (in what units?)
// relevantChannels: Channels where sonification or other effects should concentrate

ClipList {
	var list; // actually an IdentityDictionary;

	list { ^list ?? { list = IdentityDictionary() } }

	add { | name, path, buffer, startPos, length, speed, loop, relevantChannels |

	}
}

Clip {
	var <name, <path, <buffer;
	// These can be inferred from the buffer, once it is loaded:
	// var numFrames, numChannels;
	var <>startPos, <>length, <>loop, <>speed, <>relevantChannels;

}