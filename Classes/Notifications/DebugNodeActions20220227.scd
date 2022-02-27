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
		.items_([\1, \2, \3])
		.enterKeyAction_({ | me |

			me.addNodeActions(
				{ WhiteNoise.ar(0.1).dup } +> me.item.postln,
				{ me.background_(Color.red) },
				{ me.background_(Color.blue) }
			)
		})
	)
})
//:
currentEnvironment['1'].isPlaying;
//:
\test +>? { WhiteNoise.ar(0.1).dup }
//:
currentEnvironment;
//:
{ WhiteNoise.ar(0.1).dup } +> \test;
