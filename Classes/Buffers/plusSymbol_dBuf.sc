/* 26 Aug 2023 16:59
See DataBuffer.
*/

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
