//code
//2 sliding sindes morphing to noise, with sensor controls
//Introducing control of LFNoise2 range + rate
//Durations + sensor controls according to rehearsal.  Tested.
//script version
//:--[80] global vars + modulator reset
\s1_static <!> 7; // duration of initial static freq
\s1_glide <!> 180; // total duration of glide
\s1_spread <!> 90; // duration of spreading from sine to noise
\s1_numsines <!> 50; // number of sine ugens
5 +>.asayo \fadeTime;
5 +>.jun \fadeTime;
// controls:

{ \x5.gt.lag(0.5) + 0.1 } @>.jun \vol;
{ \z8.gt.lag(0.5) * 0.1 } @>.asayo \vol;

"Resetting noise multipliers to 1".postln;
{
	~s1_numsines.do({ | i | (\freq +++ i).bus.set(1)});
	PinkNoise.ar(0.05).perc(0.5, 0.9).pan(-1);
} +>.asayo \fmod;
{
	~s1_numsines.do({ | i | (\freq +++ i).bus.set(1)});
	GrayNoise.ar(0.05).perc(0.5, 0.4).pan(1)
} +>.jun \fmod;

"starting the sound".postln;
{ SinOsc.ar(\freq.br(800) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.05).pan * \vol.br(0) } +> \asayo;
{ SinOsc.ar(\freq.br(200) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.05).pan * \vol.br(0)} +> \jun;
//:--[90] start glides
{ PinkNoise.ar.perc } +> \perc;
{ XLine.kr(800, 200, ~s1_glide) } @>.asayo \freq; // main freq slides DOWN
{ XLine.kr(200, 800, ~s1_glide) } @>.jun \freq; // main freq slides UP
//:--[90] start noise clusters
{ PinkNoise.ar.perc } +> \perc;
{	// cluster opens UPWARD
	var scale;
	// scale = \lfdscale.br(0);
	scale = XLine.kr(0.001, 1.0, ~s1_spread);
	(1..~s1_numsines).normalize + 1 do: { | v, i |
		(LFDNoise3.kr(1).range(-0.05 * scale, 0.05 * scale) + XLine.kr(1, v, ~s1_spread))
		.bout(\freq +++ i)
	};
} +>.asayo \fmod;
{ 	// cluster opens DOWNWARD
	var scale;
	// scale = \lfdscale.br(0);
	scale = XLine.kr(0.001, 1.0, ~s1_spread);
	(1..~s1_numsines).normalize + 1 do: { | v, i |
		(LFDNoise3.kr(1).range(-0.05 * scale, 0.05 * scale) + XLine.kr(1, v.reciprocal, ~s1_spread))
		.bout(\freq +++ i)
	};
} +>.jun \fmod;
//:--[10] mark end of glides + clusters
{ PinkNoise.ar.perc } +> \perc;
//:--[7] fade out
{ PinkNoise.ar.perc } +> \perc;
\asayo stop: 7;
\jun stop: 7;
nil +>.asayo \fmod;
nil +>.jun \fmod;
nil +>.asayo \freq;
nil +>.jun \freq;