//Fri 23 Feb 2024 11:38
//Array

+ Array {
	@> { | bufname |
		// send contents to buffer
		// Create new buffer and store it in bufname, under Library at Arrray (!)
		// If previous buffer exists under that name, free that buffer. (!?)
		var buf;
		buf = Library.at(Buffer, bufname); // Array.bufname? We'd need to remove on Server quit!
		if (buf.isNil) {
				buf = Buffer.sendCollection(Server.default, this);
		} {
			if ( buf.size == this.size ) {
				Buffer.sendCollection(Server.default, this);
			} {
				buf.free;
				buf = Buffer.sendCollection(Server.default, this);
			}
		};
		Library.put(Buffer, bufname, buf);
	}
}