/* 14 Sep 2023 14:15
clearer grains.
step 1.
*/
//: --- Grain0 ---
{ Impulse.kr(1) } @>.test \trig;
10 @>.test \vol;
{ Grain0_.ar } +> \test;
//:
0.5 +>!.test \pos;
//:
2.1 @>.test \dur;
4 @>.test \vol;
1 @>.test \amp;
//:
0.5 @>.test \rate;
//:
{ Line.kr(0.5, 2, 3) } @>.test \rate;
//:
{ Impulse.kr(5) } @>.test \trig;
//
//{ Line.kr(0, 1, 60) } @>.test \pos;
//: --- GrainBuf0 ---
{ Impulse.kr(0.2) } @>.test \trig;
4.1 @>.test \vol;
{ GrainBuf0_.ar } +> \test;
3.04 @>.test \dur;
//:
0.5 +>!.test \pos;
//:
0.5 @>.test \pos;
//:
{ Impulse.kr(10) } @>.test \trig;
{ Line.kr(0, 1, 50) } @>.test \pos;
0.2 @>.test \dur;
//:+
0 @>.test \trig;
0.2.wait;
1 @>.test \trig.postln;
0.2.wait;
0 @>.test \trig;
0.2.wait;
1 @>.test \trig.postln;
0.2.wait;
//:
{ Impulse.kr(Line.kr(1, 5, 6)) } @>.test \trig;
//:
2.04 @>.test \dur;
{ Impulse.kr(4) } @>.test \trig;