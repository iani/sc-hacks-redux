/* 17 Jul 2023 18:40

Some extra spect used by SynthTemplate, UGenFunc, SynthParams etc.
*/

+ Spec {
	*addSC_Hacks_Specs {
		var globaldict, owndict;
		globaldict = Spec.specs;
		owndict = (
			vol: ControlSpec(0, 10, 'amp', 0, 1, \vol),
			thresh: ControlSpec(0, 80, 'amp', 0, 0, \thresh)
		);
		owndict keysValuesDo: { | key, value |
			globaldict[key] = value;
		}
	}
}