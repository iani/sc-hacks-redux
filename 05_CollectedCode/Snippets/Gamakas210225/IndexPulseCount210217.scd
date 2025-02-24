/* 17 Feb 2021 14:46
Using pulse count to access values from a buffer with Index.
*/
Mediator.push;
// b = Buffer.alloc(Server.default, 8, 1);
v = ((20..30) * 3).midicps  rotate: 1;
// Since Stepper/Index start at 1, rotate the data to make 1 be the
// first element. 
b = Buffer.alloc(Server.default, v.size, 1);
b.setn(*(v.collect({ | v, c | [c, v]}).flat));
// b.setn(*v);
(0..v.size - 1) do: { | i | b.get(i, { | v | postf("got value: %\n", v)}); };
//:
{
	SinOsc.ar(
		Index.kr(b.bufnum, PulseCount.kr(Impulse.kr(1)) % v.size),
		0, 0.1).dup
} +> \test;
//:as above, with stepper
{
	SinOsc.ar(
			Index.kr(
				b.bufnum,
				Stepper.kr(Impulse.kr(2), 0, 0, v.size - 1, 1),
			),
			0, 0.05
    ).dup
} +> \test;
//:================================================================
//:Use tduty as producer of the impulses for the Stepper
{
	var trig;
	trig = TDuty.kr(Dseq([0.1, 0.2, 0.4, 0.8, 0.4, 0.2, 0.2, 0.1], inf));
	SinOsc.ar(
			Index.kr(
				b.bufnum,
				Stepper.kr(trig, 0, 0, v.size - 1, 1),
			),
			0, 0.05
    ).dup
} +> \test;
//:================================================================
/*
NEXT: Explore 2 ways to set the rythm of TDuty from the outside. 
1. Use a control ugen for the times of TDuty instead of a Demand ugen, 
and use Index to cycle through a buffer which provides the control values of that ugen. This is not possible, see below.
2. Instead of duty, use Impulse (quasi steady beat), but: 
Use a buffer to select which of the impulses will be 1 (beats, triggers)
and which will be 0 (no trigger).
*/
//:================ Solution 1: control ugen with stepper to produce values
//: A : setup data
~durs = [0.2, 0.4, 0.8, 0.4, 0.4];
// Since Stepper/Index start at 1, rotate the data to make 1 be the
// first element. 
~buf = Buffer.alloc(Server.default, ~durs.size, 1);
//: Sync needed to ensure buffer is allocated before setting its values!
~buf.setn(*(~durs.collect({ | v, c | [c, v]}).flat));
//: Again, sync needed to ensure values have been sent before accessing them!
(0..~durs.size - 1) do: { | i | ~buf.get(i, { | v | postf("got value: %\n", v)}); };
//: B : play the synth
// THIS IS WRONG: Cannot use trig as input before assigning it a control signal
{
	var times, trig;
	times = Index.kr(
		~buf.bufnum,
		Stepper.kr(trig, 0, 0, ~durs.size - 1, 1),
	);
	trig = TDuty.kr(times);
	WhiteNoise.kr(Decay.kr(trig) * 0.05).dup
} +> \test;
//:================ Solution 2: ugen produces  values 0 / 1 to enable/cancel impulses
//:================================================================
//:preliminary tests - scrapped
//:start with something simple: just the trigger without the erasing signal
{
	var times, beats, trig;
	times = 1;
	beats = 1;
	trig = TDuty.kr(times);
	WhiteNoise.ar(Decay.kr(trig * beats) * 0.05).dup;
} +> \test;

//:Add a simple erasing signal
{
	var times, beats, trig;
	times = 1/4;
	beats = LFPulse.kr(1/4).abs;
	trig = TDuty.kr(times);
	WhiteNoise.ar(Decay.kr(trig * beats) * 0.05).dup;
} +> \test;
//:control the erasing signal from the TDuty
{
	var times, beats, trig;
	times = 1/4;
	beats = LFPulse.kr(1/4).abs;
	trig = TDuty.kr(times);
	WhiteNoise.ar(Decay.kr(trig * beats) * 0.05).dup;
} +> \test;
//:================================================================
//:New tests
//:Introduce a buffer and stepper for producing sequence of 0, 1
//: A : setup data
~beats = [1, 0, 0, 1, 1, 0, 1];
// Since Stepper/Index start at 1, rotate the data to make 1 be the
// first element.
~beats = ~beats rotate: 1;
~buf = Buffer.alloc(Server.default, ~beats.size, 1);
//:SYNC NEEDED! Wait for buffer to allocate before setting its values
~buf.setn(*(~beats.collect({ | v, c | [c, v]}).flat));
//:SYNC NEEDED! Wait for values to be sent before accessing them
(0..~beats.size - 1) do: { | i | ~buf.get(i, { | v | postf("got value: %\n", v)}); };
//:
{ | freq = 1 |
	var pulse, index, beats;
	pulse = Impulse.kr(freq);
	index = PulseCount.kr(pulse);
	beats = WrapIndex.kr(~buf.bufnum, index);
	WhiteNoise.ar(Decay.kr(beats * pulse * 0.05)).dup;
	// WhiteNoise.ar(Decay.kr(pulse * 0.05)).dup;
} +> \test;
//:
~test.set(\freq, 5);
//:
~beats = [1, 0, 1, 0, 1, 1, 1];
// Since Stepper/Index start at 1, rotate the data to make 1 be the
// first element.
~beats = ~beats rotate: 1;
~buf.setn(*(~beats.collect({ | v, c | [c, v]}).flat));