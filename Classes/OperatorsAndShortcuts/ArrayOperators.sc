/*  5 Mar 2022 09:34

*/

+ Array {
	@@> { | symbol | ^this.brdup(symbol) }
	// create / initialize kr busses with values
	brdup { | symbol | // return the controls for use in synth funcs!
		^this collect: { | val, i |
			var ctl;
			ctl = (symbol ++ i).asSymbol;
			ctl.br(val);
		}
	}

	*> { | param, envir | // store in param of envir
		envir.envir.put(param, this);
	}
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