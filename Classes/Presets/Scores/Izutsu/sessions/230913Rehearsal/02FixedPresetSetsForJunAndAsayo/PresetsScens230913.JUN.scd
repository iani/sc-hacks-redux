/*presets for jun saved at 230913_203914*/

//: (0) jun grainfroth crickets
//this is the template from mgrainfroth 230830_195041    
//The source is: 230803_195659_JunAsayo3code    
(
'amp': [ 1.0, "Xyz(7, 0.2, 0.3, 1.5)", 'on' ],
'buf': 'crickets',
'endframe': [ 1826955, nil, nil ],
'playfunc': 'grainfroth',
'pos': [ 0.079185223249971, "\\x7.sr", 'on' ],
'startframe': [ 9906, nil, nil ],
'trate': [ 0.0, "\\z7.sr", 'off' ],
'vol': [ 3.356638951877, "", 'off' ],
)
//: (1) jun grainmagshift crickets
//This should be used in asayo's last solo as it is here.  
//see also separate file copy of the preset 230913_asayo_finale_solo.scd  
//It should be softened through resonant filters, rate and shift etc.!!!  
(
'amp': [ 0.66674861549609, "Xyz(7, 0.15, 0.05, 2.1) + Xyz(6, 0.15, 0.05, 2.1) + 0.5", 'on' ],
'buf': 'crickets',
'endframe': 2451380,
'pan': [ 0.70503597122302, "", 'off' ],
'playfunc': 'grainmagshift',
'pos': [ 0.21942446043165, "\\x7.sr", 'on' ],
'rate': [ 0.12300649892359, "\\z6.sr.lin(0.25, 0.9).round(0.1);", 'on' ],
'shift': [ 0.0, "\\x7.sr.lin(-280, -120).lag(0.5).round(0.1);", 'on' ],
'startframe': 0,
'stretch': [ 1.0521223021583, "\\x6.sr.lin(1.1, 1.5).lag(0.5).round(0.1);", 'on' ],
'trate': [ 3.8314873847371, "\\z7.sr", 'off' ],
'vol': [ 8.6798043579525, "", 'off' ],
)
//: (2) jun grainbrickwall bird_crickets
(
'amp': [ 0.86129082345634, "Xyz(5, 0.2, 0.1, 1.5)", 'off' ],
'buf': 'bird_crickets',
'endframe': 2451380,
'playfunc': 'grainbrickwall',
'pos': [ 0.51798561151079, "\\x5.sr", 'off' ],
'rate': [ 0.11704296734081, "", 'off' ],
'startframe': 0,
'trate': [ 6.8810664814327, "\\z5.sr", 'off' ],
'vol': [ 3.8279592153615, "", 'off' ],
'wipe': [ -0.019424460431655, "xz6.sr", 'off' ],
)
//: (3) jun grainbrickwall crickets
(
'amp': [ 0.99281869468454, "Xyz(5, 0.2, 0.1, 1.5)", 'off' ],
'buf': 'crickets',
'endframe': 2451380,
'playfunc': 'grainbrickwall',
'pos': [ 0.62589928057554, "\\x5.sr", 'off' ],
'rate': [ 0.023275899579254, "", 'off' ],
'startframe': 0,
'trate': [ 5.3145893799404, "\\z5.sr", 'off' ],
'vol': [ 10.0, "", 'off' ],
'wipe': [ -0.051798561151079, "xz6.sr", 'off' ],
)
//: (4) jun grainbrickwall fones
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