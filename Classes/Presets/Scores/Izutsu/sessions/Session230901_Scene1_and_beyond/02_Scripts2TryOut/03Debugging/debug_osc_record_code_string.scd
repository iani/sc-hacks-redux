//   2 Sep 2023 08:51
// Why is code recorded as string and not as symbol?


//:
"This is a test";
//:
"This is a bigger test";
\s1_static <!> 7; // duration of initial static freq
\s1_glide <!> 180; // total duration of glide
\s1_spread <!> 90; // duration of spreading from sine to noise
\s1_numsines <!> 50; // number of sine ugens
5 +>.asayo \fadeTime;
5 +>.jun \fadeTime;
// controls:

{ \x1.gt.lag(0.5) * 0.06 } @>.jun \amp;
{ \z4.gt.lag(0.5) * 0.06} @>.asayo \amp;

//:
\s1_static <!> 7; // duration of initial static freq
\s1_glide <!> 180; // total duration of glide
\s1_spread <!> 90; // duration of spreading from sine to noise
\s1_numsines <!> 50; // number of sine ugens
5 +>.asayo \fadeTime;
5 +>.jun \fadeTime;
// controls:

{ \x1.gt.lag(0.5) * 0.06 } @>.jun \amp;
{ \z4.gt.lag(0.5) * 0.06} @>.asayo \amp;

//:
{ SinOsc.ar(\freq.br(800) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.01).pan } +> \asayo;
{ SinOsc.ar(\freq.br(200) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.01).pan } +> \jun;
