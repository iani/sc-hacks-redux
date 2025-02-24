// Exporting 2 snippets on 230828_214730
//:--[19596.737094166]
[ '/code', "//: Sea + Wind - controllled by 2nd sensor on ankle (NOR hand)
{ Resonz.ar(WhiteNoise.ar.dup * (\\x1.sr > 0.3).lag(1),

	 // \\x2.sr.exp(0.01, 1.9) * (\\x2.sr > 0.3).lag(1)
	\\z2.sr.exp(50, 5000).lag(0.4),
	\\x2.sr.exp(0.01, 5).lag(0.4)
) * 0.1// * XLine.kr(0.001, 0.1, 20)
} +> \\justine;
" ]
//:--[19598.224972875]
[ '/code', "//: Sea + Wind - controllled by 2nd sensor on ankle (NOR hand)
/// crescendo 
{ Resonz.ar(WhiteNoise.ar.dup * (\\x3.sr > 0.3).lag(1),

	 // \\x2.sr.exp(0.01, 1.9) * (\\x2.sr > 0.3).lag(1)
	\\z4.sr.exp(50, 5000).lag(0.4),
	\\x4.sr.exp(0.01, 5).lag(0.4)
) * 0.1 //  * XLine.kr(0.001, 0.1, 20)
} +> \\natali;
" ]

//the end

