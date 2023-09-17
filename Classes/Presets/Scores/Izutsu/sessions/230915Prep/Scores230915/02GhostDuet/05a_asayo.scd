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