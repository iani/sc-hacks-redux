/* 10 Feb 2021 22:27

*/
+ String {
	audioFiles { | ... types |
		if (types.size == 0) {
			types = ["aiff", "aif", "wav", "WAV"];
		};
		^types.collect({ | type | (this +/+ format("*.%", type)).pathMatch }).prDoNotFlatten;
	}
}
