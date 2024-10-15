/* 16 Sep 2023 11:32
Improvising the score step by step.
Should keep the good parts ...
*/

//:Starting point - original - with named controls, customized
// Works from the start because br controls are not set in the playfunc
// So the settings before the playfunc are not overwritten
// initialize all control parameters first
0.9 @>.ps1 \soft;
0.1 @>.ps1 \wet;
0.5 @>.ps1 \tempo;
{
	var p;
	p=Impulse;
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(
				\tempo.br, // \tempo.br(1)
				i/
				\idiv.br(10) // \idiv.br(10)
			)
			+p.ar(0),
			1,
			1/
			Latch.ar(
				1.015
				**Sweep.ar(
					0,
					\sweepdir.br(1) // \sweepdir.br(1)
				)
				*64
				% 1
				+ 1
				* 200,
				x
			),
			\wet.br, // \wet.br(4),
			\soft.br // \soft.br(0.2)
		)
	}!10)
} +> \ps1;
//:adding more controls in the parameter setting section
// initialize all control parameters first
0.5 @>.ps1 \tempo;
10 @>.ps1 \idiv;
1 @>.ps1 \sweepdir;
0.1 @>.ps1 \wet;
0.9 @>.ps1 \soft;
{
	var p;
	p=Impulse;
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(
				\tempo.br, // \tempo.br(1)
				i/
				\idiv.br // \idiv.br(10)
			)
			+p.ar(0),
			1,
			1/
			Latch.ar(
				1.015
				**Sweep.ar(
					0,
					\sweepdir.br // \sweepdir.br(1)
				)
				*64
				% 1
				+ 1
				* 200,
				x
			),
			\wet.br, // \wet.br(4),
			\soft.br // \soft.br(0.2)
		)
	}!10)
} +> \ps1;
//:try changing some of the controls:
0.9 @>.ps1 \tempo;
20 @>.ps1 \idiv;
-3.1 @>.ps1 \sweepdir;
0.1 @>.ps1 \wet;
0.9 @>.ps1 \soft;
//:Sine~ continuous change of one parameter:
{ SinOsc.kr(15.reciprocal, pi).range(0.25, 5) } @>.ps1 \tempo;
//:Adding more controls to the function and the parameter setting section
// initialize all control parameters first
101 @>.ps1 \ls1;  // par 1
102 @>.ps1 \ls2;  // par 2
0.5 @>.ps1 \tempo; // par 3
10 @>.ps1 \idiv; // par 4
// 1.015 @>.ps1 \lin;
64 @>.ps1 \ma64;
200 @>.ps1 \base;
1 @>.ps1 \sweepdir; // par
// 0.1 @>.ps1 \wet;
10 @>.ps1 \wet;
0.9 @>.ps1 \soft;
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
} +> \ps1;
//:some interesting controls for above
10.5 @>.ps1 \wet;
//:tempo ...
0.5 @>.ps1 \tempo;
1 @>.ps1 \tempo;
1.1 @>.ps1 \tempo;
1.2 @>.ps1 \tempo;
1.3 @>.ps1 \tempo;
1.4 @>.ps1 \tempo;
3 @>.ps1 \tempo;
//:sweepdir ...
2 @>.ps1 \sweepdir;
1 @>.ps1 \sweepdir;
0 @>.ps1 \sweepdir;
-1 @>.ps1 \sweepdir;
-2 @>.ps1 \sweepdir;
-3 @>.ps1 \sweepdir;
-4 @>.ps1 \sweepdir;
-5 @>.ps1 \sweepdir;
0.5 @>.ps1 \sweepdir;
//:idiv
10 @>.ps1 \idiv;
20 @>.ps1 \idiv;
30 @>.ps1 \idiv;
15 @>.ps1 \idiv;

9 @>.ps1 \idiv;
8 @>.ps1 \idiv;
7 @>.ps1 \idiv;
6 @>.ps1 \idiv;
5 @>.ps1 \idiv;
4 @>.ps1 \idiv;
1 @>.ps1 \idiv;
2 @>.ps1 \idiv;
// note: ls1 and ls2 may be good to control via sensors!
1 @>.ps1 \ls1;
0.1 @>.ps1 \ls2;
0.01 @>.ps1 \ls1;
0.001 @>.ps1 \ls2;
0.0001 @>.ps1 \ls1;
1001 @>.ps1 \ls1;
2001 @>.ps1 \ls2;
//:pitch base control (bass - treble)
// 70 @>.ps1 \ma64;
100 @>.ps1 \base;
50 @>.ps1 \base;
40 @>.ps1 \base;
30 @>.ps1 \base;
200 @>.ps1 \base;
300 @>.ps1 \base;
400 @>.ps1 \base;
500 @>.ps1 \base;
600 @>.ps1 \base;
700 @>.ps1 \base;
//:
{ Line.kr(102, 1, 60) } @>.ps1 \ls1;
{ Line.kr(101, 0.5, 60) } @>.ps1 \ls2;

30 @>.ps1 \ls2;
500 @>.ps1 \ls1;
300 @>.ps1 \ls2;
200 @>.ps1 \ls1;
