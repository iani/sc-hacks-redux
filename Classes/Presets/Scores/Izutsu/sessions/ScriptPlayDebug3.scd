//code
//Test score for advancing with osc messages
//:--[0] play a sine func
"STARTING.  This is the first snippet in this score".postln;
5 +>.test \fadeTime;
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[1] play a sine func
{ PinkNoise.ar(0.1) } +> \test;
//:--[2] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[3] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[4] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[5] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[6] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[7] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[8] stop
\test.stop;
"THE END".postln;
