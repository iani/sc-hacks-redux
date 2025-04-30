//: 火 29  4 2025 18:49
// Custom buffer playing functions, for "The Sun Rising".
// These methods will be further refined and developed in the
// next days.

// EXPERIMENTAL
// May be replaced by code inside TheSunRising class

+ Symbol {
	// get the buffer named after yourself,
	// and play it in environment variable named from prefix + yourself.
	tsrplay { | prefix = "tsr" |
		// { SinOsc.ar(500).dup }.pushPlayInEnvir((prefix++this).asSymbol);
		{ | t_trigger = 1 |
			var buf;
			buf = currentEnvironment[this];
			PlayBuf.ar(1, buf,
				BufRateScale.kr(buf) *
				\rate.kr((currentEnvironment[(this ++ "rate").asSymbol] ? 1)),
				trigger: t_trigger,
				startPos: \start,
				loop: \loop.kr(1),
				doneAction: 2
			).dup
		}.pushPlayInEnvir((prefix++this).asSymbol, currentEnvironment.name);
	}
}