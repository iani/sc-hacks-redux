//Keep sensor control in noise part of the script
//bare bones start
//:controls
{ \x1.gt.lag(0.5) * 0.06 } @>.jun \amp;
{ \z4.gt.lag(0.5) * 0.06} @>.asayo \amp;

5 +>.asayo \fadeTime;
5 +>.jun \fadeTime;
//:
// controls:

{ \x1.gt.lag(0.5) * 0.06 } @>.jun \amp;
{ \z4.gt.lag(0.5) * 0.06} @>.asayo \amp;

//:
{ SinOsc.ar(\freq.br(800) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.01).pan } +> \asayo;
{ SinOsc.ar(\freq.br(200) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.01).pan } +> \jun;
