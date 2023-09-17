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
			LFSaw.ar([ // IN: Sound source
				// \ls1.br(102),
				\ls1.br, // par 1
				\ls2.br  // par 2
				// \ls2.br(101)
			]),
			x=p.ar( // trigger: Impulse.ar
				// trigger rate
				\tempo.br, // \tempo.br(1) // par 3
				i/ // phase
				\idiv.br // \idiv.br(10) // par 4
			)
			+p.ar(0), // initial trigger???
			1,  // max delay time
			1/  // delaytime
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
	}!10)
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
