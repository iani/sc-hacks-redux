//code
//Test score for advancing with osc messages
//:--[3] play a sine func
5 +>.test \fadeTime;
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
//:--[3] play a sine func
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:--[5] stop
"THE END".postln;
\test.stop;