//Merging and processing these files:
// 1. 230806_155538_JunAsayo4code
// 2. 230806_160651_JunAsayo4code
// 3. 230806_161435_JunAsayo4code
// 4. 230806_162815_JunAsayo4code
// 5. 230806_164614_JunAsayo4code
// 6. 230806_165601_JunAsayo4code
//:===== 1. 230806_155538_JunAsayo4code = only vol, no synth code...
//:===== 2. 02_230806_160651_JunAsayo4code
'asayo'.envir play:
(
'playfunc': 'grainfroth'
'selectionNum': 0,
'ampctl': [ 'asayo', 'amp', 1, 'off', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
'startframe': 221667,
'trate': 0.018264840182648,
'pos': 0.91780821917808,
'player': 'asayo',
'vol': 0.0,
'endframe': 2304222,
'buf': 'bird_crickets',
'paramctl': (
	'vol': [ 'asayo', 'vol', 4, 'x', 0.01, 0.3, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
	'trate': [ 'asayo', 'trate', 3, 'z', 0.1, 0.5, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
	'pos': [ 'asayo', 'pos', 3, 'x', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ),
)
//:repeat 02 -- early conversion
'asayo'.envir play: ( 'selectionNum': 0, 'ampctl': [ 'asayo', 'amp', 1, 'off', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'startframe': 221667, 'trate': 0.018264840182648,
  'pos': 0.91780821917808, 'player': 'asayo', 'vol': 0.0, 'endframe': 2304222, 'buf': 'bird_crickets',
  'paramctl': ( 'vol': [ 'asayo', 'vol', 4, 'x', 0.01, 0.3, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'trate': [ 'asayo', 'trate', 3, 'z', 0.1, 0.5, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'pos': [ 'asayo', 'pos', 3, 'x', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ), 'playfunc': 'grainfroth' )
//:===== 3. 03_230806_161435_JunAsayo4code
//v1 - 03 - from recording direct
//1. asayo
'asayo'.envir play: ( 'selectionNum': 0, 'ampctl': [ 'asayo', 'amp', 1, 'off', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'startframe': 221667, 'trate': 0.0,
  'pos': 0.91780821917808, 'player': 'asayo', 'vol': 0.0, 'endframe': 2304222, 'buf': 'bird_crickets',
  'paramctl': ( 'vol': [ 'asayo', 'vol', 4, 'x', 0.01, 0.3, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'trate': [ 'asayo', 'trate', 3, 'z', 0.1, 0.5, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'pos': [ 'asayo', 'pos', 3, 'x', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ), 'playfunc': 'grainfroth' )
//2. jun
'jun'.envir play: ( 'selectionNum': 0, 'ampctl': [ 'jun', 'amp', 1, 'xyz', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'startframe': 264632, 'rate': 1,
  'stretch': 1.4697260273973, 'trate': 50, 'pos': 0, 'player': 'jun', 'vol': 0.0,
  'endframe': 1742046, 'shift': -278.08219178082, 'buf': 'crickets', 'paramctl': ( 'vol': [ 'jun', 'vol', 1, 'off', 0, 10, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'shift': [ 'jun', 'shift', 2, 'z', -50.0, 0.0, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'rate': [ 'jun', 'rate', 1, 'z', 0.01, 0.5, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'stretch': [ 'jun', 'stretch', 2, 'x', 1.0, 1.5, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
  'trate': [ 'jun', 'trate', 1, 'off', 1, 120, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'pos': [ 'jun', 'pos', 1, 'x', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ), 'playfunc': 'grainmagshift' )
//:===== 4. 04_230806_162815_JunAsayo4code
// 1. jun
'jun'.envir play: ( 'selectionNum': 0, 'ampctl': [ 'jun', 'amp', 1, 'xyz', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'startframe': 264632, 'rate': 1,
  'stretch': 1.4697260273973, 'trate': 50, 'pos': 0, 'player': 'jun', 'vol': 0.0052125685452764,
  'endframe': 1742046, 'shift': -278.08219178082, 'buf': 'crickets', 'paramctl': ( 'vol': [ 'jun', 'vol', 1, 'off', 0, 10, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'shift': [ 'jun', 'shift', 2, 'z', -50.0, 0.0, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'rate': [ 'jun', 'rate', 1, 'z', 0.01, 0.5, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'stretch': [ 'jun', 'stretch', 2, 'x', 1.0, 1.5, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
  'trate': [ 'jun', 'trate', 1, 'off', 1, 120, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'pos': [ 'jun', 'pos', 1, 'x', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ), 'playfunc': 'grainmagshift' )
//:===== 5. 05_230806_164614_JunAsayo4code
// 1. jun
'jun'.envir play: (
	'selectionNum': 0,
	'playfunc': 'grainmagshift'
	'ampctl': [ 'jun', 'amp', 1, 'xyz', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
	'startframe': 264632,
	'rate': 1,
	'stretch': 1.4697260273973,
	'trate': 50,
	'pos': 0,
	'player': 'jun',
	'vol': 0.00083401096724422,
	'endframe': 1742046,
	'shift': -278.08219178082,
	'buf': 'crickets',
	'paramctl': (
		'vol': [ 'jun', 'vol', 1, 'off', 0, 10, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
		'shift': [ 'jun', 'shift', 2, 'z', -50.0, 0.0, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
		'rate': [ 'jun', 'rate', 1, 'z', 0.01, 0.5, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
		'stretch': [ 'jun', 'stretch', 2, 'x', 1.0, 1.5, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
		'trate': [ 'jun', 'trate', 1, 'off', 1, 120, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
		'pos': [ 'jun', 'pos', 1, 'x', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ),
)
// 2. asayo
'asayo'.envir play: ( 'selectionNum': 0, 'ampctl': [ 'asayo', 'amp', 1, 'off', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'startframe': 221667, 'trate': 0.0,
  'pos': 0.91780821917808, 'player': 'asayo', 'vol': 0.0, 'endframe': 2304222, 'buf': 'bird_crickets',
  'paramctl': ( 'vol': [ 'asayo', 'vol', 4, 'x', 0.01, 0.3, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'trate': [ 'asayo', 'trate', 3, 'z', 0.1, 0.5, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ], 'pos': [ 'asayo', 'pos', 3, 'x', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ), 'playfunc': 'grainfroth' )
//:===== 6. 06_230806_165601_JunAsayo4code
