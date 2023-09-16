/* 16 Sep 2023 00:19
Developing the score for Ghost Duet scene.

Based on pluck staircase by Nathaniel Virgo.
Original:

p=Impulse;play{mean({|i|Pluck.ar(LFSaw.ar([102,101]),x=p.ar(1,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),4,0.2)}!10)}

*/
//:
//:original - with named controls
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(\tempo.br(1),i/\idiv.br(10))+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,
				\sweepdir.br(1)
			)*64%1+1*200,x),\wet.br(4),
			\soft.br(0.2))
	}!10)
} +> \pluckstaircase1;
//customize:
0.9 @> \soft;
0.1 @> \wet;
0.5 @> \tempo;

//:

{ Line.kr(1, -1, 60) } @>.pluckstaircase \sweepdir;
//:
1.5 @> \tempo;
//:----- vary to use as second source.
//:var 0
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(\tempo.br(1),i/\idiv.br(10))+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,
				\sweepdir.br(1)
			)*64%1+1*200,x),\wet.br(4),
			\soft.br(0.2))
	}!10)
} +> \psc1;
//customize:
0.9 @>.psc1 \soft;
1.1 @>.psc1 \wet;
0.5 @>.psc1 \tempo;
//:var 0b
//:----- vary to use as second source.
//:var 0
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(\tempo.br(1),i/\idiv.br(10))+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,
				\sweepdir.br(1)
			)*64%1+1*100,x),\wet.br(4),
			\soft.br(0.2))
	}!10)
} +> \psc1b;
//customize:
0.9 @>.psc1b \soft;
1.1 @>.psc1b \wet;
0.5 @>.psc1b \tempo;

//:var 1
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101] / 10),
			x=p.ar(\tempo.br(1),i/\idiv.br(10))+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,
				\sweepdir.br(1)
			)*64%1+1*200,x),\wet.br(4),
			\soft.br(0.2))
	}!10)
} +> \pluckstaircase2;
0.9 @>.pluckstaircase2 \soft;
0.1 @>.pluckstaircase2 \wet;
//:var 2
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101] / 10),
			x=p.ar(\tempo.br(1),i/\idiv.br(10))+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,
				\sweepdir.br(1)
			)*16%1+1*100,x),\wet.br(4),
			\soft.br(0.2))
	}!10)
} +> \pluckstaircase3;
0.9 @>.pluckstaircase3 \soft;
0.1 @>.pluckstaircase3 \wet;
