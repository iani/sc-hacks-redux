/*  1 Feb 2021 01:36
fromLib does not work in EventStream.addTrigSynth.

Find out why!

Test fromLib, atLibKey:
*/
a = OscTrig.fromLib(\test1, 1);
//:
Library.global;
b = OscTrig.fromLib(\test1, 1);
//:
b === a;
//:
a = (dur: 0.1, degree: Pn(Pseries(10, 1, 10))).eventStream;
a.addTrigSynth({ Impulse.kr(1)});
t = a.addTrig;

t.addSynth({ Impulse.kr(1)})

Library.global;

Library.put(\test, 1, 2, 3, 10000);