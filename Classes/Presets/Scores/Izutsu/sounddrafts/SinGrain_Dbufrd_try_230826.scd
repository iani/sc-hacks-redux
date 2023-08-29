//First example using Demand + Dbufrd with dbuf to create+fill the buffer
//b.getn(0, b.numFrames, {|x| x.postln })
//:Prepare the buffer:
\c.dbuf((60..100).midicps);

//: Play a sound using the buffer
{ | gate = 1, amp = 0.01 |
	var trig, ind, freq;
	trig = Impulse.kr(\trate.br(15));
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
//:randomise order of buffeer elements while running:
//randomize (scramble)
\c.dbuf((60..100).midicps.scramble);
//:reverse
\c.dbuf((60..100).midicps.reverse);
//:stutter (repeat)
(60..100).midicps.stutter toBuf: \c;
//:another pitch model - phrases
(60..70).midicps *.x ((1..5) / 50 + 1) toBuf: \c;
//:and another phrase model
(60..80).midicps *.x [1, 1.1] toBuf: \c;
//:and one more phrase model
(60..80).midicps *.x [1, 1.1, 1.2] toBuf: \c;
//:and another phrase model
(60..80).midicps *.x [1, 1.1, 1.2, 1.3] toBuf: \c;
//:and another phrase model yet
(60..80).midicps.reverse *.x [1, 1.1, 1.2, 1.3] toBuf: \c;
//:and another phrase model yet again
(60..80).midicps.scramble *.x [1, 1.1, 1.2, 1.3] toBuf: \c;
//:change tempo
10 @>.test \trate;
//:vary tempo with LFNoise1
{ LFNoise1.kr(1).range(1, 20) } @>.test \trate;