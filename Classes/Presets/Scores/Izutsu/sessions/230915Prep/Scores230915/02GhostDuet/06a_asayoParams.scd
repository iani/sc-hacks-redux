//code
// playng score for, ghost duet voice for asayo
//:--[60] ===== ASAYO ======
// initialize all control parameters first
101 @>.asayo \ls1;  // par 1
102 @>.asayo \ls2;  // par 2
0.01 @>.asayo \tempo; // par 3
10 @>.asayo \idiv; // par 4
// 1.015 @>.asayo \lin;
64 @>.asayo \ma64;
200 @>.asayo \base;
1 @>.asayo \sweepdir; // par
// 0.1 @>.asayo \wet;
10 @>.asayo \wet;
0.9 @>.asayo \soft;
{
	var p;
	p=Impulse;
	mean({|i|
		Pluck.ar(
			LFSaw.ar([
				// \ls1.br(102),
				\ls1.br, // par 1
				\ls2.br  // par 2
				// \ls2.br(101)
			]),
			x=p.ar(
				\tempo.br, // \tempo.br(1) // par 3
				i/
				\idiv.br // \idiv.br(10) // par 4
			)
			+p.ar(0) + \start.tr(0),
			1,
			1/
			Latch.ar(
				// \lin.br(1.015)  // par 5???
				1.015
				**Sweep.ar(
					0,
					\sweepdir.br // \sweepdir.br(1)
				)
				* \ma64.br // 64
				% 1
				+ 1
				* \base.br, // 200,
				x
			),
			\wet.br, // \wet.br(4),
			\soft.br // \soft.br(0.2)
		)
	}!10) * Env([0.1, 0.15, 1], [1.5, 2.01], 5).kr;
} +> \asayo;
//:--[10] -- trying the start
1 +>!.asayo \start;
//:--[20]
// 20 @>.asayo \idiv;
{ XLine.kr(10, 20, 20) } @>.asayo \idiv;
//:--[20]
// 20 @>.asayo \idiv;
{ XLine.kr(20, 10, 20) } @>.asayo \idiv;
//:--[20]
{ XLine.kr(0.5, 5, 20) } @>.asayo \tempo;
//:--[20]
{ XLine.kr(5.0, 0.5, 20) } @>.asayo \tempo;
//:--[20]
{ XLine.kr(200, 450, 20) } @>.asayo \base;
//:--[20]
{ XLine.kr(200, 50, 20) } @>.asayo \base;
//:--[20]
{ XLine.kr(50, 550, 20) } @>.asayo \base;
//:--[20]
0 @>.asayo \sweepdir;
//:--[20]
{  XLine.kr(-0.1, -2, 60) } @>.asayo \sweepdir;
//:--[20]
{  XLine.kr(10, 0.01, 60) } @>.asayo \idiv;
//:--[20]
{  XLine.kr(0.1, 10, 60) } @>.asayo \idiv;
//:--[20]
-0.1 @>.asayo \sweepdir;
//:--[20]
32 @>.asayo \ma64;
//:--[20]
16 @>.asayo \ma64;
//:--[20]
8 @>.asayo \ma64;
//:--[20]
0.9 @>.asayo \soft;
//:--[20]
0.95 @>.asayo \soft;
//:--[20]
0.95 @>.asayo \tempo;
//:--[20]
20 @>.asayo \idiv;
//:--[20]
{ XLine.kr(200, 550, 20) } @>.asayo \base;
//:--[20]
10 @>.asayo \wet;
0.19 @>.asayo \soft;
0.39 @>.asayo \soft;
//:--[20]
1 @>.asayo \tempo;
5 @>.asayo \wet;
0.1 @>.asayo \wet;
0.01 @>.asayo \wet;
//:--[20]
{ Line.kr(20, 30, 60) } @>.asayo \idiv;
//:--[20]
{ XLine.kr(0.01, 1, 120) } @>.asayo \tempo; // par 3
//:--

{ Line.kr(10, 30, 60) }

0.15 @>.asayo \tempo;
15 @>.asayo \idiv;
25 @>.asayo \idiv;
30 @>.asayo \idiv;
10 @>.asayo \idiv;

1 @>.asayo \idiv;

5 @>.asayo \wet;

-0.1 @>.asayo \sweepdir;
-0.01 @>.asayo \sweepdir;

0.0 @>.asayo \sweepdir;
0.01 @>.asayo \sweepdir;
0.02 @>.asayo \sweepdir;


1.1 @>.asayo \tempo;
1.2 @>.asayo \tempo;
