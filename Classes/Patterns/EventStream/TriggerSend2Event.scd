/* 27 Feb 2022 18:31

*/

//: NEW APPROACH 27 Feb 2022 19:00

\trigger.makeTr({ Impulse.kr(5) }) +> \mytrigger;

\trigger.trace;
\trigger.untrace;

(degree: 0, dur: 0.1) +>! \test;

\test.addTr(\trigger);
// alternative:
\trigger +>> \test;

\trigger <<+ \trigger;

(degree: -10, dur: 0.01) ++> \test;
(degree: [10, 20].pwhite, dur: 0.01) ++> \test;
(degree: (10..20).pseq, dur: 0.01) ++> \test;
(degree: ((-10..20) +.t [0, 2, 5]).pseq, dur: 0.01) ++> \test;
//:Try 2 triggers on one EventStream:

\trigger1.makeTr({ Impulse.kr(5/3) }) +> \mytrigger1;
\trigger2.makeTr({ Impulse.kr(4/3) }) +> \mytrigger2;
//:
(degree: ((20..-10) +.t [0, 2, 5]).pseq, dur: 0.1) +>! \test2;
//:
\trigger1 +>> \test2;
\trigger2 +>> \test2;
//:

// THE STUFF BELOW WENT WRONG - SEE STUFF ABOVE INSTEAD.
//:

//:
(degree: (-10..10).pseq, dur: 0.1) +>! \test;
~test.addTr(\mytrigger);

(degree: 0, dur: 1) ++> \test;
//:

~test.mergeEvent((degree: 0, dur: 1));
//:
~test.event;
~test.stream;
~test.getNextEvent;
~test.getNextEvent.play;
//:

~test.mergeEvent((degree: 10, dur: 0.1));

//: ======= new appproach
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
\trigger2.makeTr({ Impulse.kr(2.5) }) +> \mytrigger2;

\trigger2 >>> { "testing".postln };
\trigger2.removeOSC;
// \trigger2 <<< \trigger2;

(degree: (0..5).pseq, dur: 1) +>! \test;
\trigger2 >>> { ~test.postln };
\trigger2 >>> { ~test.event.postln };
\trigger2 >>> { ~test.getNextEvent.postln.play };
(degree: (-10..-1).pseq, dur: 0.1) ++> \test;
(degree: ((1..5) * [1, 2]).pseq, dur: 0.1) ++> \test;