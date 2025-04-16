// Exporting 8 snippets on 230828_220901
//:--[4156.80252375]
[ '/code', "'asayo'.envir play: ( 'dur': 1, 'selectionNum': 0, 'ampctl': [ 'asayo', 'amp', 2, 'xyz', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'startframe': 206765, 
  'rate': 1.0010519616422, 'trate': 1.0, 'pos': 0.0025875190258752, 'player': 'asayo', 'vol': 1.5781572527679, 
  'endframe': 2326575, 'buf': 'bird_crickets', 'paramctl': ( 'rate': [ 'asayo', 'rate', 2, 'z', 0.01, 10.0, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'vol': [ 'asayo', 'vol', 1, 'off', 0, 10, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'dur': [ 'asayo', 'dur', 2, 'x', 0.9, 1.0, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'trate': [ 'asayo', 'trate', 2, 'z', 0.1, 0.9, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 
  'pos': [ 'asayo', 'pos', 2, 'x', 0.0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ), 'playfunc': 'grainecho' )" ]
//:--[4156.808625]
[ '/code', "XyzGt(2) @>.asayo \\amp" ]
//:--[4156.810912167]
[ '/code', "{ \\x2.sr.lin(\\lo.kr(0.9), \\hi.kr(1.0)) } @>.asayo \\dur" ]
//:--[4156.812527292]
[ '/code', "{ \\x2.sr.lin(\\lo.kr(0.0), \\hi.kr(1)) } @>.asayo \\pos" ]
//:--[4156.813959542]
[ '/code', "{ \\z2.sr.lin(\\lo.kr(0.1), \\hi.kr(0.9)) } @>.asayo \\trate" ]
//:--[4156.815358709]
[ '/code', "{ \\z2.sr.lin(\\lo.kr(0.01), \\hi.kr(10.0)) } @>.asayo \\rate" ]
//:--[4156.816802834]
[ '/code', "nil @>.asayo \\vol" ]
//:--[4489.325594834]
[ '/code', "\\asayo.envir.stopSynths" ]

//the end

