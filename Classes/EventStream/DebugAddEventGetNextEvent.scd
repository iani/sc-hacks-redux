/* 27 Feb 2022 18:31

*/

//: NEW APPROACH 27 Feb 2022 19:00

\trigger.makeTr({ Impulse.kr(15.5) }) +> \mytrigger;

\trigger.trace;
\trigger.untrace;

(degree: 0, dur: 0.1) +>! \test;

\test.addTr(\trigger);
// alternative:
\trigger +>> \test;

\trigger <<+ \trigger;

(degree: -10, dur: 0.01) ++> \test;
(degree: [10, 20].pwhite, dur: 0.01) ++> \test;

~test

// THE STUFF BELOW WENT WRONG - SEE STUFF ABOVE INSTEAD.
//:

//:
(degree: (-10..10).pseq, dur: 0.1) +>! \test;
~test.addTr(\mytrigger);

(degree: 0, dur: 1) ++> \test;
//:

~test.addEvent((degree: 0, dur: 1));
//:
~test.event;
~test.stream;
~test.getNextEvent;
~test.getNextEvent.play;
//:

~test.addEvent((degree: 10, dur: 0.1));

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