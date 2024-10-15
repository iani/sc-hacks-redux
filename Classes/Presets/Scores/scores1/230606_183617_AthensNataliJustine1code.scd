// Exporting 2 snippets on 230828_214557
//:--[372252.30754446]
[ '/code', "//: Sea + Wind - controllled by 2nd sensor on ankle (NOR hand)
{ Resonz.ar(WhiteNoise.ar.dup * \\x2.sr.exp(0.01, 1.9) * (\\x2.sr > 0.3).lag(1),
	\\z2.sr.exp(50, 5000),
	\\x1.sr.exp(0.01, 5)
) * 5
} +> \\ampcheck1;
" ]
//:--[372498.11385458]
[ '/code', "//: Variation - different types of sound sources
{ Resonz.ar(LFSaw.ar(\\z1.sr.exp(40, 4000)).dup * \\x2.sr.exp(0.01, 1.9) * (\\x2.sr > 0.3).lag(1),
	\\z2.sr.exp(50, 5000),
	\\x1.sr.exp(0.01, 5)
) * 5
} +> \\ampcheck1;
" ]

//the end

