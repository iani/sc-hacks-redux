//TGrains with Dseq for rate. To explore
//From: https://doc.sccode.org/Classes/TGrains.html
//:weddell
{
    var trate, dur, rate, b;
	// b = \default.buf;
	// b = \crickets.buf;
	b = \weddell.buf;
    // trate = MouseY.kr(2,200,1);
    trate = MouseY.kr(0.2,20,1);
    dur = 16 / trate;
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    rate = Dseq([0.2, 0.4, 0.5, 0.1, 0.1, 0.1, 0.1, 0.5, 0.25, 0.5, 0.25, 0.5], inf);
    TGrains.ar(2, Impulse.ar(trate), b, rate, MouseX.kr(0,BufDur.kr(b)), dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:crickets
{
    var trate, dur, rate, b;
	// b = \default.buf;
	b = \crickets.buf;
	// b = \weddell.buf;
    // trate = MouseY.kr(2,200,1);
    trate = MouseY.kr(0.2,20,1);
    dur = 16 / trate;
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    rate = Dseq([0.2, 0.4, 0.5, 0.1, 0.1, 0.1, 0.1, 0.5, 0.25, 0.5, 0.25, 0.5], inf);
    TGrains.ar(2, Impulse.ar(trate), b, rate, MouseX.kr(0,BufDur.kr(b)), dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:trate = 20
{
    var trate, dur, rate, b;
	// b = \default.buf;
	// b = \crickets.buf;
	b = \weddell.buf;
    // trate = MouseY.kr(2,200,1);
    trate = 20; // MouseY.kr(0.2,20,1);
    dur = 16 / trate;
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    rate = Dseq([0.2, 0.4, 0.5, 0.1, 0.1, 0.1, 0.1, 0.5, 0.25, 0.5, 0.25, 0.5], inf);
    TGrains.ar(2, Impulse.ar(trate), b, rate, MouseX.kr(0,BufDur.kr(b)), dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:create buf for Pindex!!!!
\c.dbuf((1..10).pyramid.stutter(3) / 10);
//:Try Pindex with allocated buf
{
    var trate, dur, rate, b, trig, ind;
	b = \weddell.buf;
    trate = 20; // MouseY.kr(0.2,20,1);
    dur = 16 / trate;
	trig = Impulse.kr(trate);
	ind = Dseq((0..(\c.dbuf.numFrames - 1)), inf);
	rate = Demand.kr(trig, 0, Dbufrd(\c.dbuf, ind));
    // rate = Dseq([0.2, 0.4, 0.5, 0.1, 0.1, 0.1, 0.1, 0.5, 0.25, 0.5, 0.25, 0.5], inf);
    TGrains.ar(2, trig, b, rate, MouseX.kr(0,BufDur.kr(b)), dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:Fill rate buf with different values
(1 ! \c.dbuf.size) toBuf: \c;