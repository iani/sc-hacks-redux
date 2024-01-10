/* 17 Nov 2023 18:57

*/


//:setup
Server.default addBootAction: {
	{ Out.ar(2, In.ar([0, 1])) }.play(target: nil.asTarget, addAction: \addAfter);
};
//:test
{ SinOsc.ar([400, 600], 0, 0.1) } +> \jbard;
