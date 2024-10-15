//Wed  7 Feb 2024 16:31 start with this from the help file

//: amplitude changes
(
    {
        var trig;
        trig = TDuty.ar(
                Dseq([0.1, 0.2, 0.4, 0.3], inf), // demand ugen as durations
                0,
                Dseq([0.1, 0.4, 0.01, 0.5, 1.0], inf) // demand ugen as amplitude
            );
        Ringz.ar(trig, 1000, 0.1)

    }.play;
)

//: simple rhythm

{
	var trig;
	trig = TDuty.ar(
		Dseq([0.1, 0.2, 0.4, 0.3], inf), // demand ugen as durations
		0,
		Dseq([0.1], inf) // demand ugen as amplitude
	);
	Ringz.ar(trig, 1000, 0.1)
} +> \test;

//: modify tempo???
{
	var trig;
	// tempo can become any control ugen or bus controllable at any point:
	var tempo;
	tempo = XLine.kr(1/4, 5, 20); // placeholder for testing - could be any kr ugen.
	trig = TDuty.ar(
		Dseq([0.1, 0.2, 0.4, 0.3], inf) * tempo, // demand ugen as durations
		0,
		Dseq([0.1], inf) // demand ugen as amplitude
	);
	Ringz.ar(trig, 1000, 0.1)
} +> \test;

//:
//Note:
{
	var trig;
	// tempo can become any control ugen or bus controllable at any point:
	var tempo;
	tempo = XLine.kr(1/4, 5, 20); // placeholder for testing - could be any kr ugen.
	trig = TDuty.ar(
		// note: sum of dseq durations should be normalized to 1 and then
		// the values should be multiplied to scaled to total duration the desired
		// duration of the entire cycle.
		// also, there must be always at least one duratin in the dseq,
		// but if there are no notes in the cycle, then the dseq of the amplitudes should
		// return just 0 all the time.
		// possibly the duration and amplitude seqs should both be Dbufrd ugens
		// When the rhythm pattern is modified, both the duration and amplitude *buffers*
		// should be modified. For empty cycles, the amplitude buffer will contain only 0 and
		// the duration buffer will contain a single 1 (full cycle duration).
		// Possibly the amplitudes will only be 0 or 1. The actual amplitude of the notes
		// should be read from a different buffer from a different Dbufrd.
		// but the need to have at least one event per cycle remains so some
		// handling is needed there, or we keep the target amplitudes in the original amplitude Dbufrd!
		Dseq([0.1, 0.2, 0.4, 0.3], inf) * tempo, // demand ugen as durations
		0,
		Dseq([0.1], inf) // demand ugen as amplitude
	);
	Ringz.ar(trig, 1000, 0.1)
} +> \test;
