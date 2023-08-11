/* 11 Aug 2023 20:48

*/

+ GraphBuilder {
	*makeFadeEnv { arg fadeTime = (0.02);
		var dt = NamedControl.kr(\fadeTime, fadeTime);
		var gate = NamedControl.kr(\gate, 1.0);
		var startVal = (dt <= 0);

		// postln("Debug GraphBuilder makeFadeEnv. fadeTime" + fadeTime + "startVal" + startVal);

		^EnvGen.kr(Env.new([startVal, 1, 0], #[1, 1], \lin, 1), gate, 1.0, 0.0, dt, 2)

	}
}