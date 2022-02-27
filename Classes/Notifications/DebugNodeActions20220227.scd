/* 27 Feb 2022 08:48

*/
{ WhiteNoise.ar(0.1).dup } +> \test;
\test.stop(20);
//:
({ WhiteNoise.ar(0.1).dup } +> \test).postln;
//:
\test.addNodeActions(
	{ WhiteNoise.ar(0.1).dup } +> \test,
	{ | ... args | postln(args.asString + "started"); },
	{ | ... args | postln(args.asString + "ended"); },
);
//:
\test.stop;
//:
\x.window({|w|
	w.layout = VLayout(
		ListView()
		.items_(Buffer.all)
		.enterKeyAction_({ | me |
			me.addNodeActions(
				Buffer.all[me.value] toggle: { SinOsc.ar(400.rrand(1000), 0, 0.1).dup },
				{ me.items_(
					Buffer.all.collect({| b |
						if (b.playingp) { "*" + b + "*" }{ b }
					})
				)
				},
				{ me.items_(
					Buffer.all.collect({| b |
						if (b.playingp) { "*" + b + "*" }{ b }
					})
				)
				}
			)
		})
	)
})
//:
\x.window({|w|
	w.layout = VLayout(
		ListView()
		.items_(["blah"])
		.selectionMode_(\multi)
		.colors_([Color.red])
	)
})
//:
currentEnvironment['1'].isPlaying;
//:
\test +>? { WhiteNoise.ar(0.1).dup };
//:
\test.playingp;
//:
currentEnvironment;
//:
{ WhiteNoise.ar(0.1).dup } +> \test;
//:
Buffer.all;
wrap