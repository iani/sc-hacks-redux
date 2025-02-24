/* 16 Dec 2022 21:59
For Duel Revisited
*/

/*
+ OscGroups {
	*activateCodeMessageSpecial {
		var x;
		x = \code_x;
		postln("activating code message:" + x);
		OSC.add(x, { | n, msg |
			var code;
			code = msg[1].asString;
			if (code.isSafe) {
				postf("========= Remote evaluation: X ========= \n\(\n\%\n\)\n", code);
				{	// permit window operations via remote evaluated code
					code.interpret.postln;
					this.changed(\evalCode, code);
				}.defer;
			}{
				"WARNING: UNSAFE CODE RECEIVED!:".postln;
				code.postln;
			}
		}, \codeEvaluationx)
	}

	*activateCodeMessagey {
		var x;
		x = \code_y;
		postln("activating code message:" + x);
		OSC.add(x, { | n, msg |
			var code;
			code = msg[1].asString;
			if (code.isSafe) {
				postf("========= Remote evaluation: Y ========= \n\(\n\%\n\)\n", code);
				{	// permit window operations via remote evaluated code
					code.interpret.postln;
					this.changed(\evalCode, code);
				}.defer;
			}{
				"WARNING: UNSAFE CODE RECEIVED!:".postln;
				code.postln;
			}
		}, \codeEvaluationy)
	}
}
*/
