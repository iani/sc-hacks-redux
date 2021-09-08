/*  8 Sep 2021 14:42

*/

IdeFantasy addDependant: { | ... args | args.postln; };
//:
\test.addNotifier(IdeFantasy, \alpha, { | ... args |
	args.postln;
})
//:
\test2.addNotifier(IdeFantasy, \alpha, { | ... args |
	"this is test2 and I can add as many actions as I like".postln;
})
//:
IdeFantasy.changed(\alpha, 2, 3, 4);
//: