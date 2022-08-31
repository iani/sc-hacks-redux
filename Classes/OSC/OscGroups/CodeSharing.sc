/* 31 Aug 2022 06:31
Experimental rewriting to enable more flexible recording
of code history.  See also OscRecorder2 class and Symbol:share method.

Experimental!

Both OscRecorder2 and OscGroups should use this to watch executed code.
*/

CodeSharing {
	*initClass {
		/*
			StartUp add: {
			thisProcess.interpreter.preProcessor = { | code |
			this.changed(message, code); // allow OSCRecorder to record locally evaluated code
			code;
			}
		*/
	}
}