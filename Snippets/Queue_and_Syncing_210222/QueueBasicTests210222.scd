/* 22 Feb 2021 00:17
Testing Queue after reimplementing as NamedSingleton.
*/
//:Default
q = Queue.default;
q.server;
Queue.all;
//:
q = Queue(Server.default;);
q.server;
//:First sync test
Queue add: { "my first sync test".postln; };
//:
Synth.q(\default);