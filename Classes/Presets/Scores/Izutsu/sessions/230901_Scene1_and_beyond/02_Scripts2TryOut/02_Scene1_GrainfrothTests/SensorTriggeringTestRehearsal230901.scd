//:triggering test
//:setup xyz
{
	Xyz(5, 0.1, 0.01, 0.01) + Xyz(6, 0.1, 0.01, 0.01) +
	Xyz(7, 0.1, 0.01, 0.01) + Xyz(8, 0.1, 0.01, 0.01)
} @>.test \xyz;
//:setup xyz
{ Xyz(5, 0.1, 0.01, 0.01) } @>.test \xyz5;
{ Xyz(6, 0.1, 0.01, 0.01) } @>.test \xyz6;
{ Xyz(7, 0.1, 0.01, 0.01) } @>.test \xyz7;
{ Xyz(8, 0.1, 0.01, 0.01) } @>.test \xyz8;
//:hear xyz
{
	\xyz.br(0.5).poll;
	SinOsc.ar(\xyz.br(0.5) * 600, 0.1).amplify.pan;
} +> \test;
//:trigger when any one or more of the sensors move above threshold
{
	\xyz.br(0.5).poll;
	(\xyz.br(0.5) > 0.05).sendReply(\bang_all);
	// SinOsc.ar(\xyz.br(0.5).linexp(0.1, 20, 300, 1000), 0.1).amplify.pan;
	Silent.ar
} +> \test;
//:trigger when sensor 5 is above theshold
{
	\xyz5.br.poll;
	(\xyz5.br > 0.5).sendReply(\bang_5);
	(\xyz6.br > 0.5).sendReply(\bang_6);
	(\xyz7.br > 0.2).sendReply(\bang_7);
	(\xyz8.br > 0.2).sendReply(\bang_8);
	// SinOsc.ar(\xyz.br(0.5).linexp(0.1, 20, 300, 1000), 0.1).amplify.pan;
	Silent.ar
} +> \test;
//:
\bang_all >>> { (degree: [0, 2, 4, 7], dur: 0.1).play };
//:
\bang_all >>> { (degree: 0, dur: 0.1).play };
//:
\bang_all >>> {};
//:
\bang_all >>> nil;
//:
\bang_5 >>> { "bang5".postln; (degree: -2, dur: 0.4).play };
\bang_6 >>> { "bang6".postln; (degree: 5, dur: 0.4).play };
\bang_7 >>> { "bang7".postln; (degree: 10, dur: 0.4).play };
\bang_8 >>> { "bang8".postln; (degree: 15, dur: 0.4).play };
//:
\bang_5 >>> { "bang5".postln;
	(degree: (-2..5).pseq(1), dur: 0.1) +> \b5
};
\bang_6 >>> { "bang6".postln;
	(degree: (10..2).pseq(1), dur: 0.1) +> \b6
};

\bang_7 >>> { "bang7".postln;
	(degree: (20..26).prand(5) + [0, 3], dur: 0.4) +> \b7
};

\bang_8 >>> { "bang8".postln;
	(degree: (-15..10).prand(5) + [0, 2], dur: 0.4) +> \b7
};