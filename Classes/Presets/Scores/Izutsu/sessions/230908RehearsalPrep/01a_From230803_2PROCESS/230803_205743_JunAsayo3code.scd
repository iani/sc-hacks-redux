// Exporting 8 snippets on 230828_221421
//:--[69905.503187125]
[ '/code', "'tokyo'.envir play: ( 'dur': 0.63013698630137, 'selectionNum': 0, 'ampctl': [ 'tokyo', 'amp', 1, 'off', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'startframe': 95000, 
  'rate': 0.13708385261356, 'trate': 0.15981735159817, 'pos': 0.97260273972603, 'player': 'tokyo', 'vol': 0.58568420174725, 
  'endframe': 2311673, 'buf': 'bird_crickets', 'paramctl': ( 'rate': [ 'tokyo', 'rate', 4, 'x', 0.1, 2.5, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'vol': [ 'tokyo', 'vol', 1, 'off', 0, 10, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'dur': [ 'tokyo', 'dur', 3, 'x', 0.5, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'trate': [ 'tokyo', 'trate', 4, 'z', 0.5, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 
  'pos': [ 'tokyo', 'pos', 3, 'z', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ), 'playfunc': 'grainecho' )" ]
//:--[69905.50924025]
[ '/code', "nil @>.tokyo \\amp" ]
//:--[69905.509368209]
[ '/code', "{ \\x3.sr.lin(\\lo.kr(0.5), \\hi.kr(1)) } @>.tokyo \\dur" ]
//:--[69905.511136792]
[ '/code', "{ \\z3.sr.lin(\\lo.kr(0), \\hi.kr(1)) } @>.tokyo \\pos" ]
//:--[69905.51295625]
[ '/code', "{ \\z4.sr.lin(\\lo.kr(0.5), \\hi.kr(1)) } @>.tokyo \\trate" ]
//:--[69905.51483075]
[ '/code', "{ \\x4.sr.lin(\\lo.kr(0.1), \\hi.kr(2.5)) } @>.tokyo \\rate" ]
//:--[69905.516584959]
[ '/code', "nil @>.tokyo \\vol" ]
//:--[70032.823930167]
[ '/code', "\\tokyo.envir.stopSynths" ]

//the end

