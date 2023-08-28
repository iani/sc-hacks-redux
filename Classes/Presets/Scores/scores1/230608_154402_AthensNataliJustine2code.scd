// Exporting 3 snippets on 230828_214651
//:--[18793.106116125]
[ '/code', "//: Sea + Wind - controllled by 2nd sensor on ankle (NOR hand)
{ Resonz.ar(WhiteNoise.ar.dup * \\x2.sr.exp(0.01, 1.9) * (\\x2.sr > 0.3).lag(1),
	\\z2.sr.exp(50, 5000),
	\\x1.sr.exp(0.01, 5)
) * 5
} +> \\ampcheck1;
" ]
//:--[18815.797149041]
[ '/code', "//: Sea + Wind - controllled by 2nd sensor on ankle (NOR hand)
{ Resonz.ar(WhiteNoise.ar.dup * (\\x1.sr > 0.3).lag(1),

	 // \\x2.sr.exp(0.01, 1.9) * (\\x2.sr > 0.3).lag(1)
	\\z2.sr.exp(50, 5000).lag(0.4),
	\\x2.sr.exp(0.01, 5).lag(0.4)
) * 0.1
} +> \\ampcheck1;
" ]
//:--[19166.183105541]
[ '/code', "//: Sea + Wind - controllled by 2nd sensor on ankle (NOR hand)
{ Resonz.ar(WhiteNoise.ar.dup * (\\x1.sr > 0.3).lag(1),

	 // \\x2.sr.exp(0.01, 1.9) * (\\x2.sr > 0.3).lag(1)
	\\z2.sr.exp(50, 5000).lag(0.4),
	\\x2.sr.exp(0.01, 5).lag(0.4)
) * XLine.kr(0.001, 0.1, 20)
} +> \\ampcheck1;
" ]

//the end

