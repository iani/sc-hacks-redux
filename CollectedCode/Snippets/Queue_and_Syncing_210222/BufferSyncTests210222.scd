/* 22 Feb 2021 12:39
Examples testing behavior without sync, to show which cases need sync.

*/
//:================================================================
//
b = Buffer.alloc(Server.default, 2 ** 9, 1);
b.setn(0, 127); // no error message is issued, but the value was not set!
// error message is issued: /b_get index out of range:
b.get(0, { | val | postf("my value at 0 was %\n", val )});
//:trying above with built-in sync and fork:
{
	b = Buffer.alloc(Server.default, 2 ** 9, 1);
	Server.default.sync; // ensure that buffer exists before setting values
	b.setn(0, 127);
	// the value is set and accessed. 
	b.get(0, { | val | postf("my value at 0 was %\n", val )});	
}.fork;
// No sync needed to plot after setting:
//:Fill existing buffer with some vales
b.setn(*[(0..(2**9-1)), Signal.sineFill(2**9, [1], [0])].flop.flat);
//:
b.plot;
//:Again: plot does not seem to require sync after setn to work:
{
	var b;
	b = Buffer.alloc(Server.default, 2 ** 9, 1);
	Server.default.sync; // ensure that buffer exists before setting values
	b.setn(*[(0..(2**9-1)), Signal.sineFill(2**9, [1, 1], [0, pi])].flop.flat);
	        b.get(5, { | val | "value at 5 was: %\n", postln; });
	b.plot;
}.fork(AppClock); // AppClock required to enable plot. (Alternative: 'defer')
//:================================================================
//:Test qalloc, qsetn, qget
b = Buffer.qalloc(Server.default, 2 ** 9, 1); // ensure server is booted
b.qsetn(0, 127); // ensure that buffer exists
// ensure that the value has been sent to the buffer
b.qget(0, { | val | postf("my value at 0 was %\n", val )});

//:Counterexample: omitting sync here sends the get too soon:
b = Buffer.qalloc(Server.default, 2 ** 9, 1); // ensure server is booted
b.qsetn(0, 127); // ensure that buffer exists
// omitting sync here sends the get too soon:
b.get(0, { | val | postf("my value at 0 was %\n", val )}); 
