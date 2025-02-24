//Explore DynClang as alternative for sine clusters
//To use instead of the multiple SinOsc approach
//:from doc
{
    DynKlang.ar(`[
        [800, 1000, 1200] + SinOsc.kr([2, 3, 4.2], 0, [13, 24, 12]),
        [0.3, 0.3, 0.3],
        [pi,pi,pi]
    ]
) * 0.1
} +> \test;
//:Variant with Line
{
    DynKlang.ar(`[
        XLine.kr(500, (1..10).normalize(400, 600), 10),
        { 0.3 } ! 3,
        [pi,pi,pi]
    ]
) * 0.5
} +> \test;
//: Compare with sines
{
    SinOsc.ar(
        XLine.kr(500, (1..10).normalize(400, 600), 10)
		+ LFNoise2.kr(1).range(0, 0.1)
	).amix.amplify.pan
} +> \test;
//: Compare with sines
{
    SinOsc.ar(
        XLine.kr(500, (1..10).normalize(400, 600), 10)
	).amix.amplify.pan
} +> \test;
//:Use busses to add noise to the freq.
{
	50.do({ | i | (\freq +++ i).bus.set(1)});
	Silent.ar
} +>.test \fmod;
{
	SinOsc.ar(\freq.br(800) * \freq.brdup(50, 1))
	.amix.amplify(0.15).pan
} +> \test;
//:add xline
{
	(1..50).normalize(800, 700).do({ | f, i |
		LFNoise2.kr(2).range(800, 850).bout(\freq +++ i)
	});
	// Silent.ar
} +>.test \fmod;
//:

//:

{
    SinOsc.ar(
        XLine.kr(500, (1..10).normalize(400, 600), 10)
	).amix.amplify.pan
} +> \test;
//:more variation