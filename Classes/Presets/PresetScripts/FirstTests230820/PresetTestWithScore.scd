/*presets for jun saved at 230820_225037*/

//: (0) jun
"testscore"
//: (1) jun
"default"
//: (2) jun grainecho wind_strong
(
'ampctl': [ 'tokyo', 'amp', 1, 'xyz', 0, 1, 'lin', "x.lag(1).exp(500, 1000)", "(1 - z.lag(1)).exp(500, 1000)", "(x + y + z).lag(1).exp(500, 1000)" ],
'buf': 'wind_strong',
'dur': 1.0,
'endframe': 1150514,
'paramctl': ( 'pos': [ 'tokyo', 'pos', 1, 'off', 0, 1, 'lin', "x.lag(1).exp(500, 1000)", "(1 - z.lag(1)).exp(500, 1000)", "(x + y + z).lag(1).exp(500, 1000)" ], 'vol': [ 'tokyo', 'vol', 1, 'off', 0, 10, 'lin', "x.lag(1).exp(500, 1000)", "(1 - z.lag(1)).exp(500, 1000)", "(x + y + z).lag(1).exp(500, 1000)" ], 'dur': [ 'tokyo', 'dur', 1, 'z', 0.5, 1.0, 'lin', "x.lag(1).exp(500, 1000)", "(1 - z.lag(1)).exp(500, 1000)", "(x + y + z).lag(1).exp(500, 1000)" ], 'rate': [ 'tokyo', 'rate', 1, 'x', 0.01, 10, 'lin', "x.lag(1).exp(500, 1000)", "(1 - z.lag(1)).exp(500, 1000)", "(x + y + z).lag(1).exp(500, 1000)" ], 
  'trate': [ 'tokyo', 'trate', 1, 'off', 0, 1, 'lin', "x.lag(1).exp(500, 1000)", "(1 - z.lag(1)).exp(500, 1000)", "(x + y + z).lag(1).exp(500, 1000)" ] ),
'player': 'tokyo',
'playfunc': 'grainecho',
'pos': 0.10958904109589,
'rate': 0.213190144172,
'selectionNum': 0,
'startframe': 888712,
'trate': 0.5296803652968,
'vol': 1.0510623214695,
)
/* THE END */