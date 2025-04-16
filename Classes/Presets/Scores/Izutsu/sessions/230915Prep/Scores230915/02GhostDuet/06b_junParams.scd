//code
//:--[60] initialize all control parameters first
// 101 @>.jun \ls1;  // par 1
// 102 @>.jun \ls2;  // par 2
2.01 @>.jun \ls1;
2.02 @>.jun \ls2;
0.5 @>.jun \tempo; // par 3
5 @>.jun \idiv; // par 4
// 1.015 @>.jun \lin;
64 @>.jun \ma64;
50 @>.jun \base;
10 @>.jun \sweepdir; // par
// 0.1 @>.jun \wet;
30 @>.jun \wet;
0.7 @>.jun \soft;
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
			+p.ar(0),
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
	}!10) * Env([0, 0.1, 0.25], [1, 1], 5).kr;
} +> \jun
//:--[50] tempo
{ XLine.kr(0.5, 5, 50) } @>.jun \tempo; // par
//:--[50] idiv
{ XLine.kr(5, 15, 50) } @>.jun \idiv; // par
//:--[50] base
{ XLine.kr(50, 150, 50) } @>.jun \base; // par
//:--[10] idiv 5
10 @>.Jun \idiv;
//--[10] idiv -50 par 4
1 @>.jun \sweepdir; // par
//:--[10] base 150
50 @>.jun \base;
//:--[10] \sweepdir -2
-2 @>.jun \sweepdir;
//:--[10] sweepdir 50
50 @>.jun \sweepdir; // par
//:--[10] ls1, ls2
20.1 @>.jun \ls1;
20.2 @>.jun \ls2;
//:--[10] ls1, ls2
{ XLine.kr(20.1, 101, 20) } @>.jun \ls1;
{ XLine.kr(20.2, 102, 20) } @>.jun \ls2;
//:--[10] SENSOR CONTROLS ---------- SENSOR CONTROLS
{ \x6.sr.lag(2).lin(0.1, 50.0) } @>.jun \wet;
{ \z6.sr.lag(2).lin(0.1, 0.9) } @>.jun \soft;
//:--[10] TEST SENSOR CONTROL LIMITS
0.1 @>.jun \wet;
50.0 @>.jun \wet;
25 @>.jun \wet;
0.1 @>.jun \soft;
0.9 @>.jun \soft;
25 @>.jun \wet;
5 @>.jun \tempo;
1 @>.jun \sweepdir;
-1 @>.jun \sweepir;
5 @>.jun \tempo;
0.2 @>.jun \tempo;
20 @>.jun \idiv;