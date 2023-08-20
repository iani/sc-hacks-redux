/*presets for jun saved at 230820_185008*/

//: (0) jun plainsynthtest nil
(
'amp': [ 0.51020408163265, "", 'off' ],
'endframe': [ 534575, nil, nil ],
'freq': [ 160.61714442783, "", 'off' ],
'playfunc': 'plainsynthtest',
'startframe': [ 3404, nil, nil ],
)
//: (1) jun phasebuf weddellpock
(
'amp': [ 0.77607709750567, "", 'off' ],
'buf': 'weddellpock',
'dur': [ 0.69047619047619, "", 'off' ],
'endframe': [ 162950, nil, nil ],
'playfunc': 'phasebuf',
'pos': [ 0.51190476190476, "", 'off' ],
'rate': [ 1.0277908852238, "LFNoise2.kr(1).range(0.5, 1.2)", 'off' ],
'startframe': [ 134251, nil, nil ],
'trate': [ 0.3531746031746, "", 'off' ],
'trig': [ 62.0, "", 'off' ],
'vol': [ 7.0107394809776, "", 'off' ],
)
//: (2) jun grainecho wind_strong
(
'amp': [ 0.0, "", 'off' ],
'buf': 'wind_strong',
'dur': [ 1.0, "LFNoise0.kr(10)", 'off' ],
'endframe': 1150514,
'playfunc': 'grainecho',
'pos': [ 0, "", 'off' ],
'rate': [ 0, "", 'off' ],
'startframe': 888712,
'trate': [ 0, "", 'off' ],
'vol': [ 0, "", 'off' ],
)
//: (3) jun grainecho2 wind_strong
(
'amp': [ 0, "", 'off' ],
'buf': 'wind_strong',
'dur': [ 0.24206349206349, "LFNoise.kr(4).range(1, 2);", 'off' ],
'endframe': 1150514,
'playfunc': 'grainecho2',
'pos': [ 0.45238095238095, "", 'off' ],
'rate': [ 1.9843535278686, "", 'off' ],
'startframe': 888712,
'trate': [ 0.63888888888889, "", 'off' ],
'vol': [ 8.6963025951121, "", 'off' ],
)
/* THE END */