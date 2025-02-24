//onsetcode
//Forward XyzAmp of both hands and both dancers to Vicky
//:--[0] yoshi + mary
var of;
of = NetAddr("127.0.0.1", 10000);
{
	var trig, yoshi, mary;
	trig = Impulse.kr(20);
	yoshi = Xyz(1, 0.2, 0.1, 1.5) + Xyz(2, 0.2, 0.1, 1.5);
	mary = Xyz(5, 0.2, 0.1, 1.5) + Xyz(7, 0.2, 0.1, 1.5);
	trig.sendReply(\xyz, [yoshi, mary]);
} +> \yoshimaryxyzof;
\xyz >>> { | n, msg |
	// msg[3..].postln;
	of.sendMsg('/xyz', *msg[3..])
};