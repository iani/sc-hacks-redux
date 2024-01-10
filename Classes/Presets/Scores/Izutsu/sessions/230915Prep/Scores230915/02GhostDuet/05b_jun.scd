//:===== JUN ======
// initialize all control parameters first
101 @>.jun \ls1;  // par 1
102 @>.jun \ls2;  // par 2
0.5 @>.jun \tempo; // par 3
10 @>.jun \idiv; // par 4
// 1.015 @>.jun \lin;
64 @>.jun \ma64;
200 @>.jun \base;
1 @>.jun \sweepdir; // par
// 0.1 @>.jun \wet;
10 @>.jun \wet;
0.9 @>.jun \soft;
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
} +> \jun;