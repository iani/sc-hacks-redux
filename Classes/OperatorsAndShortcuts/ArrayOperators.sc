/*  5 Mar 2022 09:34

*/

+ Array {
	// create / initialize kr busses with values
	brdup { | symbol, offset = 0 | // return the controls for use in synth funcs!
		^this collect: { | val, i |
			var ctl;
			ctl = (symbol ++ (i + offset)).asSymbol;
			ctl.br(val);
		}
	}
}