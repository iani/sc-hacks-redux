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
/* Note interesting patterns arise when we poll a single durations demand ugen
from two Duty-like ugens (here: one Duty and one TDuty).  
This is because each Duty-like ugen fetches the next value of the duration-source
ugen in sequence.

So: 

*/
{
	var freq, dt, amp;
	//	dt = Dseq([0.1, 0.2, 0.4], inf); // demand ugen as durations
	// dt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	dt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	// freq = Duty.kr(dt, 0, Dseq([204, 400, 201, 502, 300, 200], inf));
	freq = Duty.kr(dt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(dt, 0, Dseq((1..20).normalize.mirror / 11 + 0.01, inf));
	SinOsc.ar(Lag.kr(freq, 0.01) * [1, 1.01]) * Decay.kr(amp);
}.play;
//:Observe difference when using a separate dur ugen for each of the Duty ugens:
{
	var freq, freqdt, ampdt, amp;
	freqdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	ampdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(freqdt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(ampdt, 0, Dseq((1..20).normalize.mirror / 11 + 0.01, inf));
	SinOsc.ar(Lag.kr(freq, 0.01) * [1, 1.01]) * Decay.kr(amp);
}.play
//:================ try out different combinations of the above:
{
	var freq, dt, amp;
	//	dt = Dseq([0.1, 0.2, 0.4], inf); // demand ugen as durations
	// dt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	dt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	// freq = Duty.kr(dt, 0, Dseq([204, 400, 201, 502, 300, 200], inf));
	freq = Duty.kr(dt, 0, Dseq([200, 400], inf));
	amp = 	TDuty.kr(dt, 0, Dseq((1..20).normalize.mirror / 11 + 0.01, inf));
	SinOsc.ar(Lag.kr(freq, 0.1) * [1, 1.01]) * Decay.kr(amp);
}.play;
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
}.play;
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