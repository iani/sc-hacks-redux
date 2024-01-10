// Exporting 2 snippets on 230828_220229
//:--[29804.74364875]
[ '/code', "//:
{
	Resonz.ar(PinkNoise.ar(0.5), XLine.kr(200, 500, 120), 0.5) * (\\z1.sr < 0.5).lag(0.2)
} +> \\jun1;

{
	Resonz.ar(PinkNoise.ar(0.5), XLine.kr(500, 200, 120), 0.5) * (\\z2.sr < 0.5).lag(0.2)
} +> \\jun2;
" ]
//:--[29806.543052208]
[ '/code', "//:
{  SinOsc.ar(XLine.kr(300, 700, 120) * [1.0, 5/4], 0, 0.05) * (\\x3.sr < 0.5).lag(0.3) } +> \\asayo1;
{  SinOsc.ar(XLine.kr(5000, 200, 120) * [1.0, 6/5], 0, 0.05) * (\\x4.sr < 0.5).lag(0.3) } +> \\asayo2;
" ]

//the end

