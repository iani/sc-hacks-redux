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

	// store rate to free control rate synths
	*wrapOut { arg name, func, rates, prependArgs, outClass=\Out, fadeTime;
		// postln("This is GraphBuilder:wrapOut. fadeTime is:" + fadeTime);
		^SynthDef.new(name, { arg i_out=0;
			var result, rate, env;
			result = SynthDef.wrap(func, rates, prependArgs).asUGenInput;
			rate = result.rate;
			Library.put(\sdefrates, name.asSymbol, rate); // we need this to release/free control synths!
			if(rate.isNil or: { rate === \scalar }) {
				// Out, SendTrig, [ ] etc. probably a 0.0
				// "GraphBuilder:wrapOut returning without makeFadeEnv!!!".postln;
				result
			} {
				// "GraphBuilder:wrapOut WILL NOW makeFadeEnv!!!".postln;
				// postln("GraphBuilder fadeTime is:" + fadeTime);
				// postln("GraphBuilder canReleaseSynth.not is:" + UGen.buildSynthDef.canReleaseSynth.not);

				if(fadeTime.notNil and: { UGen.buildSynthDef.canReleaseSynth.not }) {
					// postln("GraphBuilder next makeFadeEnv. fadeTime:" + fadeTime);
					result = this.makeFadeEnv(fadeTime) * result;
				};
				outClass = outClass.asClass;
				outClass.replaceZeroesWithSilence(result.asArray);
				outClass.multiNewList([rate, i_out]++result)
			}
		})
	}
}