/*presets for jun saved at 230823_144634*/

//: (0) jun plainsynthtest nil
////on-off with x1.gt  
(
'amp': [ 0.1, "\\x1.gt lag: 0.5 * 0.1", 'on' ],
'freq': [ 551.45005724336, "LFNoise2.kr(5).range(100, 10000)", 'off' ],
'playfunc': 'plainsynthtest',
)
//: (1) jun plainsynthtest nil
////on-off with Xyz  
(
'amp': [ 0.1, "Xyz(1, 0.1, 0.1, 0.5) * 0.1 - 0.1 max: 0 lag: 0.5", 'on' ],
'freq': [ 1743.8381967193, "LFNoise2.kr(5).range(100, 10000)", 'off' ],
'playfunc': 'plainsynthtest',
)
//: (2) jun plainsynthtest nil
//preparing for Integrate  
(
'amp': [ 0.1, "Xyz(1, 0.1, 0.1, 0.5)", 'on' ],
'freq': [ 1743.8381967193, "LFNoise2.kr(5).range(100, 10000)", 'off' ],
'playfunc': 'plainsynthtest',
)
/* THE END */