// 日 22  6 2025 01:00
Animation.gui;
AnimationController.player;
AnimationController.databus;
AnimationController.modbus.set(100);

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