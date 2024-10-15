//redoing to debug loss of control, refactor code, and tweak details
//reintroducing sensor control to the refactored script
//:reset freq controls and start sind
{ // reset freq multiplers
	{ | i | (\freq +++ i).bus.set(1) } ! 50;
	Silent.ar;
} +>.jun \freqmod;
//sensor control
{ \x1.gt.lag(0.5) * 0.1 } @>.jun \amp;
// start sine
{ SinOsc.ar(\freq.br(200) * \freq.brdup(50, 1)).amix.amplify(0.3).pan } +> \jun;
//:glissando xline
{ XLine.kr(200, 800, 1) } @>.jun \freq; // main freq slides UP
//:add noise variation instead of constant variation
{	// cluster opens DOWNWARD
	(1..50).normalize(1, 0.5) do: { | v, i |
		(XLine.kr(1, v, 50) + LFNoise2.kr(1).range(0, 0.05))
		.bout(\freq +++ i)
	};
} +>.jun \freqmod;
{
	50.wait;
	"CLUSTER REACHED".postln;
	// { SinOsc.ar(400, 0, 0.1).perc.pan }.play;
}.fork;
//:======= Sensor control tests
//:on
0.6 >>! \x1;
//:off
0.4 >>! \x1;
//:
