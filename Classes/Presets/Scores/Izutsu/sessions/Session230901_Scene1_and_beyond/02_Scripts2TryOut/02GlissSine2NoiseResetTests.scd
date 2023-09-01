//onsetcode
//2 sliding sindes morphing to noise
//Introducing control of LFNoise2 range + rate
//script version
//:--[0] global vars + sound start
\s1_static <!> 7; // duration of initial static freq
\s1_glide <!> 60; // total duration of glide
\s1_spread <!> 5; // duration of spreading from sine to noise
\s1_numsines <!> 50; // number of sine ugens
5 +>.asayo \fadeTime;
5 +>.jun \fadeTime;

// { PinkNoise.ar.perc; } +> \perc;
{ SinOsc.ar(\freq.br(800) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.15).pan } +> \asayo;
{ SinOsc.ar(\freq.br(200) * \freq.brdup(~s1_numsines, 1)).amix.amplify(0.15).pan } +> \jun;
//// :--[7]
// { PinkNoise.ar.perc } +> \perc;
// { XLine.kr(800, 200, ~s1_glide) } @>.asayo \freq; // main freq slides DOWN
// { XLine.kr(200, 800, ~s1_glide) } @>.jun \freq; // main freq slides UP
//:--[10] start noise clusters
// { PinkNoise.ar.perc } +> \perc;
{	// cluster opens UPWARD
	(1..~s1_numsines).normalize + 1 do: { | v, i |
		(LFDNoise3.kr(1).range(-0.05, 0.05) + XLine.kr(1, v, ~s1_spread))
		.bout(\freq +++ i)
	};
} +>.asayo \freqmod;
{ 	// cluster opens DOWNWARD
	(1..~s1_numsines).normalize + 1 do: { | v, i |
		(LFDNoise3.kr(1).range(-0.05, 0.05) + XLine.kr(1, v.reciprocal, ~s1_spread))
		.bout(\freq +++ i)
	};
} +>.jun \freqmod;
//:--[11] stop noise clusters
nil +>.asayo \freqmod;
nil +>.jun \freqmod;
//:--[12] reset noise multipliers
{
	1.dup(~s1_numsines) do: { | i | (\freq +++ i).bus.set(i)};
	PinkNoise.ar(0.05).perc(0.5, 0.9).pan(-1)
} +>.asayo \perc1;
{
	1.dup(~s1_numsines) do: { | i | (\freq +++ i).bus.set(i)};
	GrayNoise.ar(0.05).perc(0.5, 0.4).pan(1)
} +>.jun \perc1;

/*
//:--[20] stop mulitplier noise ugens
nil +>.asayo \freqmod;
nil +>.jun \freqmod;
//:--[21] reset multipliers
// reset noise multipliers + base to initial state:
{
	1.dup(~s1_numsines) do: { | i | (\freq +++ i).bus.set(i)};
	PinkNoise.ar(0.05).perc(1.5, 4.9).pan(-1)
} +>.asayo \perc1;
{
	1.dup(~s1_numsines) do: { | i | (\freq +++ i).bus.set(i)};
	GrayNoise.ar(0.05).perc(1.5, 4.4).pan(1)
} +>.jun \perc1;

{ PinkNoise.ar.perc } +> \perc;
//:--[30]
{ PinkNoise.ar.perc } +> \perc;
\asayo stop: 2;
\jun stop: 2;
*/