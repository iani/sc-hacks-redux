//code
//Test score for advancing with osc messages
//:--[3] play a sine func
"STARTING.  This is the first snippet in this score".postln;
5 +>.test \fadeTime;
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[3] play a sine func
{ PinkNoise.ar(0.1) } +> \test;
//:--[3] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[3] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[3] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[3] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[3] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[3] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[5] stop
\test.stop;
"THE END".postln;