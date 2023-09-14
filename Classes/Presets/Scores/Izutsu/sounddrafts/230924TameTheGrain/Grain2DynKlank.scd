/* 14 Sep 2023 23:35
filtering rains through a dynklank resonant filter bank
*/

//:
{ Impulse.kr(2.9, pi) } @>.test \trig;
\crickets +>.test \buf;
0.3 @>.test \rate;
0.1 @>.test \vol;
{ GrainBuf0_.ar } +> \test;
3.04 @>.test \dur;
//:
{ Line.kr(0, 1, 100) } @>.test \pos;
//:
{
	var src;
	src = GrainBuf0_.ar;
	DynKlank.ar(
		`[[400, 500, 600, 700], [0.05, 0.05, 0.05, 0.05]],
		src
	);
} +> \test;
{ Line.kr(0, 1, 100) } @>.test \pos;
//:
{
	var src;
	src = GrainBuf0_.ar;
	DynKlank.ar(
		`[[400, 500, 600, 700] / 2, [0.01, 0.01, 0.01, 0.01]],
		src
	);
} +> \test;
{ Line.kr(0, 1, 100) } @>.test \pos;
//:
{
	var src;
	src = GrainBuf0_.ar;
	DynKlank.ar(
		`[[400, 500, 600, 1400] / 2, [0.01, 0.01, 0.01, 0.01]],
		src
	);
} +> \test;
{ Line.kr(0, 1, 100) } @>.test \pos;
//:
{
	var src;
	src = GrainBuf0_.ar;
	DynKlank.ar(
		`[[1600, 500, 600, 1400] / 2, [0.01, 0.01, 0.01, 0.01]],
		src
	);
} +> \test;
{ Line.kr(0, 1, 100) } @>.test \pos;
//:
{
	var src;
	src = GrainBuf0_.ar;
	DynKlank.ar(
		`[[400, 500, 600, 700], [0.01, 0.01, 0.01]],
		src
	);
} +> \test;