/* 26 Aug 2023 16:56
Buffers allocated for use as non-audio-data sources are kept in a separate
dictionary from audio-file-data buffers.

We only use this class to remove allocated buffers from Library
when the Server boots.

*/

DataBuffer {
	*initClass {
		ServerBoot add: {
			postln("Library at databufs BEFORE server boot" + Library.at(\databufs));
			Library.global.removeEmptyAt(\databufs);
			postln("Library at databufs AFTER server boot" + Library.at(\databufs));
		}
	}
}