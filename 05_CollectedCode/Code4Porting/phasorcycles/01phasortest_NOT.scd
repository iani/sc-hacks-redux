//Wed  7 Feb 2024 15:44 understanding phasor to implement cycles with. ...
//:from help file
// Phasor does not work - see last snippet.
// Must try tduty with demand ugen
(
{ var trig, rate, x, sr;
    rate = MouseX.kr(0.2, 2, 1);
    trig = Impulse.ar(rate); // trigger every rate.reciprocal
	// Therefore rate is beats per second.
    sr = SampleRate.ir;
	// go from 0 to 1 in a time interval given by rate.reciprocal.
	// if rate is 0.2, the time interval will be 5 seconds
	// if rate is 2 the time interval  will be 0.5 seconds
    x = Phasor.ar(trig, rate / sr);
    SinOsc.ar(
        [
            LinLin.kr(x, 0, 1, 600, 1000), // convert range from 0..1 to 600..1000
            1000 // constant second frequency
        ], 0, 0.02)

}.play;
)
//:
(
{ var trig, rate, x, sr;
    rate = MouseX.kr(0.2, 2, 1);
    trig = Impulse.ar(rate);
    sr = SampleRate.ir;
    x = Phasor.ar(trig, rate / sr); // kr does not work!
    SinOsc.ar(
        [
            LinLin.kr(x, 0, 1, 600, 1000), // convert range from 0..1 to 600..1000
			WhiteNoise.ar(0.1) * Env.perc(0.01, 0.1).kr(0, A2K.kr(Decay.ar(x > 0.5)))
            // 1000 // constant second frequency
        ], 0, 0.2)

}.play;
)
//:
{
	WhiteNoise.ar(0.1) *
	Env.perc(0.01, 0.1).kr(0, Impulse.kr(1))
}.play;
//:
{
	WhiteNoise.ar(0.1) *
	Env.perc(0.01, 0.1).kr(0, SinOsc.kr(1) > 0)
}.play;
//:
{
	WhiteNoise.ar(0.1) *
	[
		Env.perc(0.01, 0.1).kr(0, LFSaw.kr(1) > 0.5),
		Env.perc(0.01, 0.1).kr(0, LFSaw.kr(1) > 0.0),
	]
} +> \test;
//:
{
	var x;
	x = Phasor.ar(Impulse.ar(1), 1 / SampleRate.ir); // kr does not work!

	WhiteNoise.ar(0.1) *
	[
		Env.perc(0.01, 0.1).kr(0, A2K.kr(x) > 0.15),
		Env.perc(0.01, 0.1).kr(0, A2K.kr(x) > 0.0013) // this stops triggering when threshold is 0.0013 or less
	]
} +> \test;