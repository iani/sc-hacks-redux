/* 11 Aug 2023 20:48

*/

+ GraphBuilder {
	*makeFadeEnv { arg fadeTime = (0.02);
		var dt = NamedControl.kr(\fadeTime, fadeTime);
		var gate = NamedControl.kr(\gate, 1.0);
		var startVal = (dt <= 0);
		^EnvGen.kr(Env.new([startVal, 1, 0], #[1, 1], \lin, 1), gate, 1.0, 0.0, dt, 2)
	}

	*wrapOut { arg name, func, rates, prependArgs, outClass=\Out, fadeTime;
		^SynthDef.new(name, { arg out=0;
			var result, rate, env;
			result = SynthDef.wrap(func, rates, prependArgs).asUGenInput;
			rate = result.rate;
			Library.put(\sdefrates, name.asSymbol, rate); // we need this to release/free control synths!
			if(rate.isNil or: { rate === \scalar }) {
				result
			}{
				result = this.makeFadeEnv(fadeTime) * result;
				outClass = outClass.asClass;
				outClass.replaceZeroesWithSilence(result.asArray);
				outClass.multiNewList([rate, out]++result)
			}
		})

	}
}