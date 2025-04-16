// Exporting 1 snippets on 230828_214933
//:--[1554.868412042]
[ '/code', "//: NICE.
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z2.sr.exp(30, 6000).lag(0.1), 0.1) * 20
} +> \\natali;
" ]

//the end

