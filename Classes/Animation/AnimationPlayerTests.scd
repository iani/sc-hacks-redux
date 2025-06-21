//: 250616
a = Animation.sessions.asArray;  // ok
b = a.first; // ok
c = b.player;
c;
c.class.verbose;
c.play;
c.class.verbose = true;
c.stop;
d = b.converter;
d.buffer;
d.buffer.numChannels;
//: Sound:
c.bus;
{ SinOsc.ar(In.kr(c.bus.index).linlin(-10, 10, 400, 4000)).dup * 0.1 }.play;

//:
RokokoJoints.makeOscMessage((1..161));
a = RokokoJoints();
a.activeJoints = [\hip, \spine];
a.jointFilter;
a.makeOscMessage((1..161))
a.activeJoints = [\hip, \rightToeEnd];
a.makeOscMessage((1..161));

RokokoJoints.joints;

//:
var oscMaker;
oscMaker = RokokoJoints();
oscMaker.activeJoints = RokokoJoints.joints;
oscMaker.makeOscMessage((1..161), \testFigureInGodot);