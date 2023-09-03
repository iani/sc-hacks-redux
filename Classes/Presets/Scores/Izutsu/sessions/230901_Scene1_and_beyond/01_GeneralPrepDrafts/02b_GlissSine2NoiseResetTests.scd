//onsetcode
//2 sliding sindes morphing to noise
//Introducing reset of noise busses at start of script
//:--[0] global vars + modulator reset
\s1_static <!> 7; // duration of initial static freq
\s1_glide <!> 20; // total duration of glide
\s1_spread <!> 5; // duration of spreading from sine to noise
\s1_numsines <!> 50; // number of sine ugens
5 +>.asayo \fadeTime;
5 +>.jun \fadeTime;

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
{ SinOsc.ar(\freq.br(800) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.15).pan } +> \asayo;
{ SinOsc.ar(\freq.br(200) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.15).pan } +> \jun;
//:--[7] start glides
{ XLine.kr(800, 200, ~s1_glide) } @>.asayo \freq; // main freq slides DOWN
{ XLine.kr(200, 800, ~s1_glide) } @>.jun \freq; // main freq slides UP
//:--[17] start noise clusters
{ PinkNoise.ar.perc } +> \perc;
{	// cluster opens UPWARD
	(1..~s1_numsines).normalize + 1 do: { | v, i |
		(LFDNoise3.kr(1).range(-0.05, 0.05) + XLine.kr(1, v, ~s1_spread))
		.bout(\freq +++ i)
	};
} +>.asayo \fmod;
{ 	// cluster opens DOWNWARD
	(1..~s1_numsines).normalize + 1 do: { | v, i |
		(LFDNoise3.kr(1).range(-0.05, 0.05) + XLine.kr(1, v.reciprocal, ~s1_spread))
		.bout(\freq +++ i)
	};
} +>.jun \fmod;
//:--[27] mark end of glides + clusters
{ PinkNoise.ar.perc } +> \perc;
//:--[35] fade out
{ PinkNoise.ar.perc } +> \perc;
\asayo stop: 7;
\jun stop: 7;