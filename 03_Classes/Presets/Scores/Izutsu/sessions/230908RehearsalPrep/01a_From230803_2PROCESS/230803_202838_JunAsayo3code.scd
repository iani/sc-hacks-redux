// Exporting 14 snippets on 230828_221343
//:--[68161.664332709]
[ '/code', "'tokyo'.envir play: ( 'selectionNum': 0, 'ampctl': [ 'tokyo', 'amp', 1, 'off', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'startframe': 95000, 'trate': 0, 
  'pos': 0, 'player': 'tokyo', 'vol': 1, 'endframe': 2311673, 'buf': 'bird_crickets', 
  'paramctl': ( 'vol': [ 'tokyo', 'vol', 3, 'z', 0.05, 0.9, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'trate': [ 'tokyo', 'trate', 4, 'x', 0.01, 0.2, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'pos': [ 'tokyo', 'pos', 3, 'x', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ), 'playfunc': 'grainfroth' )" ]
//:--[68161.672412]
[ '/code', "nil @>.tokyo \\amp" ]
//:--[68161.6726]
[ '/code', "{ \\x3.sr.lin(\\lo.kr(0), \\hi.kr(1)) } @>.tokyo \\pos" ]
//:--[68161.674601792]
[ '/code', "{ \\x4.sr.lin(\\lo.kr(0.01), \\hi.kr(0.2)) } @>.tokyo \\trate" ]
//:--[68161.67646175]
[ '/code', "{ \\z3.sr.lin(\\lo.kr(0.05), \\hi.kr(0.9)) } @>.tokyo \\vol" ]
//:--[68707.1740635]
[ '/code', "\\tokyo.envir.set(\\vol, \\hi, 0.7)" ]
//:--[68710.159229792]
[ '/code', "\\tokyo.envir.set(\\vol, \\hi, 0.6)" ]
//:--[68712.501265292]
[ '/code', "\\tokyo.envir.set(\\vol, \\hi, 0.5)" ]
//:--[68714.834541834]
[ '/code', "\\tokyo.envir.set(\\vol, \\hi, 0.4)" ]
//:--[68718.291746917]
[ '/code', "\\tokyo.envir.set(\\vol, \\hi, 0.3)" ]
//:--[68721.860561209]
[ '/code', "\\tokyo.envir.set(\\vol, \\hi, 0.2)" ]
//:--[68724.940797667]
[ '/code', "\\tokyo.envir.set(\\vol, \\hi, 0.1)" ]
//:--[68731.7950125]
[ '/code', "\\tokyo.envir.set(\\vol, \\hi, 0.0)" ]
//:--[68731.87802525]
[ '/code', "\\tokyo.envir.stopSynths" ]

//the end

