/* 16 Sep 2023 14:25
Preparing to add second voice with different range
*/
//:===== ASAYO ======
// initialize all control parameters first
101 @>.asayo \ls1;  // par 1
102 @>.asayo \ls2;  // par 2
0.5 @>.asayo \tempo; // par 3
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
	}!10)
} +> \asayo;


//:====== CONTROLS SECTION ======
//:some interesting controls for above
10.5 @>.asayo \wet;
//:tempo ...
0.5 @>.asayo \tempo;
1 @>.asayo \tempo;
1.1 @>.asayo \tempo;
1.2 @>.asayo \tempo;
1.3 @>.asayo \tempo;
1.4 @>.asayo \tempo;
3 @>.asayo \tempo;
//:sweepdir ...
2 @>.asayo \sweepdir;
1 @>.asayo \sweepdir;
0 @>.asayo \sweepdir;
-1 @>.asayo \sweepdir;
-2 @>.asayo \sweepdir;
-3 @>.asayo \sweepdir;
-4 @>.asayo \sweepdir;
-5 @>.asayo \sweepdir;
0.5 @>.asayo \sweepdir;
//:idiv
10 @>.asayo \idiv;
20 @>.asayo \idiv;
30 @>.asayo \idiv;
15 @>.asayo \idiv;

9 @>.asayo \idiv;
8 @>.asayo \idiv;
7 @>.asayo \idiv;
6 @>.asayo \idiv;
5 @>.asayo \idiv;
4 @>.asayo \idiv;
1 @>.asayo \idiv;
2 @>.asayo \idiv;
// note: ls1 and ls2 may be good to control via sensors!
1 @>.asayo \ls1;
0.1 @>.asayo \ls2;
0.01 @>.asayo \ls1;
0.001 @>.asayo \ls2;
0.0001 @>.asayo \ls1;
1001 @>.asayo \ls1;
2001 @>.asayo \ls2;
//:pitch base control (bass - treble)
// 70 @>.asayo \ma64;
100 @>.asayo \base;
50 @>.asayo \base;
40 @>.asayo \base;
30 @>.asayo \base;
200 @>.asayo \base;
300 @>.asayo \base;
400 @>.asayo \base;
500 @>.asayo \base;
600 @>.asayo \base;
700 @>.asayo \base;
//:
{ Line.kr(102, 1, 60) } @>.asayo \ls1;
{ Line.kr(101, 0.5, 60) } @>.asayo \ls2;

30 @>.asayo \ls2;
500 @>.asayo \ls1;
300 @>.asayo \ls2;
200 @>.asayo \ls1;
