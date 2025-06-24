// 日 22  6 2025 01:00
//:
OscMonitor.gui;
//:

Animation.gui;
//:
c;
c.player;
c addListener: Animation.localListener;

//:
c.trace(\msg);
c.untrace;
//:
c addListener: Animation.localListener;
c removeListener: Animation.localListener;
//:
c addListener: NetAddr("", );
c removeListener: NetAddr("", );
//:
Animation.localListener.addNotifier(c, \msg, { | ... args | args.postln });
//:
Animation.localListener.removeNotifier(c, \msg, { | ... args | args.postln });
//:
b = LiveMocapPlayer(\default).controller;
b.player.activate;
b.player.deactivate;
//:

//:
b.player.activate;
//:
b.trace(\msg);
b.untrace(\msg);
b.player addListener: Animation.localListener;
b.player removeListener: Animation.localListener;


//:
b.player addListener: NetAddr("195.251.170.89", 22245);
b.player removeListener: NetAddr("195.251.170.89", 22245);
//:
\sound.addNotifier(b.player, \msg, { | n, msg |
	postln("the message is:" + msg);
});
//:
\sound.removeNotifier(b.player, \msg);
//:
x = { | freq = 400 | SinOsc.ar(freq, 0, 0.1).dup }.play;
//:
\sound.addNotifier(b.player, \msg, { | n, msg |
	var prm;
	prm = msg[71];
	postln("the param is:" + prm);
	x.set(\freq, prm.linlin(-1.0, 1.0, 400, 800));
});
//:
x = { | freq = 400, amp = 0.1 | SinOsc.ar(freq, 0, amp).dup }.play;
//:
\sound.addNotifier(b.player, \msg, { | n, msg |
	var prm, prm2;
	prm = msg[71];
	prm2 = msg[72];
	postln("the param is:" + prm);
	x.set(\freq, prm.linlin(-1.0, 1.0, 400, 800), \amp,
		prm2.linlin(-1.0, 1.0, 0.01, 0.4));
});
//:
x = { | freq = 400, amp = 0.1 | SinOsc.ar(freq, 0, amp).dup }.play;
y = { | freq = 400, amp = 0.1 | SinOsc.ar(freq, 0, amp).dup }.play;
//:
x.free;
x = { | freq = 400, amp = 0.1, rq = 0.05 |
	Ringz.ar(WhiteNoise.ar(amp), freq, rq).dup;
}.play;
//:
x.free;
x = { | freq = 400, amp = 0.1, rq = 0.05, vib = 5 |
	var mod;
	mod = SinOsc.kr(vib).range(0.1,0.5);
	Ringz.ar(WhiteNoise.ar(amp) * mod, freq, rq).dup;
}.play;
//:
y.free;
y = { | freq = 400, amp = 0.1, rq = 0.05, vib = 5 |
	var mod;
	mod = SinOsc.kr(vib).range(0.1,0.5);
	Ringz.ar(WhiteNoise.ar(amp) * mod, freq, rq).dup;
}.play;
//:
y.free;
y = { | freq = 400, amp = 0.1, rq = 0.05 |
	Ringz.ar(WhiteNoise.ar(amp), freq, rq).dup;
}.play;
//:
y.free;
//:
\sound.removeNotifier(b.player, \msg);
//:
\sound.addNotifier(b.player, \msg, { | n, msg |
	var prm, prm2, prm3, prm4;
	prm = msg[58];
	prm2 = msg[57];
	prm3 = msg[89];
	prm4 = msg[81];

	// postln("the param is:" + prm);
	x.set(\freq, prm.linlin(-1.0, 1.0, 100, 300),
		\amp, prm2.linlin(-1.0, 1.0, 0.01, 0.4));
	y.set(\freq, prm3.linlin(-1.0, 1.0, 200, 600),
		\amp, prm4.linlin(-1.0, 1.0, 0.01, 0.4));
});
//:
//:
\sound.addNotifier(b.player, \msg, { | n, msg |
	var prm, prm2, prm3, prm4, prm5, prm6;
	prm = msg[58];
	prm2 = msg[57];
	prm3 = msg[89];
	prm4 = msg[81];
	prm5 = msg[100];
	prm6 = msg[101];
	prm7 = msg[102];
	prm8 =
	// postln("the param is:" + prm);
	x.set(\freq, prm.linlin(-1.0, 1.0, 100, 300),
		\amp, prm2.linlin(-1.0, 1.0, 0.01, 0.4),
		\rq, prm5.linlin(-1.0, 1.0, 0.005, 0.01);
	);
	y.set(\freq, prm3.linlin(-1.0, 1.0, 200, 600),
		\amp, prm4.linlin(-1.0, 1.0, 0.01, 0.4),
		\rq, prm5.linlin(-1.0, 1.0, 0.005, 0.01));
});
//:
x = { | freq = 400, amp = 0.1, vibrate = 5 |
	SinOsc.ar(freq * LFTri.kr(vibrate).range(1, 1.1), 0, amp).dup
}.play;
y = { | freq = 400, amp = 0.1 |
	SinOsc.ar(freq, 0, amp).dup
}.play;
//:
x = { | freq = 400, amp = 0.1, vibrate = 5 |
	WhiteNoise.ar(amp).dup
}.play;
y = { | freq = 400, amp = 0.1 |
	PinkNoise.ar(amp).dup
}.play;
//:
x = { | freq = 400, amp1 = 0.5, amp2 = 0.5, vib1 = 5, vib2 = 5 |
	amp1 = amp1 * LFSaw.kr(vib1).range(0.01, 0.1);
	amp2 = amp2 * LFSaw.kr(vib2).range(0.01, 0.1);
	[WhiteNoise.ar(amp1), WhiteNoise.ar(amp2)]
}.play;
y = { | freq = 400, amp1 = 0.1, amp2 = 0.1, vib1 = 5, vib2 = 5 |
	amp1 = amp1 * LFSaw.kr(vib1).range(0.01, 0.1);
	amp2 = amp2 * LFSaw.kr(vib2).range(0.01, 0.1);
	[PinkNoise.ar(amp1) + PinkNoise.ar(amp2)]
}.play;
//:
RokokoJoints().jointVarIndex(\head, \x);
RokokoJoints().jointVarIndex(\rightLeg, \x);
RokokoJoints().jointVarIndex(\leftLeg, \x);

RokokoJoints().jointVarIndex(\leftHand, \x);
RokokoJoints().jointVarIndex(\rightHand, \x);
//:
\sound.addNotifier(b.player, \msg, { | n, msg |
	var prm, prm2, prm3, prm4, prm5, prm6, prm7, prm8;
	prm = msg[56];
	prm2 = msg[57];
	prm3 = msg[58];
	prm4 = msg[59];
	prm5 = msg[133];
	prm6 = msg[134];
	prm7 = msg[84];
	prm8 = msg[85];

	// postln("the param is:" + prm);
	x.set(\amp1, prm.linlin(-1.0, 1.0, 0.0, 0.1),
		\amp2, prm2.linlin(-1.0, 1.0, 0.0, 0.1),
		\vib1, prm3.linlin(1, 15),
		\vib2, prm4.linlin(1, 15),
	);
	y.set(\amp1, prm5.linlin(-1.0, 1.0, 0.0, 0.1),
		\amp2, prm6.linlin(-1.0, 1.0, 0.0, 0.1),
		\vib1, prm7.linlin(1, 15),
		\vib2, prm8.linlin(1, 15),
	);
});
//:
\sound.addNotifier(b.player, \msg, { | n, msg |
	var prm, prm2, prm3, prm4, prm5;
	prm = msg[71];
	prm2 = msg[72];
	prm3 = msg[92];
	prm4 = msg[93];
	prm5 = msg[95];

	// postln("the param is:" + prm);
	x.set(\freq, prm.linlin(-1.0, 1.0, 400, 800),
		\amp, prm2.linlin(-1.0, 1.0, 0.01, 0.4),
		\vibrate, prm5.linlin(1, 15),
	);
	y.set(\freq, prm3.linlin(-1.0, 1.0, 200, 600),
		\amp, prm2.linlin(-1.0, 1.0, 0.01, 0.4));
});
//:
\sound.addNotifier(b.player, \msg, { | n, msg |
	var prm, prm2, prm3, prm4, prm5;
	prm = msg[71];
	prm2 = msg[72];
	prm3 = msg[92];
	prm4 = msg[103];
	prm5 = msg[104];

	// postln("the param is:" + prm);
	x.set(\freq, prm.linlin(-1.0, 1.0, 400, 800),
		\amp, prm2.linlin(-1.0, 1.0, 0.01, 0.4),
		\vibrate, prm5.linlin(1, 15),
	);
	y.set(\freq, prm3.linlin(-1.0, 1.0, 200, 600),
		\amp, prm4.linlin(-1.0, 1.0, 0.01, 0.4));
});

//:
\sound.addNotifier(b.player, \msg, { | n, msg |
	var prm, prm2, prm3, prm4, prm5;
	prm = msg[151];
	prm2 = msg[152];
	prm3 = msg[92];
	prm4 = msg[103];
	prm5 = msg[104];

	// postln("the param is:" + prm);
	x.set(\freq, prm.linlin(-1.0, 1.0, 400, 800),
		\amp, prm2.linlin(-1.0, 1.0, 0.01, 0.4),
		\vibrate, prm5.linlin(1, 15),
	);
	y.set(\freq, prm3.linlin(-1.0, 1.0, 200, 600),
		\amp, prm4.linlin(-1.0, 1.0, 0.01, 0.4));
});

//:
x.free;
y.free;

//:
b;
b.player.postOsc;
b.player.unpostOsc;
//: stop all controls and remove all distortion!!!!!!!!!!!
c.stopCtls;
//:Test global movement (161 points sine)
c.addCtl(\hip, \x, { SinOsc.kr(0.1) ! 161});
//:global movement - out of phase
c.addCtl(\hip, \x, { { | i | SinOsc.kr(i.linlin(0, 160, 0.1, 0.2)) } ! 161});
//:test hip only - 7 dims
c.addCtl(\hip, \x, { SinOsc.kr(0.1) ! 7});
//:x has no effect?
c.addCtl(\hip, \x, { SinOsc.kr(0.1) ! 1});
//:y moves up and down.
c.addCtl(\hip, \y, { SinOsc.kr(0.1) ! 1});
//:z?
c.addCtl(\hip, \z, { SinOsc.kr(0.1) ! 1});
//:qx
c.addCtl(\hip, \qx, { SinOsc.kr(0.1) ! 1});
//:qy
c.addCtl(\hip, \qy, { SinOsc.kr(0.1) ! 1});
//:qz
c.addCtl(\hip, \qz, { SinOsc.kr(0.1) ! 1});
//:qw
c.addCtl(\hip, \x, { SinOsc.kr(0.1) ! 1});
//:
[ hip, spine, chest, neck, head,
	leftShoulder, leftUpperArm, leftLowerArm, leftHand,
	rightShoulder, rightUpperArm, rightLowerArm, rightHand,
	leftUpLeg, leftLeg, leftFoot, leftToe, leftToeEnd,
	rightUpLeg, rightLeg, rightFoot, rightToe, rightToeEnd
]
//:KEEP
c.addCtl(\chest, \qx, { SinOsc.kr(2).range(-0.1, 0.1) ! 1});
c.addCtl(\chest, \qy, { SinOsc.kr(0.5).range(-0.1, 0.1) ! 1});
//:SOUND for the movement
x = { SinOsc.ar(SinOsc.kr(2).range(400, 500)) }.play;
//:Test left and right arm;
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
c.addCtl(\leftShoulder, \x, { SinOsc.kr(0.1) });
//:
RokokoJoints.joints;
[ hip, spine, chest, neck, head,
	leftShoulder, leftUpperArm, leftLowerArm, leftHand,
	rightShoulder, rightUpperArm, rightLowerArm, rightHand,
	leftUpLeg, leftLeg, leftFoot, leftToe, leftToeEnd,
	rightUpLeg, rightLeg, rightFoot, rightToe, rightToeEnd
]
//:
c.addCtl(\chest, \qy, { SinOsc.kr(0.8).range(-0.51, 0.08).dup(1) });
//:
c.addCtl(\chest, \qy, { SinOsc.kr(0.1).range(-0.1, 0.08).dup(1) });
c.addCtl(\chest, \qy, { SinOsc.kr(0.1).range(-0.1, 0.08).dup(1) });
//:
c.stopCtl(\chest, \qy);
//:
c.addCtl(\hip, \x, { \ctl.kr(0) ! 161});
//:
c.addCtl(\hip, \x, { \ctl.kr(0.01) ! 161});
//:
c.addCtl(\hip, \x, { SinOsc.kr(0.1) ! 161});
//:
c.addCtl(\hip, \x, { \ctl.kr(0.01) ! 161});
//:
c.addCtl(\hip, \x, { SinOsc.kr(Array.rand(161, 1, 2.1)).range(0, 0.9) });
//:t
c.stopCtls;
//:
//:
c.addCtl(\hip, \x, { \ctl.kr(1) ! 161});
//:
RokokoJoints.joints;
//:
x = {
	Out.kr(c.modbus.index, { SinOsc.kr(10.reciprocal).range(-0.5, 0.5) } ! 100);
}.play;

//:
x.free;
//:
x = {
	Out.kr(c.modbus.index, SinOsc.kr(Array.rand(161, 0.1, 0.15)).range(-0.5, 0.5));
}.play;
//:
f = { SinOsc.kr(0.1).range(-0.5, 0.5) };
x = { | out = 0 |
	Out.kr(out, f.value);
}.play(args: [out: c.modbus.index]);
//:
f = { SinOsc.kr(0.1).range(-0.5, 0.5) };
x = { | out = 0 |
	Out.kr(out, f ! 100);
}.play(args: [out: c.modbus.index]);
//:
x = {  LFNoise1.kr(1).range(-0.5, 0.9) ! 100 }.play(outbus: c.modbus.index)
//:
x = {  \ctl.kr(10.1) ! 100 }.play(outbus: c.modbus.index)
x = {  \ctl.kr(0.0) ! 100 }.play(outbus: c.modbus.index)
//:
x.set(\ctl, 1);
//:
r = fork {
	loop {
		x.set(\ctl, (0 rrand: 1.0));
		0.1.wait;
	}
};
//
r.stop;
//:
x = { SinOsc.kr(1).range(0.009, 0.9) ! 161 }.play(outbus: c.modbus.index)
//:
x.free;
//:
c.modbus.setn(0 ! 161);
//:
c.inspect;

AnimationController.databus;
AnimationController.modbus.set(100);

a = AnimationController(\default);
a.dataJoints;
AnimationController.verbose = true;
//:
AnimationController.player.stop;
//:First experiments:
a = Bus(\control, 200, 1);
a.set(100);
//:
r = fork {
	loop {
		a.set(-100.rrand(100).postln);
		1.wait;
	}
}
//:
j = RokokoJoints();
j enumerate: { | i, j, v | [i, j, v].postln; };