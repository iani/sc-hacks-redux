//debugging reseting of busses from within environments.
//:Start a sound with bus control
{ SinOsc.ar(\freq.br(400)).amplify.pan } +> \test;
//:Create a bus modulating synth
{ LFNoise0.kr(5).range(400, 600).bout(\freq) } +>.test \fmod;
//:reset the bus and stop the previous modulating func
{ \freq.bus.set(1000).perc } +>.test \fmod;
//:Try with many busses - source
{ SinOsc.ar(\freq.br(400) * \freq.brdup(2, 1)) .amix.amplify.pan } +> \test;
//:start the modulator
{
	2 do: { | i |
		LFNoise0.kr(20.5 rrand: 30.0).range(0.5, 1.4).bout(\freq +++ i)
	}
} +>.test \fmod;
//:stop the modulator
{
	2 do: { | i |
		(\freq +++ i).bus.set(1.25)
	};
	nil.perc;
} +>.test \fmod;
//:try with izutsu scene setup