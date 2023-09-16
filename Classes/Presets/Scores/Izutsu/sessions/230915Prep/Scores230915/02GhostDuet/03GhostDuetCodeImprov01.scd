/* 16 Sep 2023 11:32
Improvising the score step by step.
Should keep the good parts ...
*/

//:Starting point - original - with named controls, customized
// Works from the start because br controls are not set in the playfunc
// So the settings before the playfunc are not overwritten
// initialize all control parameters first
0.9 @>.pluckstaircase1 \soft;
0.1 @>.pluckstaircase1 \wet;
0.5 @>.pluckstaircase1 \tempo;
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
} +> \pluckstaircase1;
//:adding more controls in the parameter setting section
// initialize all control parameters first
0.5 @>.pluckstaircase1 \tempo;
10 @>.pluckstaircase1 \idiv;
1 @>.pluckstaircase1 \sweepdir;
0.1 @>.pluckstaircase1 \wet;
0.9 @>.pluckstaircase1 \soft;
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
} +> \pluckstaircase1;
//:Adding more controls to the function and the parameter setting section
// initialize all control parameters first
0.5 @>.pluckstaircase1 \tempo;
10 @>.pluckstaircase1 \idiv;
1 @>.pluckstaircase1 \sweepdir;
0.1 @>.pluckstaircase1 \wet;
0.9 @>.pluckstaircase1 \soft;
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
} +> \pluckstaircase1;
//:
