//:
CmdPeriod add: {
	var of;
	of = NetAddr(\"127.0.0.1\", 10000);
	{
		var trig, yoshi, mary, justine;
		trig = Impulse.kr(20);
		yoshi = Xyz(6, 0.2, 0.1, 1.5) + Xyz(7, 0.2, 0.1, 1.5);
		mary = Xyz(5, 0.2, 0.1, 1.5) + Xyz(8, 0.2, 0.1, 1.5);
		justine = Xyz(5, 0.2, 0.1, 1.5) + Xyz(6, 0.2, 0.1, 1.5);
		trig.sendReply(\\xyz, [yoshi, mary, justine]);
	} +> \\yoshimaryxyzof;
	\\xyz >>> { | n, msg |
		// msg[3..].postln;
		of.sendMsg('/xyz', *msg[3..]);
		of.sendMsg('/jun', msg[3]);
		of.sendMsg('/asayo', msg[4]);
		of.sendMsg('/justine', msg[5]);
		//postln(\"jun\" + msg[3] + \"asayo\" + msg[4]);
	};

	{ "RESTARTING GRAPHICS DATA".postln } ! 10;

}