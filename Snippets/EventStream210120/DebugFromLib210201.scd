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
t = a.addTrig;
t.addSynth({ Impulse.kr(1)});
MyLibrary.global;

t.dependants;

\xxx.changed(\yyy);


t;
t.changed(\trig);

y = a.addTrig;
y.addSynth({ Impulse.kr(1)});

t.id;

{ SendTrig.kr(Impulse.kr(1), 1009) }.play;

a.addTrigSynth({ Impulse.kr(5)});
a.trigSynth({ Impulse.kr(0.5)});
a.trigSynth({ Impulse.kr(15)});


t.addSynth({ Impulse.kr(3)})
y.addSynth({ Impulse.kr(2)})

MyLibrary.global.put(1, 2, 3);


