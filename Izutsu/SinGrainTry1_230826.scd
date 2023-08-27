\c.dbuf((60..100).midicps);
//b.getn(0, b.numFrames, {|x| x.postln })
//:

//:
{ | gate = 1, amp = 0.01 |
	var trig, ind, freq;
	trig = Impulse.kr(5);
	ind = Dseq((0..(\c.dbuf.numFrames - 1)), inf);
    freq = Demand.kr(trig, 0, Dbufrd(\c.dbuf, ind));
	SinGrain.ar(trig, 0.25, freq,
		EnvGen.kr(
			Env([0, 1, 0], [1, 1], \sin, 1),
			gate,
			levelScale: amp,
			doneAction: 2)
	)

} +> \test;
//:change order while running:
\c.dbuf((60..100).midicps.scramble);
\c.dbuf((60..100).midicps.reverse);

(60..100).midicps.stutter toBuf: \c;

(60..70).midicps *.x ((1..5) / 50 + 1) toBuf: \c;
(60..80).midicps *.x [1, 1.1] toBuf: \c;
(60..80).midicps *.x [1, 1.1, 1.2] toBuf: \c;
(60..80).midicps *.x [1, 1.1, 1.2, 1.3] toBuf: \c;
(60..80).midicps.reverse *.x [1, 1.1, 1.2, 1.3] toBuf: \c;
(60..80).midicps.scramble *.x [1, 1.1, 1.2, 1.3] toBuf: \c;