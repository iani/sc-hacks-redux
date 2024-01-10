/* 15 Sep 2023 21:12

*/
/* by  nathaniel virgo. pluck staircase */
/*
p=Impulse;play{mean({|i|Pluck.ar(LFSaw.ar([102,101]),x=p.ar(1,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),4,0.2)}!10)}
*/
/*
p=Impulse;
play{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(1,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),4,0.2)
	}!10)
}
*/
//:original
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(1,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),4,0.2)
	}!10)
} +> \pluckstaircase;
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
} +> \pluckstaircase;
0.9 @> \soft;

//:softness of pluck
0.9 @> \soft;
//:wetness
0.1 @> \wet;
//:tempo (very fast)
5 @> \tempo;
//:medium slow tempo
0.5 @> \tempo;
//:accelerating tempo
{ Line.kr(0.5, 5, 50) } @> \tempo;
//:
0.5 @> \tempo;
//:
nil @> \tempo;
//:sweep direction
0 @> \sweepdir;
//:sweep down very slowly
-0.01 @> \sweepdir;
//:??? idiv?
5 @> \idiv;
4 @>\tempo;
3 @>\sweepdir;
//:downward (horizontal mirror)
//
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(1,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,-1.0)*64%1+1*200,x),4,0.2)
	}!10)
} +> \pluckstaircase;
//:downward (horizontal mirror) + higher start
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(1,i/10)+p.ar(0),1,0.5/Latch.ar(1.015**Sweep.ar(0,-1.0)*64%1+1*200,x),4,0.2)
	}!10)
} +> \pluckstaircase;
//:lower start + higher rise ratio
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(1,i/10)+p.ar(0),1,2.5/Latch.ar(1.015**Sweep.ar(0,2)*64%1+1*200,x),4,0.2)
	}!10)
} +> \pluckstaircase;
//:staccato
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(1,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),0.4,0.2)
	}!10)
} +> \pluckstaircase;
//:super staccato + softer plectrum + double tempo
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(2,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),0.1,0.9)
	}!10)
} +> \pluckstaircase;
//:softer plectrum
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(1,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),4,0.9)
	}!10)
} +> \pluckstaircase;
//:10 times slower
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(0.1,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),4,0.2)
	}!10)
} +> \pluckstaircase;
//:
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(0.1,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),4,0.2)
	}!10)
} +> \pluckstaircase;

{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(0.2,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),4,0.2)
	}!10)
} +> \pluckstaircase2;
//:
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(0.1,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),4,0.2)
	}!10)
} +> \pluckstaircase;

{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(0.2,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1x+1*200,x),4,0.2)
	}!10)
} +> \pluckstaircase2;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(0.3,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),4,0.2)
	}!10)
} +> \pluckstaircase3
//:
var p;
p=Impulse;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(0.1,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,1)*64%1+1*200,x),4,0.9)
	}!10)
} +> \pluckstaircase;

{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(0.2,i/10)+p.ar(0),1,0.2/Latch.ar(1.015**Sweep.ar(0,-2)*64%1x+1*200,x),4,0.9)
	}!10)
} +> \pluckstaircase2;
{
	mean({|i|
		Pluck.ar(
			LFSaw.ar([102,101]),
			x=p.ar(0.3,i/10)+p.ar(0),1,1/Latch.ar(1.015**Sweep.ar(0,-1)*64%1+1*200,x),4,0.9)
	}!10)
} +> \pluckstaircase3
//:§