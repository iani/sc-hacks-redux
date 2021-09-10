/* 11 Sep 2021 05:15

*/
//:
IdeFantasy.gui;
//:
IdeFantasy.start;
//:
IdeFantasy addDependant: { | ... args | args.postln; };
//:*
NetAddr.localAddr.sendMsg('/corfu', 1, 2, 3);
1.0.wait;

//:
'/corfu' +++> { | message ... args | args.postln; };