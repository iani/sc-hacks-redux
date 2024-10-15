var of;
of = NetAddr("127.0.0.1", 10000);
\xyz >>> { | n, msg |
	// msg.postln;
	of.sendMsg('xyz', *msg[1..]);
}