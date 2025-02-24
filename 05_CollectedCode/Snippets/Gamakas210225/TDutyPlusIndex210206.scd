/*  6 Feb 2021 12:51
Combine TDuty or Duty with Index for playing patterns with 
data from a buffer.
*/
//: Example to adapt:
{
	var freq, freqdt, ampdt, amp;
	freqdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	ampdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(freqdt, 0, Dseq([200, 400, 500, 600, 700], inf));
	amp = TDuty.kr(ampdt, 0, Dseq((1..20).normalize / 11 + 0.01, inf));
	SinOsc.ar(Lag.kr(freq, 0.3) * [1, 1.01]) * Decay.kr(amp);
}.mplay;

//: 
{
	var freq, freqdt, ampdt, amp;
	freqdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	ampdt = Dseq([0.2, 0.4], inf); // demand ugen as durations
	freq = Duty.kr(freqdt, 0, Dseq([200, 400, 500, 600, 700], inf));
	amp = TDuty.kr(ampdt, 0, Dseq((1..20).normalize / 11 + 0.01, inf));
	SinOsc.ar(Lag.kr(freq, 0.3) * [1, 1.01]) * Decay.kr(amp);
}.mplay;
