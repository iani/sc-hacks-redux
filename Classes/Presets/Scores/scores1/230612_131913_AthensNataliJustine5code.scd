// Exporting 4 snippets on 230828_220010
//:--[69735.955030625]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = PinkNoise.ar(0.4) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(300, 3000).lag(0.5), 0.05) * 10
} +> \\justine;
" ]
//:--[69738.328914958]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = SinOsc.ar(\\z2.sr.exp(30, 6000).lag(0.1), 0, 0.15).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;

" ]
//:--[69805.392857333]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = SinOsc.ar(\\z2.sr.exp(300, 600).lag(0.1), 0, 0.5).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;

" ]
//:--[69855.723599]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = PinkNoise.ar(0.4) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(30, 3000).lag(0.1), 0.1) * 20
} +> \\justine;
" ]

//the end

