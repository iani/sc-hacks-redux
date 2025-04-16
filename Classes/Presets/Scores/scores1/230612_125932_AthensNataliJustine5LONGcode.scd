// Exporting 20 snippets on 230828_215658
//:--[68554.985373541]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = PinkNoise.ar(0.4) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(30, 6000).lag(0.1), 0.1) * 20
} +> \\justine;
" ]
//:--[68628.680967125]
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
//:--[68714.716269041]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.09);
	src = SinOsc.ar(\\z2.sr.exp(100, 200).lag(0.1), 0, 0.5).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;

" ]
//:--[68732.490709375]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.09);
	src = SinOsc.ar(\\z2.sr.exp(1000, 1200).lag(0.1), 0, 0.5).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;

" ]
//:--[68748.945918625]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.009, 0.01);
	src = SinOsc.ar(\\z2.sr.exp(500, 1200).lag(0.1).round(100), 0, 0.5).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;

" ]
//:--[68797.720271541]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z2.sr.exp(400, 1500).lag(0.1).round(50) * [1, 1.999], 0, 0.2) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;

" ]
//:--[68834.428329958]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.09);
	src = SinOsc.ar(\\z2.sr.exp(300, 600).lag(0.1), 0, 0.3).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;

" ]
//:--[68860.342544625]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = SinOsc.ar(\\z2.sr.exp(300, 600).lag(0.1), 0, 0.3).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;
" ]
//:--[68889.157709833]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z2.sr.exp(400, 1500).lag(0.1).round(50) * [1, 1.999], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;

" ]
//:--[68915.43960375]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z2.sr.exp(1400, 2500).lag(0.1).round(50) * [1, 1.01], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;
" ]
//:--[68970.77599875]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z2.sr.exp((400, 500).lag(0.1).round(50) * [1, 1.01], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;
" ]
//:--[68988.150167041]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z2.sr.exp((400, 500).lag(0.1).round(50) * [1, 1.01], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;
" ]
//:--[69022.551024166]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z2.sr.exp(400, 500).lag(0.1).round(50) * [1, 1.01], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.l lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;

" ]
//:--[69040.936546416]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z2.sr.exp(400, 500).lag(0.1).round(50) * [1, 1.01], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;

" ]
//:--[69090.566652583]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z2.sr.exp(200, 300).lag(0.1).round(50) * [1, 1.01], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\natali;

" ]
//:--[69192.311269125]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = PinkNoise.ar(0.4) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(30, 6000).lag(0.1), 0.5) * 10
} +> \\justine;
" ]
//:--[69199.702560875]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = PinkNoise.ar(0.4) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(30, 3000).lag(0.1), 0.5) * 10
} +> \\justine;
" ]
//:--[69203.668618458]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = PinkNoise.ar(0.4) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(300, 3000).lag(0.1), 0.5) * 10
} +> \\justine;
" ]
//:--[69211.531537458]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = PinkNoise.ar(0.4) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z4.sr.exp(300, 3000).lag(0.5), 0.5) * 10
} +> \\justine;
" ]
//:--[69241.07578375]
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

//the end

