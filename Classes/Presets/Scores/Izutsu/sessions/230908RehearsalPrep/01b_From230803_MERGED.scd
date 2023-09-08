//Merging and processing these files:
// 1. 230803_195659_JunAsayo3code
// 2. 230803_202838_JunAsayo3code
// 3. 230803_204306_JunAsayo3LONGcode
// 4. 230803_205743_JunAsayo3code
// 5. 230803_210447_JunAsayo3code
//:===== 1. 230803_195659_JunAsayo3code
'tokyo'.envir play: (
	'playfunc': 'grainfroth'
	'selectionNum': 0,
	'pos': 0.15525114155251,
	'player': 'tokyo',
	'vol': 1,
	'endframe': 1750985,
	'buf': 'bird_crickets',
	'trate': 0.0,
	'startframe': 551374,
	'ampctl': [ 'tokyo', 'amp', 1, 'off', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
	'paramctl': (
		'vol': [ 'tokyo', 'vol', 3, 'z', 0, 0.9, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
		'trate': [ 'tokyo', 'trate', 4, 'x', 0.1, 0.4, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ],
		'pos': [ 'tokyo', 'pos', 3, 'x', 0, 1, 'lin', \"x.lag(1).exp(500, 1000)\", \"(1 - z.lag(1)).exp(500, 1000)\", \"(x + y + z).lag(1).exp(500, 1000)\" ] ),
)
//:===== 1. 230803_195659_JunAsayo3code