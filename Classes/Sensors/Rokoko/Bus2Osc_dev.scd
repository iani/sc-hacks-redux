//:
SynthDef("help-SendTrig",{
	var trig, src;
	// src = SinOsc.kr([1, 1, 1], 0, 0.1);
	src = [In.kr(0), In.kr(1), In.kr(2)];
	trig = Impulse.kr(2);
    // SendTrig.kr(Impulse.kr(2), 0, src);
	SendReply.kr(trig, '/reply123', src, -1);
}).add;

// register to receive this message
// o = OSCFunc({ arg msg, time;
//     [time, msg].postln;
// },'/tr', s.addr);
//:

Synth("help-SendTrig");
//:
Bus(\control, 0, 1).set(0.4);
Bus(\control, 1, 1).set(0.5);
Bus(\control, 2, 1).set(0.6);
//:
a = nil;
'/reply' >>> { | n, msg | a = a add: msg[3..].postln; };
//:
a;
//:
Bus2Osc.named(\x);
Bus2Osc.named;

Rokoko.atDate;
Rokoko.named;

OscGroups.sendAddress;
OscGroups.enable;
//:
b = Bus2Osc.named(\x, buses: (0..2), sendaddress: NetAddr.localAddr);
b.play;
OscMonitor.gui;

//:
NetAddr.localAddr.sendMsg(\testing123);
\testing123.addAction({ (degree: (0..10).choose).play }, \blah);
//:
//:
b = Bus2Osc.named(\x, buses: (0..2), sendaddress: NetAddr.localAddr);
b.play;
OscMonitor.gui;
