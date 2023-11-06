ServerBoot add: {
	{
		"adding output copy synth".postln;
	{
		Out.ar(10,
			In.ar(8)
		);
	}.play(nil.asTarget, addAction: 'addAfter'
	)
	}.defer(1.0)
}