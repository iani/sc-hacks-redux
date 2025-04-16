// Exporting 8 snippets on 230828_220140
//:--[4457.762610375]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.1);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z2.sr.exp(30, 6000).lag(0.1), 0.1) * 30
} +> \\jun;
" ]
//:--[4531.829974291]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.1);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 1.9, doneAction: 0
	);
	Resonz.ar(src, \\z2.sr.exp(30, 6000).lag(0.1), 0.1) * 30
} +> \\jun;
" ]
//:--[4687.371565083]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.1);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 2.9, doneAction: 0
	);
	Resonz.ar(src, \\z2.sr.exp(30, 6000).lag(0.1), 0.1) * 30
} +> \\jun;
" ]
//:--[4726.667445708]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.1, 0.1);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 2.9, doneAction: 0
	);
	Resonz.ar(src, \\z2.sr.exp(30, 6000).lag(0.1), 0.1) * 30
} +> \\jun;
" ]
//:--[4750.601505958]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.1, 0.1);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 1.9, doneAction: 0
	);
	Resonz.ar(src, \\z2.sr.exp(30, 6000).lag(0.1), 0.1) * 30
} +> \\jun;
" ]
//:--[4853.085684083]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.1, 0.1);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(
			\\x1.sr.lag(0.1)
			\\y1.sr.lag(0.1)
			\\z1.sr.lag(0.1)

		)) > 1.9, doneAction: 0
	);
	Resonz.ar(src, \\z2.sr.exp(30, 6000).lag(0.1), 0.1) * 30
} +> \\jun;
" ]
//:--[4859.842871458]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.1, 0.1);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(
			\\x1.sr.lag(0.1)
			+ \\y1.sr.lag(0.1)
			+ \\z1.sr.lag(0.1)

		)) > 1.9, doneAction: 0
	);
	Resonz.ar(src, \\z2.sr.exp(30, 6000).lag(0.1), 0.1) * 30
} +> \\jun;
" ]
//:--[4890.589280166]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.1, 0.1);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(
			\\x1.sr.lag(0.01)
			+ \\y1.sr.lag(0.01)
			+ \\z1.sr.lag(0.01)

		)) > 1.9, doneAction: 0
	);
	Resonz.ar(src, \\z2.sr.exp(30, 6000).lag(0.1), 0.1) * 30
} +> \\jun;
" ]

//the end

