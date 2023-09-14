//: --- GrainBuf0 ---
//: could be interesting
\default +>.test \buf;
{ Impulse.kr(10) } @>.test \trig;
{ Line.kr(0, 1, 60) } @>.test \pos;
{ LFNoise2.kr(5.reciprocal).range(0.2, 5) } @>.test \rate;
0.4 @>.test \dur;
{ GrainBuf0_.ar } +> \test;
//:crickets - they seem to be calling
\crickets +>.test \buf;
{ Impulse.kr(1) } @>.test \trig;
{ Line.kr(0, 1, 60) } @>.test \pos;
5.reciprocal @>.test \rate;
4 @>.test \dur;
{ GrainBuf0_.ar } +> \test;

//:crickets
\crickets +>.test \buf;
{ Impulse.kr(10) } @>.test \trig;
{ Line.kr(0, 1, 60) } @>.test \pos;
{ LFNoise2.kr(5.reciprocal).range(0.2, 5) } @>.test \rate;
0.4 @>.test \dur;
//:

//:
{ Impulse.kr(0.2) } @>.test \trig;
4.1 @>.test \vol;
{ GrainBuf0_.ar } +> \test;
3.04 @>.test \dur;
//:
0.5 @>.test \pos;
//:
{ Impulse.kr(10) } @>.test \trig;
{ Line.kr(0, 1, 60) } @>.test \pos;
0.2 @>.test \dur;
//:
\test.push;
3 +>.test \xxx;
\test +>.test \yyy;