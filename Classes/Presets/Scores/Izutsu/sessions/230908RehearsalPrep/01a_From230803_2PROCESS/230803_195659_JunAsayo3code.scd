// Exporting 8 snippets on 230828_221325
//:--[66263.668160959]
[ '/code', "'tokyo'.envir play: ( 'selectionNum': 0, 'ampctl': [ 'tokyo', 'amp', 1, 'off', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'startframe': 551374, 'trate': 0.0, 
  'pos': 0.15525114155251, 'player': 'tokyo', 'vol': 1, 'endframe': 1750985, 'buf': 'bird_crickets', 
  'paramctl': ( 'vol': [ 'tokyo', 'vol', 3, 'z', 0, 0.9, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'trate': [ 'tokyo', 'trate', 4, 'x', 0.1, 0.4, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'pos': [ 'tokyo', 'pos', 3, 'x', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ), 'playfunc': 'grainfroth' )" ]
//:--[66263.672210084]
[ '/code', "nil @>.tokyo \\amp" ]
//:--[66263.672315875]
[ '/code', "{ \\x3.sr.lin(\\lo.kr(0), \\hi.kr(1)) } @>.tokyo \\pos" ]
//:--[66263.673709667]
[ '/code', "{ \\x4.sr.lin(\\lo.kr(0.1), \\hi.kr(0.4)) } @>.tokyo \\trate" ]
//:--[66263.67488125]
[ '/code', "{ \\z3.sr.lin(\\lo.kr(0), \\hi.kr(0.9)) } @>.tokyo \\vol" ]
//:--[66290.677769042]
[ '/code', "\\tokyo.envir.set(\\trate, \\hi, 0.2)" ]
//:--[66298.054972167]
[ '/code', "\\tokyo.envir.set(\\trate, \\lo, 0.0)" ]
//:--[66713.978754]
[ '/code', "\\tokyo.envir.stopSynths" ]

//the end

