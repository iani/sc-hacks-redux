/* 15 Sep 2023 00:21
Adapt this to script for scene, with sensor controls
*/
//:from 02. br controls but No sensors yet:
{ Impulse.kr(2.9, pi) } @>.test \trig;
\crickets +>.test \buf;
0.1 @>.test \vol;
{
	var src;
	src = GrainBuf0_.ar;
	DynKlank.ar(
		`[[
			\f1.br(400), \f2.br(500), \f3.br(600), \f4.br(700)
		] / 2, [0.01, 0.01, 0.01, 0.01]],
		src
	);
} +> \test;
{ Line.kr(0, 1, 100) } @>.test \pos;
//:
1200 @>.test \f1;
//:
1000 @>.test \f2;
//:
900 @>.test \f3;
//:
800 @>.test \f4;
//:
0.5 @>.test \rate;
//:
1.5 @>.test \rate;
//:
0.15 @>.test \rate;
//:
2 @>.test \rate;