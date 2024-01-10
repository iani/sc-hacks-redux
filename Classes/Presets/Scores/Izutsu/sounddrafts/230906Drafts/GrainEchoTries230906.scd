//  6 Sep 2023 18:23
//
//
//:from preset mgrainecho
\jun.envir play: (
'amp': [ 1.0, "Xyz(5, 0.2, 0.1, 1.5)", 'off' ],
'buf': 'bird_crickets',
'endframe': 2451380,
'playfunc': 'grainecho',
'pos': [ 0.079365079365079, "\\x5.sr", 'on' ],
'startframe': 0,
'trate': [ 0.48412698412698, "\\z5.sr", 'on' ],
'vol': [ 3.356638951877, "", 'off' ],
);
//:substitute cgrainfroth from preset mgrainecho
\jun.envir play: (
'amp': [ 1.0, "Xyz(5, 0.2, 0.1, 1.5)", 'off' ],
'buf': 'bird_crickets',
'endframe': 2451380,
'playfunc': 'grainfroth',
'pos': [ 0.079365079365079, "\\x5.sr", 'on' ],
'startframe': 0,
'trate': [ 0.48412698412698, "\\z5.sr", 'on' ],
'vol': [ 3.356638951877, "", 'off' ],
);
//:from rehearsal code
\mary.envir play:
( \vol: [ 3.356638951877, "", \off ],
\endframe: 2451380,
buf: \bird_crickets,
\amp: [ 1.0, "Xyz(5, 0.2, 0.1, 1.5)", \on ],
\startframe: 0,
\playfunc: \grainfroth,
\trate: [ 0.48412698412698, "\\z5.sr", \on ],
\pos: [ 0.079365079365079, "\\x5.sr", \on ] )
//:from Exporting 14 snippets on 230828_221343
'tokyo'.envir play:
( 'selectionNum': 0,
'ampctl': [ 'tokyo', 'amp', 1, 'off', 0, 1, 'lin',
	"x.lag(1).exp(500, 1000)", "(1 - z.lag(1)).exp(500, 1000)", "(x + y + z).lag(1).exp(500, 1000)\" ], 'startframe': 95000, 'trate': 0,
  'pos': 0, 'player': 'tokyo', 'vol': 1, 'endframe': 2311673, 'buf': 'bird_crickets',
  'paramctl': ( 'vol': [ 'tokyo', 'vol', 3, 'z', 0.05, 0.9, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'trate': [ 'tokyo', 'trate', 4, 'x', 0.01, 0.2, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'pos': [ 'tokyo', 'pos', 3, 'x', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ), 'playfunc': 'grainfroth' )" ]
