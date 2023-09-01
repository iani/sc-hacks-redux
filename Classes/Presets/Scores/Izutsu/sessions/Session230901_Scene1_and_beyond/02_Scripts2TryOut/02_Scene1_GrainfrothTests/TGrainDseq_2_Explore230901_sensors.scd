//TGrains with Dseq for rate. To explore
//From: https://doc.sccode.org/Classes/TGrains.html
//:sensor controls (for all snippets)
{ \x7.sr.exp(0.2, 500) } @>.test \trate;
{ \z8.sr.lin(0.0, 1.0) } @>.test \pos;
//:weddell
{
    var trate, dur, rate, b, pos;
	// b = \default.buf;
	// b = \crickets.buf;
	b = \crickets.buf;
    // trate = MouseY.kr(2,200,1);
    // trate = MouseY.kr(0.2,20,1);
    trate = \trate.br(1);
	pos = \pos.br(0.5);
    dur = 16 / trate;
    rate = Dseq([0.2, 0.4, 0.5, 0.1, 0.1, 0.1, 0.1, 0.5, 0.25, 0.5, 0.25, 0.5], inf);
    TGrains.ar(2, Impulse.ar(trate), b, rate * \x7.sr.exp(0.5, 5), BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:crickets
{
    var trate, dur, rate, b, pos;
	// b = \default.buf;
	b = \crickets.buf;
	// b = \weddell.buf;
    // trate = MouseY.kr(2,200,1);
    trate = \trate.br(1);
	pos = \pos.br(0.5);
    // trate = MouseY.kr(0.2,20,1);
    dur = 16 / trate;
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    rate = Dseq([0.2, 0.4, 0.5, 0.1, 0.1, 0.1, 0.1, 0.5, 0.25, 0.5, 0.25, 0.5], inf);
    TGrains.ar(2, Impulse.ar(trate), b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:trate = 20
{
    var trate, dur, rate, b, pos;
	// b = \default.buf;
	// b = \crickets.buf;
	b = \weddell.buf;
    trate = \trate.br(1);
	pos = \pos.br(0.5);
    // trate = MouseY.kr(2,200,1);
    // trate = 20; // MouseY.kr(0.2,20,1);
    dur = 16 / trate;
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    // rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    rate = Dseq([0.2, 0.4, 0.5, 0.1, 0.1, 0.1, 0.1, 0.5, 0.25, 0.5, 0.25, 0.5], inf);
    TGrains.ar(2, Impulse.ar(trate), b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:create buf for Pindex!!!!
\c.dbuf((1..10).pyramid.stutter(3) / 5);
//:create buf for Pindex!!!!
\c.dbuf((1..10).pyramid.stutter(3) / 20);
//:fill with 1
\c.dbuf({ 1 } ! \c.dbuf.numFrames);
//:fill with 0.25
\c.dbuf({ 0.25 } ! \c.dbuf.numFrames);
//:fill with [0.25, 0.5, 0.25, 0.5 ...]
\c.dbuf(({ [0.25, 0.5] } ! \c.dbuf.numFrames).flat[..\c.dbuf.numFrames - 1]);
//:fill with [0.1, 0.2, 0.1, 0.2 ...]
\c.dbuf(({ [0.1, 0.2] } ! \c.dbuf.numFrames).flat[..\c.dbuf.numFrames - 1]);
//:fill with [1, 2, 1, 2 ...]
\c.dbuf(({ [1, 2] } ! \c.dbuf.numFrames).flat[..\c.dbuf.numFrames - 1]);
//:Try Pindex with allocated buf
{
    var trate, dur, rate, b, trig, ind, pos;
	b = \weddell.buf;
    // trate = 20; // MouseY.kr(0.2,20,1);
    trate = \trate.br(1);
	pos = \pos.br(0.5);
    dur = 16 / trate;
	trig = Impulse.kr(trate);
	ind = Dseq((0..(\c.dbuf.numFrames - 1)), inf);
	rate = Demand.kr(trig, 0, Dbufrd(\c.dbuf, ind));
    TGrains.ar(2, trig, b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:Try with crickets
{
    var trate, dur, rate, b, trig, ind, pos;
	b = \crickets.buf;
    // trate = 20; // MouseY.kr(0.2,20,1);
    trate = \trate.br(1);
	pos = \pos.br(0.5);
    dur = 16 / trate;
	trig = Impulse.kr(trate);
	ind = Dseq((0..(\c.dbuf.numFrames - 1)), inf);
	rate = Demand.kr(trig, 0, Dbufrd(\c.dbuf, ind));
    TGrains.ar(2, trig, b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:Try with crickets - trate 1
{
    var trate, dur, rate, b, trig, ind, pos;
	b = \crickets.buf;
    // trate = 1; // MouseY.kr(0.2,20,1);
    trate = \trate.br(1);
	pos = \pos.br(0.5);
    dur = 16 / trate;
	trig = Impulse.kr(trate);
	ind = Dseq((0..(\c.dbuf.numFrames - 1)), inf);
	rate = Demand.kr(trig, 0, Dbufrd(\c.dbuf, ind));
    TGrains.ar(2, trig, b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:Watch mouse vales
{  MouseX.kr(0,BufDur.kr(\crickets.buf)).poll; } +> \mousex;
//:Fix pos to *selected* polled mouse value
{
    var trate, dur, rate, b, trig, ind, pos;
	b = \crickets.buf;
    // trate = 1; // MouseY.kr(0.2,20,1);
    trate = \trate.br(1);
	pos = \pos.br(0.5);
    dur = 16 / trate;
	trig = Impulse.kr(trate);
	ind = Dseq((0..(\c.dbuf.numFrames - 1)), inf);
	rate = Demand.kr(trig, 0, Dbufrd(\c.dbuf, ind));
    TGrains.ar(2, trig, b, rate, -53.1794, dur, Dseq([-1, 1], inf), 0.1, 2);
    TGrains.ar(2, trig, b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:============== PREPARE RESET of Dbufrd
//:Prep test: faster trate for quicker reaction
//also use a simple rate dbuf for clarity:
\c.dbuf((1..\c.dbuf.numFrames - 1).normalize + 0.2);
{
    var trate, dur, rate, b, trig, ind, pos;
	b = \crickets.buf;
    // trate = 20; // MouseY.kr(0.2,20,1);
    trate = \trate.br(1);
	pos = \pos.br(0.5);
    dur = 16 / trate;
	trig = Impulse.kr(trate);
	ind = Dseq((0..(\c.dbuf.numFrames - 1)), inf);
	rate = Demand.kr(trig, 0, Dbufrd(\c.dbuf, ind));
    TGrains.ar(2, trig, b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:Add trigger:
//also use a simple rate dbuf for clarity:
\c.dbuf((1..\c.dbuf.numFrames - 1).normalize + 0.2);
{
    var trate, dur, rate, b, trig, ind, reset, pos;
	b = \crickets.buf;
    // trate = 20; // MouseY.kr(0.2,20,1);
    trate = \trate.br(1);
	pos = \pos.br(0.5);
    dur = 16 / trate;
	trig = Impulse.kr(trate);
	ind = Dseq((0..(\c.dbuf.numFrames - 1)), inf);
	reset = \t_reset.kr(0);
	rate = Demand.kr(trig, reset, Dbufrd(\c.dbuf, ind));
    TGrains.ar(2, trig, b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:Template for resetting testing set - intermediate
//:(from scdoc) - posts only when setting the trigs below.
a = { |t_trig, t_reset|
    var    d = Demand.kr(t_trig + t_reset, t_reset, Dseries(0, 1, inf));
    d.poll(t_trig + t_reset);
    0.0;
}.play
//:
a.set(\t_trig, 1); // do this a few times -- the demand value should increase by 1 each time
a.set(\t_reset, 1);    // goes immediately back to 0
//:Testing in envir player framework:
{ |t_trig, t_reset|
    var    d = Demand.kr(t_trig + t_reset, t_reset, Dseries(0, 1, inf));
    d.poll(t_trig + t_reset);
    0.0;
} +> \rtest;
//:testing set operator.
1 +>.rtest \t_trig;
//:
1 +>.rtest \t_reset;
//:Prepare for use with reset:
\c.dbuf((1..100).normalize + 0.5);
{
    var trate, dur, rate, b, trig, ind, reset, pos;
	b = \crickets.buf;
    // trate = 1; // MouseY.kr(0.2,20,1);
    trate = \trate.br(1);
	pos = \pos.br(0.5);
    dur = 16 / trate;
	trig = Impulse.kr(trate);
	ind = Dseq((0..(\c.dbuf.numFrames - 1)), inf);
	reset = \t_reset.kr(0);
	rate = Demand.kr(trig + reset, reset, Dbufrd(\c.dbuf, ind));
    TGrains.ar(2, trig, b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
} +> \test;
//:reset
1 +>.test \t_reset;
