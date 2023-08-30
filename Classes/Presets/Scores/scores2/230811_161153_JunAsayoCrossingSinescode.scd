// Exporting 4 snippets on 230828_222204
//:--[79759.607829]
[ '/code', "//:source jun 1
{
	SinOsc.ar(\\freq.br(200)).dup.fader(amp: 0.05) * (\\z1.sr > 0.5).lag(1);
} +> \\jun;
" ]
//:--[79762.17208575]
[ '/code', "//:source asayo 1
{
	SinOsc.ar(\\freq.br(200 * 8)).dup.fader(amp:0.05) * (\\z4.sr > 0.5).lag(1);
} +> \\asayo;
" ]
//:--[79765.188326375]
[ '/code', "{ XLine.kr(200, 200 * 8, 180) } @>.jun \\freq;" ]
//:--[79766.841355417]
[ '/code', "{ XLine.kr(200 * 8, 200, 180) } @>.asayo \\freq;" ]

//the end

