//金  9  5 2025 08:12 Test modifying stream of
//rokoko data received over oscgroups, and sending
//back the modified data to oscgroups.
//
//: Load data and store in ~rokoko
~rokoko = \session1.rdata; // load some data in advance
//: Start modifying data and sending
~rokoko.pollAddSumSend;
//: Modify entire bus array by the same UGen output
// pollAddSumSend takes any one bus value and adds italic
// to a single
//:---- preliminary sound check ----
\mod1.ndef({ SinOsc.ar(400, 0, 0.1) });
\mod1.play;
\mod1.stop;
//:---- Test sending to 1 bus -----
\mod1.ndef({
	var bus;
	bus = ~rokoko.buses.index;
	Out.kr(bus, LFSaw.kr(1))
});
