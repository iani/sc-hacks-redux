/* 26 Aug 2023 16:56
Buffers allocated for use as non-audio-data sources are kept in a separate
dictionary from audio-file-data buffers.

We only use this class to remove allocated buffers from Library
when the Server boots.

All the other work regarding this is done in Symbol:dbuf.

*/

DataBuffer {
	*initClass {
		ServerBoot add: {
			Library.global.removeEmptyAt(\databufs);
		}
	}
}

+ Symbol {
	dbuf { | data |
		var cfunc, buf;
		if (data.size > 0) {
			cfunc = { | b |
				b.sendCollection(data);
				Library.put(\databufs, this, b);
			};
		}{
			data = (1..8); // dummy data for minimum size buffer
		};
		buf = Library.at(\databufs, this);
		if (buf.isNil) {
			postln("allocating new buffer for " + this);
			buf = Buffer.alloc(Server.default, data.size, 1, cfunc);
		}{ // cfunc only sends if original data arg was non empty
			cfunc.(buf);
		}
		^buf;
	}
}

+ Array {
	toBuf { | name | ^name.dbuf(this) }
}
