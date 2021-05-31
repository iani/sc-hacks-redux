
+ GraphBuilder {
		*makeFadeEnv { arg fadeTime = (0.02);
		var dt = NamedControl.kr(\fadeTime, fadeTime);
		var gate = NamedControl.kr(\gate, 1.0);
		var startVal = (dt <= 0);

		^EnvGen.kr(
			// 31 May 2021 19:11 tweak envelope shape. [5, -5] is smoother:
			Env.new([startVal, 1, 0], #[1, 1], [5, -5], 1),
			// IZ: I find \lin too abrupt:
			// Env.new([startVal, 1, 0], #[1, 1], \lin, 1),  
			gate, 1.0, 0.0, dt, 2)

	}
}