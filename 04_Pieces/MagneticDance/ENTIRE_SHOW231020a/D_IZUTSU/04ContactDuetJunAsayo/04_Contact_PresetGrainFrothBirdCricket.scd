/*presets for sun saved at 231019_190846*/

//: (0) sun grainfrothl bird_crickets
//this is the template from mgrainfroth 230830_195041      
//The source is: 230803_195659_JunAsayo3code      
(
'amp': [ 1.0, "Xyz(5, 0.2, 0.3, 1.5) + Xyz(8, 0.2, 0.3, 1.5) * 2.0", 'on' ],
'buf': 'bird_crickets',
'endframe': [ 2393634, nil, nil ],
'playfunc': 'grainfrothl',
'pos': [ 0.079185223249971, "\\x7.sr", 'on' ],
'startframe': [ 16764, nil, nil ],
'trate': [ 0.0, "\\z7.sr", 'off' ],
'vol': [ 3.356638951877, "", 'off' ],
)
//: (1) sun grainmagshift hanbaiki
//This should be used in asayo's last solo as it is here.    
//see also separate file copy of the preset 230913_asayo_finale_solo.scd    
//It should be softened through resonant filters, rate and shift etc.!!!    
(
'amp': [ 0.70246105274054, "Xyz(7, 0.15, 0.05, 2.1) + Xyz(6, 0.15, 0.05, 2.1) - 2.0", 'on' ],
'buf': 'hanbaiki',
'endframe': [ 21861056, nil, nil ],
'pan': [ 0.68345323741007, "", 'off' ],
'playfunc': 'grainmagshift',
'pos': [ 0.30215827338129, "\\x7.sr", 'on' ],
'rate': [ 0.12300649892359, "\\z6.sr.lin(0.15, 0.9).lag(3.0)", 'on' ],
'shift': [ -45.323741007194, "\\x7.sr.lin(-480, -220).lag(3.5)", 'on' ],
'startframe': [ 989188, nil, nil ],
'stretch': [ 2.058309352518, "\\x6.sr.lin(0.5, 2.5).lag(2.5)", 'on' ],
'trate': [ 4.8761198231362, "\\z7.sr", 'off' ],
'vol': [ 8.6798043579525, "", 'off' ],
)
//: (2) sun grainbrickwall bird_crickets
(
'amp': [ 0.49707572071839, "Xyz(7, 0.15, 0.05, 2.1) + Xyz(6, 0.15, 0.05, 2.1) - 3.0", 'off' ],
'buf': 'bird_crickets',
'endframe': 2451380,
'playfunc': 'grainbrickwall',
'pos': [ 0.11510791366906, "\\x5.sr", 'off' ],
'rate': [ 0.11704296734081, "\\x7.sr.lin(1, 1.9)", 'off' ],
'startframe': 0,
'trate': [ 70.360691643081, "\\z5.sr", 'off' ],
'vol': [ 9.0181667615548, "", 'off' ],
'wipe': [ -0.62805755395683, "xz6.sr.lin(-0.1, 0.1)", 'off' ],
)
//: (3) sun grainbrickwall swallows
(
'amp': [ 0.98566326794679, "Xyz(5, 0.2, 0.1, 1.5)", 'off' ],
'buf': 'swallows',
'endframe': [ 25923983, nil, nil ],
'playfunc': 'grainbrickwall',
'pos': [ 0.93884892086331, "\\x5.sr", 'off' ],
'rate': [ 0.47061077937735, "", 'off' ],
'startframe': [ 399801, nil, nil ],
'trate': [ 24.189551024695, "\\z5.sr", 'off' ],
'vol': [ 9.7143005020444, "", 'off' ],
'wipe': [ -0.39496402877698, "xz6.sr", 'off' ],
)
//: (4) sun grainbrickwall fones
(
'amp': [ 0.99281869468454, "Xyz(5, 0.2, 0.1, 1.5)", 'off' ],
'buf': 'fones',
'endframe': 2451380,
'playfunc': 'grainbrickwall',
'pos': [ 0.65827338129496, "\\x5.sr", 'off' ],
'rate': [ 0.41562822832389, "", 'off' ],
'startframe': 0,
'trate': [ 14.183279505276, "\\z5.sr", 'off' ],
'vol': [ 5.8704259613892, "", 'off' ],
'wipe': [ -0.29136690647482, "xz6.sr", 'off' ],
)
/* THE END */