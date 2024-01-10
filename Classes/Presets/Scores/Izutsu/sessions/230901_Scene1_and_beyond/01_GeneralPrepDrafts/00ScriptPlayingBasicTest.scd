//onsetcode
//debugging the timestamps in the script
//:--[0]
{ PinkNoise.ar(0.1).perc } +> \perc;
//:--[1]
{ PinkNoise.ar(0.1).perc } +> \perc;
1.postln;
//:--[1.1]
1.1.postln;
{ PinkNoise.ar(0.1).perc } +> \perc;
"THE END";