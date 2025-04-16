// Exporting 15 snippets on 230828_220117
//:--[2922.984721083]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(0.5)) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[2943.479394791]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		\\freq.br(300) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(0.5)) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[2949.638942583]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(0.5)) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3021.714996375]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		) + 500).lag(0.5)) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3067.215767208]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(0.5))) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3086.960808708]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(0.5)) + 1000 ) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3102.370703625]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(0.5)) + 500 ) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3114.571753416]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(0.5)) + 200 ) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3140.043751333]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(1.5)) + 200 ) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3191.419078916]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(1.5)) + 100 ) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3325.367585083]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(1.5).reciprocal) + 100 ) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3477.291395708]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(1.51)
			 + \\y2.sr.lag(1.51)
			 + \\z2.sr.lag(1.51)
		)).lag(1.5).reciprocal) + 100 ) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3503.186869083]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(1.5).reciprocal) + 100 ) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 2.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3551.779949875]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(1.5).reciprocal) + 100 ) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 2.9, doneAction: 0
	);
} +> \\jun1;
" ]
//:--[3562.370419958]
[ '/code', "//:KEEP
{
	var env, src;
	env = Env.perc(0.15, 0.05);
	src =  SinOsc.ar(
		(\\freq.br(200) * Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.51)
			 + \\y2.sr.lag(0.51)
			 + \\z2.sr.lag(0.51)
		)).lag(1.5).reciprocal) + 100 ) *
	 env.kr(gate:
		 Amplitude.kr(Slope.kr(
			 \\x2.sr.lag(0.01)
 			 + \\y2.sr.lag(0.01)
			 + \\z2.sr.lag(0.01)
		 )) > 1.9, doneAction: 0
	);
} +> \\jun1;
" ]

//the end

