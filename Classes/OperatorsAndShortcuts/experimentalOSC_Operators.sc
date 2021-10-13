+ Integer {

	trace {
		postf("Tracing messages from port %\n", this);
	}

	untrace {
		postf("Stopped tracing messages from port %\n", this);
	}

	|>|  { | index |
		// this should be a symbol method
		// it should imitate Route object from pd/MAX
		"operator not yet implemented".postln;
	}

}

+ Symbol {

	|>|  { | index |
		// this should be a symbol method
		// it should imitate Route object from pd/MAX
		"operator not yet implemented".postln;
		// prototype:
		// OSCFunc.addNotifier(this, { | msg |
		//     msg[index] ... do something here?????
		//     // how to make this chainable to a function?
		//     maybe: |>| { | action, index | } ???????
 		// })
		//
		// or maybe set a bus?
		// { | [lo, hi], index | }
	}


}