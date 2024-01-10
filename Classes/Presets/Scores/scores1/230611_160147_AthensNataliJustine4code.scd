// Exporting 2 snippets on 230828_215507
//:--[88691.226020208]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(30, 6000).lag(0.1), 0.1) * 20
} +> \\justine;
" ]
//:--[88806.290328125]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(30, 6000).lag(0.1), 0.1) * 20
} +> \\justine;
" ]

//the end

