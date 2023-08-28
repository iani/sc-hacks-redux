// Exporting 4 snippets on 230828_222218
//:--[80240.446322542]
[ '/code', "//:source jun 1
{
	SinOsc.ar(\\freq.br(200)).dup.fader(amp: 0.05) * (\\x1.sr > 0.5).lag(1);
} +> \\jun;
//source asayo 1
{
	SinOsc.ar(\\freq.br(200 * 8)).dup.fader(amp:0.05) * (\\z4.sr > 0.5).lag(1);
} +> \\asayo;
" ]
//:--[80258.902406375]
[ '/code', "//:glissando jun
{ XLine.kr(200, 200 * 8, 180) } @>.jun \\freq;
{ XLine.kr(200 * 8, 200, 180) } @>.asayo \\freq;
" ]
//:--[80282.245932959]
[ '/code', "//:source jun 1
{
	SinOsc.ar(\\freq.br(200)).dup.fader(amp: 0.05) * (\\x1.sr > 0.5).lag(1);
} +> \\jun;
//source asayo 1
{
	SinOsc.ar(\\freq.br(200 * 8)).dup.fader(amp:0.05) * (\\z4.sr > 0.5).lag(1);
} +> \\asayo;
" ]
//:--[80351.106191834]
[ '/code', "//:glissando jun
{ XLine.kr(200, 200 * 8, 180) } @>.jun \\freq;
{ XLine.kr(200 * 8, 200, 180) } @>.asayo \\freq;
" ]

//the end

