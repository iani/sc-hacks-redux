(
// var hidur,hidur2,middur,middur2,kickdur,kickdur2,rockdur,rockdur2,adddur,noisebombdur,addnoisedur,addnoisedur2,himul,kickmul,fmbassmul,midmul,rockmul,addmul,noisebombmul,addnoisemul;
// hidur = [1/4, 1/4, 1/4, 1/4];
[1/4, 1/4, 1/4, 1/4] <!> \hidur;

// hidur2 = [1/4, 1/4, 1/4, 1/4, 1/8 , 1/8, 1/4, 1/4, 1/4, 1/4, 1/8, 1/8, 1/4, 1/16, 1/16, 1/16, 1/16];
[1/4, 1/4, 1/4, 1/4, 1/8 , 1/8, 1/4, 1/4, 1/4, 1/4, 1/8, 1/8, 1/4, 1/16, 1/16, 1/16, 1/16] <!> \hidur2;
// middur=[1,Rest(3)];
[1,Rest(3)] <!> \middur;
// middur2=[1/16, 1/16, 1/16, 1/16, Rest(1), 1, 1/4,Rest(2/4), 1/2, 1/2];
[1/16, 1/16, 1/16, 1/16, Rest(1), 1, 1/4,Rest(2/4), 1/2, 1/2] <!> \middur;
// kickdur=[1,1,1,1,1,1,1,1];
[1,1,1,1,1,1,1,1] <!> \kickdur;
// kickdur2=[1,1,1,1,1,1,1,1/4,Rest(1/4),1/4,1/4];
[1,1,1,1,1,1,1,1/4,Rest(1/4),1/4,1/4] <!> \kickdur2;
// rockdur=
//:
[Rest(1),1,Rest(1),Rest(1),Rest(1),1,Rest(1/2),1/2,Rest(1/2),1/4,1/4,Rest(1/2)
	,1/2,1,Rest(1),Rest(1),Rest(1),1,Rest(1/2),1/2,Rest(1)] <!> \rockdur;
//:
// rockdur2=[1/4,1/4,1/4,1/4,1/8,1/8,1/4,1/4,1/4,Rest(2/4),1/8,1/8,1/4];
[1/4,1/4,1/4,1/4,1/8,1/8,1/4,1/4,1/4,Rest(2/4),1/8,1/8,1/4] <!> \rockdur2;

// adddur=[Rest(7),Rest(1/2),1/4,1/4];
[Rest(7),Rest(1/2),1/4,1/4] <!> \adddur;

// noisebombdur=[Rest(1),1,Rest(5),1];
[Rest(1),1,Rest(5),1] <!> \noisebombdur;

[Rest(1/2),1/2] <!> \addnoisedur;
[Rest(2/3),1/3, Rest(2), Rest(1/2), 1/4, 1/4] <!> \addnoisedur2;
//:
0.2 <!> \himul;
0.5 <!> \kickmul;
0.2 <!> \fmbassmul;
0.4 <!> \midmul;
3 <!> \rockmul;
0.09 <!> \addmul;
0.2 <!> \noisebombmul;
0.3 <!> \addnoisemul;

//:
\hi +> (freq: [4000, 2000, 1500].prand, dur: [0.4, 0.8, 1.6].prand / 2, mul: ~himul)
+> \seqa;
//:
(dur: [0.4, 0.8, 1.6].prand / 3) ++> \seqa;

(freq: [200, 250, 300].pseq) ++> \seqa;

(mul: 0.01) ++> \seqa;
(mul: [0.01, 0.2, 0.01, 0.03].pseq) ++> \seqa;

nil +> \seqa;
\hi +> (freq: [4000, 2000, 1500].prand, dur: (1..100).normalize(0.1, 1).pseq, mul: ~himul) +> \seqa;

\hi +> (freq: [4000, 2000, 1500].prand, dur: (1..100).normalize(0.1, 1).reverse.pseq, mul: ~himul) +> \seqa;

(freq: 100) ++> \seqa;

nil +> \seqa;
//:
\fmbass +> (mul: 0.2, freq: 55) +> \seqfmbass;
\fmbass +> (mul: 0.2, freq: [55, 110].pseq, dur: [0.1, 0.2, 1.4].prand) +> \seqfmbass;
//:
\fmbass +> (mul: 0.2, freq: 55, dur: Pseq([2,Rest(1/2),1/2,Rest(1/4),3/4],inf)) +> \seqfmbass;
//:
nil +> \seqfmbass;
//:
\kick +> (freq: 60)
// // 	~fmbass=sp.par(Pbind(*[
// 	\mul: fmbassmul,
// 	\instrument: \fmbass,
// 	\freq: 55,
// 	\dur: Pseq([2,Rest(1/2),1/2,Rest(1/4),3/4],inf)
// ]));

	// sp.wait(32);

	~kick=sp.par(Pbind(*[
        instrument: \kick,
        freq: 60,
		mul: kickmul,
        dur: Pseq(kickdur, inf),
]));

	sp.wait(48);

	~mid=sp.par(Pbind(*[
		instrument: \mid,
		freq: 60,
		dur: Pseq(middur,inf),
		out: Prand([0,1,2,3,4,5,6],inf),
		mul: midmul
]));

	sp.wait(32);

	~rock=sp.par(Pbind(*[
		instrument: \rock,
		freq: 800,
		dur: Pseq(rockdur, inf),
		mul: rockmul
]));

	sp.wait(24);

	~add=sp.par(Pbind(*[
	    instrument: \add,
		freq: 400,
		dur: Pseq(adddur, inf),
		out: Prand([0,1,2,3,4,5,6],inf),
		mul: addmul
]));

	sp.wait(64);
	sp.suspend(~kick);//kickstop

	~noisebomb=sp.par(Pbind(*[
	    instrument: \noisebomb,
		freq: 300,
		dur: Pseq(noisebombdur, inf),
		mul: noisebombmul
]));

	sp.wait(32);//rockstop
	sp.suspend(~rock);

	~kick2=sp.par(Pbind(*[
        instrument: \kick,
        freq: 55,
        dur: Pseq(kickdur, inf),
		mul: kickmul
]));//kickrestart

	~addnoise=sp.par(Pbind(*[
		instrument: \addnoise,
		freq: 6000,
		dur: Pseq(addnoisedur, inf),
		mul: addnoisemul
]));

	sp.wait(40);

	~rock2=sp.par(Pbind(*[
		instrument: \rock,
		freq: 800,
		dur: Pseq(rockdur, inf),
		mul: rockmul
]));//rockrestart

	sp.wait(128);
	sp.suspend(~kick2);
	sp.suspend(~addnoise);
	sp.suspend(~fmbass);



	~kick3=sp.par(Pbind(*[
        instrument: \kick,
        freq: 60,
		mul: kickmul,
		dur: Pseq([1/2, 1/2, Rest(1/2), 1/2,Rest(1/2), 1/2, Rest(1/4), 3/4,  1/2, 1/2, Rest(1/2), 1/2, Rest(1/2), 1/4, 1/4, Rest(1/4), 1/4, 1/4, 1/4], inf)
]));
	sp.wait(32);
	sp.suspend(~rock2);

	~hi2=sp.par(Pbind(*[
		instrument: \hi,
		freq: 4000,
		dur: Pseq(hidur2, inf),
		out: Prand([0,1,2,3,4,5,6],inf),
		mul: himul
]));
	sp.wait(64);
	sp.suspend(~mid);

	~mid2=sp.par(Pbind(*[
		instrument: \mid,
		freq: 60,
		dur: Pseq(middur2,inf),
		out: Prand([0,1,2,3,4,5,6],inf),
		mul: midmul
]));
	sp.wait(16);
	~addnoise2=sp.par(Pbind(*[
		instrument: \addnoise,
		freq: 6000,
		dur: Pseq(addnoisedur2, inf),
		mul: addnoisemul
]));
	sp.wait(32);
	~rock3=sp.par(Pbind(*[
		instrument: \rock,
		freq: Prand([800,1000,500,2000],inf),
		dur: Pseq(rockdur2, inf),
		mul: rockmul,
		out: Prand([0,1,2,3,4,5,6],inf)
]));
	sp.wait(32);
	sp.suspend(~kick3);

	~kick4=sp.par(Pbind(*[
        instrument: \kick,
        freq: 60,
		mul: kickmul,
		dur: Pseq( [1/4,1/4,Rest(1/4),1/4,Rest(1),1,Rest(1/2),1/8,1/8,1/8,1/8,Rest(1/2),1/2], inf)
]));

	sp.wait(32);
	~fmbass2=sp.par(Pbind(*[
	\mul: fmbassmul,
	\instrument: \fmbass,
	\freq: 55,
	\dur: Pseq([Rest(1/2),1,Rest(1/2),1/2,Rest(1/4),3/4,Rest(1),3/2],inf)
]));
	sp.wait(132);
	sp.suspendAll;

	~fmbassrand=sp.par(Pbind(*[
	\mul: fmbassmul,
	\instrument: \fmbass,
	\freq: 55,
	\dur: Prand([Rest(1/2),1,Rest(1/2),1/2,Rest(1/4),3/4,Rest(1),3/2],inf)
]));

	sp.wait(20);

	~noisebombrand=sp.par(Pbind(*[
	    instrument: \noisebomb,
		freq: 300,
		dur: Prand(noisebombdur, inf),
		mul: noisebombmul
]));

	~addrand=sp.par(Pbind(*[
	    instrument: \add,
		freq: 400,
		dur: Prand(adddur, inf),
		mul: addmul,
		out: Prand([0,1,2,3,4,5,6],inf)
]));

	sp.wait(38);

	~kickrand=sp.par(Pbind(*[
        instrument: \kick,
        freq: 60,
		mul: kickmul,
		dur: Prand( [1/4,1/4,Rest(1/4),1/4,Rest(1),1,Rest(1/2),1/8,1/8,1/8,1/8,Rest(1/2),1/2], inf)
]));

	sp.wait(45);

	~addnoise2=sp.par(Pbind(*[
		instrument: \addnoise,
		freq: Prand([6000, 7000, 8000],inf),
		dur: Prand(addnoisedur2, inf),
		mul: addnoisemul
]));

	~hi2rand=sp.par(Pbind(*[
		instrument: \hi,
		freq: 4000,
		dur: Prand(hidur2, inf),
		out: Prand([0,1,2,3,4,5,6],inf),
		mul: himul
]));

	sp.wait(80);

	~rock3=sp.par(Pbind(*[
		instrument: \rock,
		freq: Prand([800,1000,500,2000],inf),
		dur: Prand(rockdur2, inf),
		mul: rockmul,
		out: Prand([0,1,2,3,4,5,6],inf)
]));
	sp.wait(20);

	~mid2=sp.par(Pbind(*[
		instrument: \mid,
		freq: 60,
		dur: Prand(middur2,inf),
		out: Prand([0,1,2,3,4,5,6],inf),
		mul: midmul
]));

	sp.wait(260);
	sp.suspendAll;

}).play(TempoClock(125 / 60));
)

//:
a.stop;
a.start;