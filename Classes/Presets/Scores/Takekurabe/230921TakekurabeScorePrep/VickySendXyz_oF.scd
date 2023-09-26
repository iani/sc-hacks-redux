var of;
of = NetAddr("127.0.0.1", 10000);
\xyz >>> { | n, msg |
	of.sendMsg('xyz', *msg[3..].postln);
}