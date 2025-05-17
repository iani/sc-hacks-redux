// 土 17  5 2025 08:30 - add fader to make a function releasable
//
+ UGen {
	f { | fadeTime = 0.02 | ^this * GraphBuilder.makeFadeEnv(fadeTime) }
}

+ Array {
	f { | fadeTime = 0.02 | ^this * GraphBuilder.makeFadeEnv(fadeTime) }
}

/*
	ff { | out = 0, fadeTime = 0.02 |
		^{
			Out.perform(
				(this.first.rate===\audio).if {\ar} {\kr},
				out,
				this.f(fadeTime)
			)
		}
	}
*/