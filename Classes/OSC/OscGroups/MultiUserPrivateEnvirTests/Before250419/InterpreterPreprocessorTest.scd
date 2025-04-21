/*
Important Note:

When you add aFunction to the preProcessor of the interpreter like this:

thisProcess.interpreter.preProcessor = aFunction;

then this function is run whenever the user evaluates code in SuperCollider
by typing control-enter (in SCIde) or control-c control-c (in Emacs)
or other similar ways.

In contrast, the code is not run when SuperCollider interprets
a string in response of some code in a function.

*/
//:
OSC.add(\test, { | n, msg |
	msg[1].postln;
	// !!!!!! This will NOT call the preprocessor function !!!!!!
	msg[1].asString.interpret.postln;
});

//: Test the above:
r = {
	loop {
		NetAddr.localAddr.sendMsg(\test, "100.rand");
		1.wait;
	}
}.fork;
//: This preprocessor function will run only
//when evaluating code manually from the user,
//such as in the example below.
thisProcess.interpreter.preProcessor = { | code |
	"will interpret some code".postln;
	code;
};
1000.rand;
//:
"100".interpret