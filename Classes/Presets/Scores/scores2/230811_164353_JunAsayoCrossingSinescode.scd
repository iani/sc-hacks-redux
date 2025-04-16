// Exporting 2 snippets on 230828_222246
//:--[81676.798098]
[ '/code', "//:source jun 1
{
	SinOsc.ar(\\freq.br(200)).dup.fader(amp: 0.2) * (\\x1.sr > 0.5).lag(1);
} +> \\jun;
//source asayo 1
{
	SinOsc.ar(\\freq.br(200 * 8)).dup.fader(amp:0.2) * (\\z4.sr > 0.5).lag(1);
} +> \\asayo;
" ]
//:--[81756.788328292]
[ '/code', "//:glissando jun
{ XLine.kr(200, 200 * 8, 180) } @>.jun \\freq;
{ XLine.kr(200 * 8, 200, 180) } @>.asayo \\freq;
" ]

//the end

