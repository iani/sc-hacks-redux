// A woodblock synth for potential use as Metronome in the Ligeti Symphonic Poem piece
// From: https://sccode.org/1-5de
//:
// Rhythm-Experiment for sambalike patterns. The goal are cyclic patterns with elliptical time behavior.

// Masterclock is a LFSaw that's bent exponentially with the "swing"-argument.
// swing = 1 is linear. Zero and negative values lead to funny behavior.

// The masterclock is chopped with the "div"-argument in shorter parts for each instrument.
// If div=0 strange stuff happens when going back to other values.
// Negative values for "div" invert the envelopes for kick and snare.
// Behaves symmetrical around the offbeat. div=2 stays linear.

// The raw clock-signals control the instruments. More organic than classic triggers and envelopes, but strange sometimes.

// Default volumes of instruments are high for saturation and distortion.

(
SynthDef(\polyswingsym,{

	arg
	kickv=1500, frqkick=45, deckick=16, pkick=4, bwkick=0.01, divkick=1,
	snrv1=40, decsnr=15, frqsnr=222, divsnr=2,
	hhv=50, divhh=3,
	tmv=44, frqtm=135, bwtm=0.05, divtm=8,
	clvv=40, divclv=4,
	wdv=30, frqwd=850, divwd=5,
	speed=1, swing=1.5, amp=0.5, out=0;

	var sig, master, trig1, trig2, trig3, trig4, trig5, trig6, kick,snr1,hh,tm,clv,wd;

	master=(((LFSaw.ar(speed,pi,1,0).neg**(swing))+1)/2);

	trig1 = (master%(1/divkick))*divkick;
	trig2 = (master%(1/divsnr))*divsnr;
	trig3 = (master%(1/divhh))*divhh;
	trig4 = (master%(1/divtm))*divtm;
	trig5 = (master%(1/divclv))*divclv;
	trig6 = (master%(1/divwd))*divwd;

	kick= Resonz.ar(trig1,frqkick*((trig1**deckick*pkick)+1),bwkick,kickv);
	snr1=  (Resonz.ar(trig2,frqsnr,0.02,4)+WhiteNoise.ar(0.01)+Dust2.ar(123,0.01))*(trig2**decsnr).tanh*snrv1;
	hh= RHPF.ar(Mix.new(Resonz.ar(trig3,Array.linrand(11,4000,6500),0.0005)),2400,0.4,hhv);
	tm= Resonz.ar(trig4,frqtm,bwtm,tmv);
	clv= HPF.ar(Mix.new(Resonz.ar(trig5,Array.linrand(9,500,3500),0.01,clvv)),700);
	wd= Resonz.ar(trig6,frqwd,0.04,wdv);

	sig = kick+snr1+hh+tm+clv+wd;

	Out.ar(out,LeakDC.ar(sig!2).tanh*amp);
}).add;
)
//:
y=Synth(\polyswingsym)
y.free

// Parameters main
y.set(\swing,2);
y.set(\speed,1);
y.set(\amp,0.5);

// Volumes
y.set(\kickv,1500);
y.set(\snrv1, 40);
y.set(\hhv,50);
y.set(\tmv,44);
y.set(\clvv,40);
y.set(\wdv,40);

// Divisors
y.set(\divkick,1);
y.set(\divsnr,2);
y.set(\divhh,3);
y.set(\divtm,6);
y.set(\divclv,4);
y.set(\divwd,5);

// Parameters Kick
y.set(\deckick,16);
y.set(\frqkick,45);
y.set(\bwkick,0.01);
y.set(\pkick,3);

// Parameters Snare
y.set(\decsnr,6);
y.set(\frqsnr,222);

// Parameters Tom
y.set(\frqtm,135);
y.set(\bwtm,0.06);


// Parameters Woodblock
y.set(\frqwd,850);
//:
//------------------------------------------------------------
//
//: Isolate the woodblock sound
(
SynthDef(\wdblk, { | dt = 1, frqwd = 850,  wdv = 30 |
	var trig, src;
	trig = LFSaw.ar(dt);
	src = Resonz.ar(trig, frqwd, 0.04, wdv);
	Out.ar(0, src);
}).add

)
//:Try out one woodblock
Synth(\wdblk);
//:Try out several woodblocks
{
	Synth(\wdblk, [dt: 1.5, frqwd: 850]);
	0.25.wait;
	Synth(\wdblk, [dt: 1, frqwd: 850 / 2]);
	0.1.wait;
	Synth(\wdblk, [dt: 2, frqwd: 850 / 3]);
}.fork
//: Try Impulse instead of LFSaw as plectrum
(
SynthDef(\wd1, { | dt = 1, frqwd = 850,  wdv = 30 |
	var trig, src;
	trig = Impulse.ar(dt);
	src = Resonz.ar(trig, frqwd, 0.04, wdv);
	Out.ar(0, src);
}).add
)
//:test w1
Synth(\wd1);
//:Use TDuty instead of Impulse, so as to retrigger and reset at any point
(
SynthDef(\wd2, { | dt = 1, frqwd = 850,  vol = 30, t_reset = 0 |
	var trig, src;
	trig = TDuty.ar(dt, t_reset);
	src = Resonz.ar(trig, frqwd, 0.04, vol);
	Out.ar(0, src);
}).add
)
//: test w2
a = Synth(\wd2, [dt: 2]);
//: Test vol parameter
a.set(\vol, 100)
//:reset
a.set(\t_reset, 1);
//--------------------------------------------------------------------------------
//:Separating the trigger synth from the sound synth
(
// Trigger SynthDef, control rate.
SynthDef(\t1, { | out = 0, freq = 1, t_reset = 0 |
	var trig;
	trig = TDuty.kr(freq.reciprocal, t_reset);
	Out.kr(out, trig);
}).add;
// Audio Synthdef
SynthDef(\w1, { | in = 0, freq = 850, vol = 30 |
	var src;
	src = Resonz.ar(K2A.ar(In.kr(in)), freq, 0.04, vol);
	Out.ar(0, src);
}).add;
)
//:Test
a = Synth(\t1, [out: 0]);
b = Synth(\w1, [in: 0]);
//:
a.set(\freq, 3);
//:
a.set(\freq, 0.3);
//:
a.set(\t_reset, 1);
//:
b.set(\freq, 900);
//:------------------------------------------------------------
//playing multiple triggers, each with its own control, in one SynthDef
//: Trigger SynthDef, control rate.
SynthDef(\mt0, { | out = 0, bpm = 60, t_reset = 0 |
	var trig;
	trig = TDuty.kr((bpm/60).reciprocal, t_reset);
	Out.kr(out, trig);
}).add;
//: Audio Synthdef
SynthDef(\m0, { | in = 0, freq = 850, vol = 30 |
	var src;
	src = Resonz.ar(K2A.ar(In.kr(in)), freq, 0.04, vol);
	Out.ar(0, src);
}).add
//:Metronome click synth
m = Synth(\m0, [in: 0]);
//:Metronome beats trigger synth
t = Synth(\mt0); // t: trigger
//:Set bpm in trigger synth to 120
t.set(\bpm, 120);
//:Set bpm in trigger synth to 160
t.set(\bpm, 160);
//:Multiple metronome beats triger synthdef
SynthDef(\mmt0, {
	[\out, \bpm, \t_reset, \vol].symcollect(
		{ | i, out, bpm, reset, vol |
			var trig;
			trig = TDuty.kr(
				bpm.kr(60+i/60).reciprocal,
				reset.kr(0)
			);
			Out.kr(out.kr(i - 1), trig * vol.kr(1));
		},
		50
	);
}).add;
//:Test multiple metronome beats trigger synthdef
m = Synth(\m0, [in: 0]);
t = Synth(\mmt0); // t: trigger