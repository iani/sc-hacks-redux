// Exporting 3 snippets on 230828_220244
//:--[30598.640751833]
[ '/code', "//:
{
	Resonz.ar(PinkNoise.ar(0.5),
		LFNoise0.kr(14).range(1, 1.5)
		
		* XLine.kr(200, 500, 120), 0.5) * (\\z1.sr < 0.5).lag(0.2)
} +> \\jun1;

{
	Resonz.ar(PinkNoise.ar(0.5),
	LFNoise0.kr(14).range(1, 1.5) *
		XLine.kr(500, 200, 120), 0.5) * (\\z2.sr < 0.5).lag(0.2)
} +> \\jun2;

{  SinOsc.ar(XLine.kr(300, 700, 120)
	* LFNoise0.kr(14).range(1, 1.5)
	* [1.0, 5/4], 0, 0.05) * (\\x3.sr < 0.5).lag(0.3) } +> \\asayo1;
{  SinOsc.ar(XLine.kr(5000, 200, 120)
	* LFNoise0.kr(14).range(1, 1.5)
	* [1.0, 6/5], 0, 0.05) * (\\x4.sr < 0.5).lag(0.3) } +> \\asayo2;
" ]
//:--[30731.43391625]
[ '/code', "//:
{
	Resonz.ar(PinkNoise.ar(0.5),
		LFNoise0.kr(14).range(1, 1.5)
		
		* XLine.kr(200, 500, 120), 0.5) * (\\z1.sr < 0.5).lag(0.2)
} +> \\jun1;

{
	Resonz.ar(PinkNoise.ar(0.5),
	LFNoise0.kr(14).range(1, 1.5) *
		XLine.kr(500, 200, 120), 0.5) * (\\z2.sr < 0.5).lag(0.2)
} +> \\jun2;

{  SinOsc.ar(XLine.kr(300, 700, 120)
	* LFNoise0.kr(14).range(1, 1.5)
	* [1.0, 5/4], 0, 0.05) * (\\x3.sr < 0.5).lag(0.3) } +> \\asayo1;
{  SinOsc.ar(XLine.kr(5000, 200, 120)
	* LFNoise0.kr(14).range(1, 1.5)
	* [1.0, 6/5], 0, 0.05) * (\\x4.sr < 0.5).lag(0.3) } +> \\asayo2;
" ]
//:--[30869.294980375]
[ '/code', "//:
\\jun1 stop: 15;
\\jun2 stop: 15;
\\asayo1 stop: 15;
\\asayo2 stop: 15;" ]

//the end

