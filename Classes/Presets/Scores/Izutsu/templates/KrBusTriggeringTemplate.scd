//Wed 30 Aug 2023 18:26 Send an OSC message when a condition
//is met in the value of a kr ugen input from a bus
//:Play something when receiving osc message \trigger
\trigger >>> { (dur: 0.1).play; };
//:test the above trigger with Impulse
{ Impulse.kr(1).sendReply(\trigger) } +> \test;
//:Create trigger func watching a bus 'trig'
{ \trig.br(0).gt(0.0).sendReply(\trigger) } +> \test;
//:Play a triggering signal into bus 'trig'
{ LFSaw.kr(4) } @>.test \trig;
//:Try triggering with LFNoise2
{ LFNoise2.kr(10) } @>.test \trig;
//:ONLY TRIGGERS once: Trigger when an XLine crosses to > 400;
{ \trig.br(0).gt(400).sendReply(\trigger) } +> \test;
{ XLine.kr(200, 800, 1) } @>.test \trig;
//:Like above, XLine duration 10sec;
{ \trig.br(0).gt(400).sendReply(\trigger) } +> \test;
{ XLine.kr(200, 800, 10) } @>.test \trig;
//:Insert timing;
{ \trig.br(0).gt(400).sendReply(\trigger) } +> \test;
{ XLine.kr(200, 800, 10) } @>.test \trig;