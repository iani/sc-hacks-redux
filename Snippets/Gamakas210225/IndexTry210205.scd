/*  6 Feb 2021 00:16
Trying index as a way to get values from a buffer.
To be combined with Tduty.
*/

//:
(
{
	var freq;
	freq = Index.kr(
            LocalBuf.newFrom([ 200, 300, 400, 500, 600, 800 ]),
				LFSaw.kr(1/4, -pi/4).range(0, 6),
        );
    SinOsc.ar(
        Lag.kr([freq, freq * 1.25], 0.5),
        0,
        0.01
    );
}.play;
)