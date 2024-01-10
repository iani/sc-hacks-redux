// Exporting 28 snippets on 230828_215559
//:--[2101.231973959]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	// Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2103.927761834]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.009);
	src = LFSaw.ar(\\z4.sr.exp(5, 10).lag(0.1), 0, 1.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x4.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(100, 400).lag(0.1), 0.1) * 20
} +> \\justine2;
" ]
//:--[2157.609467459]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(10, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	// Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2169.787032959]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(10, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2192.331795]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(\\z4.sr.exp(5, 10).lag(0.1), 0, 1.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x4.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(100, 400).lag(0.1), 0.1) * 20
} +> \\justine2;
" ]
//:--[2196.3137945]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(10, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2214.182692834]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(1, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2219.531354875]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(\\z4.sr.exp(1, 10).lag(0.1), 0, 1.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x4.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(100, 400).lag(0.1), 0.1) * 20
} +> \\justine2;
" ]
//:--[2246.817345334]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(2, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2248.714039625]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(\\z4.sr.exp(2, 10).lag(0.1), 0, 1.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x4.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(100, 400).lag(0.1), 0.1) * 20
} +> \\justine2;
" ]
//:--[2261.084875584]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(\\z4.sr.exp(3, 10).lag(0.1), 0, 1.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x4.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(100, 400).lag(0.1), 0.1) * 20
} +> \\justine2;
" ]
//:--[2263.38148]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(3, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2272.007119292]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(5, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2290.196030542]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(\\z4.sr.exp(3, 10).lag(0.1), 0, 1.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x4.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(400, 500).lag(0.1), 0.1) * 20
} +> \\justine2;
" ]
//:--[2300.955055959]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(2, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2302.958956875]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.9);
	src = LFSaw.ar(\\z4.sr.exp(2, 10).lag(0.1), 0, 1.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x4.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(400, 500).lag(0.1), 0.1) * 20
} +> \\justine2;
" ]
//:--[2313.384038917]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(\\z4.sr.exp(2, 10).lag(0.1), 0, 1.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x4.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(400, 500).lag(0.1), 0.1) * 20
} +> \\justine2;
" ]
//:--[2315.348273625]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(2, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2335.367594167]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(20, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2363.669293792]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(20, 0, 1.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x4.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(400, 500).lag(0.1), 0.1) * 20
} +> \\justine2;
" ]
//:--[2368.651750875]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(2, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2370.684388042]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(2, 0, 1.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x4.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(400, 500).lag(0.1), 0.1) * 20
} +> \\justine2;
" ]
//:--[2377.412851084]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(1, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 20
} +> \\justine1;
" ]
//:--[2396.6326935]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(1, 0, 1.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x4.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(1400, 1500).lag(0.1), 0.1) * 20
} +> \\justine2;
" ]
//:--[2422.36289875]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(1, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.1) * 50
} +> \\justine1;
" ]
//:--[2434.487049167]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(1, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.01) * 50
} +> \\justine1;
" ]
//:--[2465.2835955]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(1, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.01) * 150
} +> \\justine1;
" ]
//:--[2470.270100292]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = LFSaw.ar(1, 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z3.sr.exp(200, 400).lag(0.1), 0.01) * 250
} +> \\justine1;
" ]

//the end

