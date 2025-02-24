/*  6 Feb 2021 00:16
Trying index as a way to get values from a buffer.
To be combined with Tduty.
*/

//:
{
	var freq;
	freq = Index.kr(
		LocalBuf.newFrom([ 200, 300, 400, 500, 600, 800 ]),
		LFSaw.kr(1/4, -pi/4).range(0, 6),
	);
    SinOsc.ar(
        Lag.kr([freq, freq * 1.25], 0.5),
        0,
        0.01
    );
}.play;
//:
// amplitude changes
{
	var trig;
	trig = TDuty.ar(
		Dseq([0.1, 0.2, 0.4, 0.3], inf), // demand ugen as durations
		0,
		Dseq([0.1, 0.4, 0.01, 0.5, 1.0], inf) // demand ugen as amplitude
	);
	Ringz.ar(trig, 1000, 0.1)

}.play;
//:================================================================
//:From the help file: List of demand ugens as value source
{
    var freq, trig, reset, seq1, seq2;
    trig = Impulse.kr(10);
    seq1 = Drand([72, 75, 79, 82] - 12, inf).midicps;
    seq2 = Dseq([72, 75, 79, Drand([82,84,86])], inf).midicps;
    freq = Demand.kr(trig, 0, [seq1, seq2]);
    SinOsc.ar(freq + [0,0.7]).cubed.cubed * 0.1;
}.play;
//:Combine TDuty and Duty
/* Note interesting patterns arise when we poll a single demand ugen for durations
from two Duty-like ugens (here: one Duty and one TDuty).
This is because each Duty-like ugen fetches the next value of the duration-source
ugen in sequence. So: 

freq -> (dur: 0.2, freq: 200)
amp -> (dur: 0.4, amp: 0.01);
freq -> (dur: 0.2, freq: 400);
amp -> (dur: 0.4, amp: 0.015);
freq -> (dur: 0.2, freq: 200)
amp -> (dur: 0.4, amp: 0.20);
freq -> (dur: 0.2, freq: 400);
amp -> (dur: 0.4, amp: 0.025);

The above have to be sorted like 2 parallel duration streams: 

freq: ab ???? (TODO: need to calculate this more carefully)
amp:  tt????

Where a = 200 and b = 400, and t = trigger and - = no trigger
*/
{
	var freq, dt, amp;
	//	dt = Dseq([0.1, 0.2, 0.4], inf); // demand ugen as durations
	// dt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	dt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	// contrast with this also:
	// dt = Dseq([0.1, 0.8], inf); // demand ugen as durations
	// freq = Duty.kr(dt, 0, Dseq([204, 400, 201, 502, 300, 200], inf));
	freq = Duty.kr(dt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(dt, 0, Dseq((1..20).normalize / 11 + 0.01, inf));
	SinOsc.ar(Lag.kr(freq, 0.01) * [1, 1.01]) * Decay.kr(amp);
}.playIn;
//:Observe difference when using a separate dur ugen for each of the Duty ugens:
{  // Time patterns as expected (not polled in parallel):
	var freq, freqdt, ampdt, amp;
	freqdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	ampdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(freqdt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(ampdt, 0, Dseq((1..20).normalize / 11 + 0.01, inf));
	SinOsc.ar(Lag.kr(freq, 0.01) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:================================================================
//: For clearer behavior, use separate Demand ugens for TDuty and Duty
{  // Time patterns as expected (not polled in parallel):
	var freq, freqdt, ampdt, amp;
	freqdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	ampdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(freqdt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(ampdt, 0, Dseq((1..20).normalize / 11 + 0.01, inf));
	SinOsc.ar(Lag.kr(freq, 0.01) * [1, 1.01]) * Decay.kr(amp);
}.splay;

//: Interesting patterns can also be created this way 
{  
	var freq, freqdt, ampdt, amp;
	freqdt = Dseq([0.2, 0.4, 0.4], inf); // demand ugen as durations
	ampdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(freqdt, 0, Dseq([200, 400], inf));
	amp = TDuty.kr(ampdt, 0, Dseq((1..20).normalize / 11 + 0.01, inf));
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:Using Lag for Gamaka-like portamento
{  
	var freq, freqdt, ampdt, amp;
	freqdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	ampdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(freqdt, 0, Dseq([200, 400, 500, 600, 700], inf));
	amp = TDuty.kr(ampdt, 0, Dseq((1..20).normalize / 11 + 0.01, inf));
	SinOsc.ar(Lag.kr(freq, 0.3) * [1, 1.01]) * Decay.kr(amp);
}.splay;

//:
//:stream cycle sizes: dt: 2, freq; 3
{
	var freq, dt, amp;
	dt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(dt, 0, Dseq([200, 400, 500], inf));
	amp = 	TDuty.kr(dt, 0, 0.1);
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:stream cycle sizes: dt: 2, freq; 4
{
	var freq, dt, amp;
	dt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(dt, 0, Dseq([200, 400, 500, 600], inf));
	amp = 	TDuty.kr(dt, 0, 0.1);
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:stream cycle sizes: dt: 3, freq; 2
{
	var freq, dt, amp;
	dt = Dseq([0.2, 0.2, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(dt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(dt, 0, 0.1);
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:stream cycle sizes: dt: 3, freq; 2 - different dt ratio
{
	var freq, dt, amp;
	dt = Dseq([0.2, 0.2, 0.5], inf); // demand ugen as durations
	freq = Duty.kr(dt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(dt, 0, 0.1);
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:stream cycle sizes: dt: 3, freq; 2 - changing dt ratio
{
	var freq, dt, amp;
	dt = Dseq([0.2, 0.2, Dseries(0.2, 0.1, 7)], inf); // demand ugen as durations
	freq = Duty.kr(dt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(dt, 0, 0.1);
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:stream cycle sizes: dt: 3, freq; 2 - changing dt ratio, studdered
// FIXME: NEEDS RECODING OF THE nested Dseq!
{
	var freq, dt, amp;
	dt = Dseq([0.2, 0.2, Dseq((0.1, 0.2 .. 0.9).stutter(16), inf)], inf);
	freq = Duty.kr(dt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(dt, 0, 0.1);
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:stream cycle sizes: dt: 3, freq; 2 - changing dt ratio, stuttered, inverted
{
	var freq, dt, amp, durs;
	durs = (0.1, 0.2 .. 0.9).stutter(16);
	dt = Dseq([Dseq(durs.reverse, inf), Dseq(durs, inf)], inf);
	freq = Duty.kr(dt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(dt, 0, 0.1);
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:stream cycle sizes: dt: 4, freq; 3
{
	var freq, dt, amp;
	dt = Dseq([0.2, 0.2, 0.4, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(dt, 0, Dseq([200, 400, 600], inf));
	amp = 	TDuty.kr(dt, 0, 0.1);
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:stream cycle sizes: dt: 3, freq; 2
{
	var freq, dt, amp;
	dt = Dseq([0.2, 0.2, 0.4, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(dt, 0, Dseq([200, 400, 600], inf));
	amp = 	TDuty.kr(dt, 0, 0.1);
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:stream cycle sizes: dt: 3, freq; 2
{
	var freq, dt, amp;
	dt = Dseq([0.2, 0.2, 0.4, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(dt, 0, Dseq([200, 400, ], inf));
	amp = 	TDuty.kr(dt, 0, 0.1);
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:
{
	var freq, dt, amp;
	//	dt = Dseq([0.1, 0.2, 0.4], inf); // demand ugen as durations
	// dt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	dt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	// freq = Duty.kr(dt, 0, Dseq([204, 400, 201, 502, 300, 200], inf));
	freq = Duty.kr(dt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(dt, 0, Dseq((1..20).normalize.mirror / 11 + 0.01, inf));
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.splay;
//:
{
    var freq, trig, reset, amp, freq;
    trig = Impulse.kr(10);
    amp = Drand([72, 75, 79, 82] - 12, inf).midicps;
    freq = Dseq([72, 75, 79, Drand([82,84,86])], inf).midicps;
    freq = Demand.kr(trig, 0, [seq1, seq2]);
    SinOsc.ar(freq[1]).cubed.cubed.dup * 0.1;
}.play;
//:
{
    var trig, seq;
    trig = Impulse.kr(8);
    seq = Drand([
        Dseq([4,0,0,1,2,1,0,1]),
        Dseq([4,0,2,0,1,0,1,1]),
        Dseq([4,0,0,2,0,0,1,1]),
        Dseq([4,0,1,2,0,1,2,0]),
        Dseq([4,1,1,1,2,2,3,3]),
        Dseq([4,1,0,1,0,1,0,1])
    ], inf);
    trig = Demand.kr(trig, 0, seq * 0.4) * trig;
    {LPF.ar(PinkNoise.ar, 5000)}.dup * Decay.kr(trig, 0.5);
}.play;
//:
)