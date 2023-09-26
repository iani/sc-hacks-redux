/* 24 Sep 2023 17:24

*/

var of1, of2;
of1 = NetAddr("127.0.0.1", 10000);
of2 = NetAddr("127.0.0.1", 10001);
{
	var trig, yoshi, mary;
	trig = Impulse.kr(20);
	yoshi = Xyz(1, 0.2, 0.1, 1.5) + Xyz(2, 0.2, 0.1, 1.5);
	mary = Xyz(5, 0.2, 0.1, 1.5) + Xyz(7, 0.2, 0.1, 1.5);
	trig.sendReply(\xyz, [yoshi, mary]);
} +> \yoshimaryxyzof;
\xyz >>> { | n, msg |
	// msg[3..].postln;
	of1.sendMsg('/xyz', *msg[3..])
	of2.sendMsg('/xyz', *msg[3..])
};
