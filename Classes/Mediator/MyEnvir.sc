
/*
Store an environment as class variable for safe global access of its variables.

Demo class - try experimenting with this principle for your own needs.

// store something in a variable
MyEnvir.buf1 = \justTesting;
// retrieve the thing stored in the variable
MyEnvir.buf1;

Sun 18 Feb 2024 10:59

Disabling this class here, because used by other libs in collaboration with others.

*/

/*
MyEnvir {
	classvar >event;

	*event {
		event ?? { event = () };
		^event;
	}
	*doesNotUnderstand { | selector ... args |
		^this.event.perform(selector, *args);
	}

}
*/