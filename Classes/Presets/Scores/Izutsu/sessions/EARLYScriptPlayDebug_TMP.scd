//onsetcode
//debugging the timestamps in the script
// //:--[0]
// 0.postln;
//:--[0] global vars + sound start
// \s1_static <!> 7; // duration of initial static freq
// \s1_glide <!> 60; // total duration of glide
// \s1_spread <!> 30; // duration of spreading from sine to noise
// \s1_numsines <!> 50; // number of sine ugens
// 5 +>.asayo \fadeTime;
// 5 +>.jun \fadeTime;
{ PinkNoise.ar(0.1) } +> \perc;
// { SinOsc.ar(\freq.br(800) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.15).pan } +> \asayo;
// { SinOsc.ar(\freq.br(200) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.15).pan } +> \jun;

//:--[1]
1.postln;
//:--[1.1]
1.1.postln;