/* 26 Sep 2023 11:47
Forward xamp.
*/

Forward {
	*forward {
		{
			var oscgroups;
			// oscgroups = OscGroups.sendAddress;
			{
				var trig, yoshi, mary;
				trig = Impulse.kr(20);
				yoshi = Xyz(1, 0.2, 0.1, 1.5) + Xyz(2, 0.2, 0.1, 1.5);
				mary = Xyz(5, 0.2, 0.1, 1.5) + Xyz(7, 0.2, 0.1, 1.5);
				trig.sendReply(\xyzforward, [yoshi, mary]);
			} +> \yoshimaryxyzof;
			\xyzforward >>> { | n, msg |
				// msg[3..].postln;
				// of1.sendMsg('/xyz', *msg[3..])
				// of2.sendMsg('/xyz', *msg[3..])
				// "Sending xyz".postln;

				// OscGroups.sendAddress.sendMsg('/test', 1, 2, 3);
				OscGroups.sendAddress.sendMsg('/xyz', *msg[3..]);
			};
		}.defer(5);
	}
}