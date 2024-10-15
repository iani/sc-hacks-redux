// Exporting 13 snippets on 230917_194221
//Source: [ 230917_193251, 230917_193336, 230917_193426, 230917_193516, 230917_193606, 230917_193656, 230917_193745 ]
//:--[13203.623501792]
[ '/code', "//:--[60] ===== ASAYO ======
// initialize all control parameters first
101 @>.asayo \\ls1;  // par 1
102 @>.asayo \\ls2;  // par 2
0.01 @>.asayo \\tempo; // par 3
10 @>.asayo \\idiv; // par 4
// 1.015 @>.asayo \\lin;
64 @>.asayo \\ma64;
200 @>.asayo \\base;
1 @>.asayo \\sweepdir; // par
// 0.1 @>.asayo \\wet;
10 @>.asayo \\wet;
0.9 @>.asayo \\soft;
{
	var p;
	p=Impulse;
	mean({|i|
		Pluck.ar(
			LFSaw.ar([
				// \\ls1.br(102),
				\\ls1.br, // par 1
				\\ls2.br  // par 2
				// \\ls2.br(101)
			]),
			x=p.ar(
				\\tempo.br, // \\tempo.br(1) // par 3
				i/
				\\idiv.br // \\idiv.br(10) // par 4
			)
			+p.ar(0) + \\start.tr(0),
			1,
			1/
			Latch.ar(
				// \\lin.br(1.015)  // par 5???
				1.015
				**Sweep.ar(
					0,
					\\sweepdir.br // \\sweepdir.br(1)
				)
				* \\ma64.br // 64
				% 1
				+ 1
				* \\base.br, // 200,
				x
			),
			\\wet.br, // \\wet.br(4),
			\\soft.br // \\soft.br(0.2)
		)
	}!10) * Env([0.1, 0.15, 1], [1.5, 2.01], 5).kr;
} +> \\asayo;
" ]
//:--[13220.406998292]
[ '/code', "{ XLine.kr(0.01, 1, 120) } @>.asayo \\tempo; // par 3" ]
//:--[13271.652854084]
[ '/code', "15 @>.asayo \\idiv;" ]
//:--[13322.169092917]
[ '/code', "{ Line.kr(20, 30, 60) } @>.asayo \\idiv;" ]
//:--[13343.592710542]
[ '/code', "5 @>.asayo \\wet;" ]
//:--[13371.442626709]
[ '/code', "-0.1 @>.asayo \\sweepdir;" ]
//:--[13385.221836875]
[ '/code', "-0.01 @>.asayo \\sweepdir;" ]
//:--[13413.704006625]
[ '/code', "1.1 @>.asayo \\tempo;" ]
//:--[13425.474994375]
[ '/code', "1.2 @>.asayo \\tempo;" ]
//:--[13439.029537834]
[ '/code', "10 @>.asayo \\idiv;" ]
//:--[13451.923241959]
[ '/code', "0.0 @>.asayo \\sweepdir;" ]
//:--[13457.846093667]
[ '/code', "0.01 @>.asayo \\sweepdir;" ]
//:--[13472.286590292]
[ '/code', "0.02 @>.asayo \\sweepdir;" ]

//the end

