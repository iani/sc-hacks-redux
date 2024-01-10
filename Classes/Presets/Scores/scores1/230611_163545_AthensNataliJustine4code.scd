// Exporting 6 snippets on 230828_215544
//:--[1002.909762167]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(400, 1500).lag(0.1).round(50) * [1, 1.999], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;
" ]
//:--[1135.97475475]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(400, 1500).lag(0.1).round(150) * [1, 1.999], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;
" ]
//:--[1158.827055417]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(400, 2500).lag(0.1).round(150) * [1, 1.999], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;
" ]
//:--[1188.993048209]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(1400, 2500).lag(0.1).round(50) * [1, 1.999], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;
" ]
//:--[1198.814643]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(1400, 2500).lag(0.1).round(50) * [1, 1.2], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;
" ]
//:--[1206.04656475]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(1400, 2500).lag(0.1).round(50) * [1, 1.01], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;
" ]

//the end

