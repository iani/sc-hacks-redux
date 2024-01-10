// Exporting 27 snippets on 230828_215526
//:--[73.53527125]
[ '/code', "//:
{
	var env, src;
	env = Env.perc(0.9, 0.9);
	src = PinkNoise.ar(0.3) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x1.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	Resonz.ar(src, \\z2.sr.exp(30, 6000).lag(0.1), 0.1) * 20
} +> \\natali;
" ]
//:--[138.108353625]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = SinOsc.ar(\\z4.sr.exp(300, 600).lag(0.1)).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;
" ]
//:--[261.994656709]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.09);
	src = SinOsc.ar(\\z4.sr.exp(300, 600).lag(0.1)).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[288.019296375]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.09);
	src = SinOsc.ar(\\z4.sr.exp(100, 200).lag(0.1)).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[314.079325584]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.09);
	src = SinOsc.ar(\\z4.sr.exp(100, 200).lag(0.1), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[338.158465]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.09);
	src = SinOsc.ar(\\z4.sr.exp(1000, 1200).lag(0.1), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[365.58241025]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.009, 0.09);
	src = SinOsc.ar(\\z4.sr.exp(1000, 1200).lag(0.1), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[378.069538834]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.009, 0.01);
	src = SinOsc.ar(\\z4.sr.exp(1000, 1200).lag(0.1), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[386.687070834]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.009, 0.01);
	src = SinOsc.ar(\\z4.sr.exp(500, 1200).lag(0.1), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[419.32960125]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.009, 0.01);
	src = SinOsc.ar(\\z4.sr.exp(500, 1200).lag(0.1).round(100), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[456.12663]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.01);
	src = SinOsc.ar(\\z4.sr.exp(500, 1200).lag(0.1).round(100), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[480.005556375]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.01);
	src = SinOsc.ar(\\z4.sr.exp(500, 1500).lag(0.1).round(100), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[488.228952334]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.01);
	src = SinOsc.ar(\\z4.sr.exp(500, 2500).lag(0.1).round(100), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[495.700616709]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.01);
	src = SinOsc.ar(\\z4.sr.exp(300, 2500).lag(0.1).round(100), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[513.184355625]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.01);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(100), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[524.413475084]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.9, 0.1);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(100), 0, 0.1).dup * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[552.376405209]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.9, 0.1);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(100) * [1, 1.2], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[564.639943209]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.1);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(100) * [1, 1.2], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[578.46233675]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(100) * [1, 1.2], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[603.746578959]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(100) * [1, 0.8], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[610.665887292]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(100) * [1, 0.7], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[657.287737459]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(50) * [1, 0.7], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[679.212660875]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(50) * [1, 0.97], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[689.102757125]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(50) * [1, 0.99], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[698.709283917]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(50) * [1, 0.999], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[721.931443125]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(900, 2500).lag(0.1).round(50) * [1, 1.999], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]
//:--[741.597025792]
[ '/code', "//:Variant with sine
{
	var env, src;
	env = Env.perc(0.09, 0.91);
	src = SinOsc.ar(\\z4.sr.exp(400, 1500).lag(0.1).round(50) * [1, 1.999], 0, 0.1) * env.kr(gate:
		Amplitude.kr(Slope.kr(\\x3.sr.lag(0.1))) > 0.9, doneAction: 0
	);
	src;
} +> \\justine;

" ]

//the end

