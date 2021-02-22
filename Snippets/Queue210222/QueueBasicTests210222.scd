/* 22 Feb 2021 00:17
Testing Queue after reimplementing as NamedSingleton.
*/
//:Default
q = Queue.default;
q.server;
Queue.all;
//:First sync test
Queue add: { "my first sync test".postln; };
//:================================================================
//:testing behavior without sync
// to find out precisely which cases need sync.
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
//:Fill existing buffer with some vales
b.setn(*[(0..(2**9-1)), Signal.sineFill(2**9, [1], [0])].flop.flat);
//:
b.plot;
//:plot does not seem to require sync after setn to work:
{
	b = Buffer.alloc(Server.default, 2 ** 9, 1);
	Server.default.sync; // ensure that buffer exists before setting values
	b.setn(*[(0..(2**9-1)), Signal.sineFill(2**9, [1, 1], [0, pi])].flop.flat);
	b.plot;
}.fork(AppClock); // AppClock required to enable plot. (Alternative: 'defer')