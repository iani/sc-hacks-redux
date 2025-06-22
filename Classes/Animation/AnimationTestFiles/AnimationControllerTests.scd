// 日 22  6 2025 01:00
Animation.gui;
c = AnimationController(\default);
c.player;
c.modIndex(\chest, \x);
c.modJoint(\chest, \x);
p.setJoint(\chest, \x, 10.5);
c.modbus;
//:
c.addCtl(\chest, \qy, { SinOsc.kr(0.8).range(-0.51, 0.08).dup(1) });
//:
c.addCtl(\hip, \x, { SinOsc.kr(Array.rand(161, 1, 2.1)).range(0, 0.9) });
//:t
c.stopCtls;
//:
c.addCtl(\hip, \x, { \ctl.kr(0) ! 161});
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
x = {
	Out.kr(c.modbus.index, { 0 } ! 161);
}.play;
//:
x = {  LFNoise1.kr(1).range(-0.5, 0.9) ! 100 }.play(outbus: c.modbus.index)
//:
x = {  \ctl.kr(10.1) ! 100 }.play(outbus: c.modbus.index)
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