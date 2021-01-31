/* 31 Jan 2021 14:14
Timing the triggering of an EventStream's events from a Synth
by using SendTrig.  Makes use of OscTrig. 
*/
//:
~a = (dur: 0.1, degree: Pn(Pseries(10, 1, 10))).splay;
//:
~a.next.play;
//:
{ 50 do: { ~a.playNext; 0.2.wait }; "done".postln; }.fork;
//:FIXME: synth added on same default key should REPLACE previous trig synth
~a.trigSynth({ Impulse.kr(5)});
~a.trigSynth({ Dust.kr(5)});
//:Debugging:
a = (dur: 0.1, degree: Pn(Pseries(20, 1, 10))).eventStream;
t = a.addTrig;
t.addSynth({ Impulse.kr(1) });
t.addSynth({ Impulse.kr(2) });
t.addSynth({ Impulse.kr(3) });
//:compare with:
b = (dur: 1, degree: Pn(Pseries(10, 2, 10))).eventStream;
b.trigSynth({ Impulse.kr(1) }, \trig1);
b.trigSynth({ Impulse.kr(2) }, \trig1);
b.trigSynth({ Impulse.kr(3) }, \trig1);
//:
c = (amp: 0.5, dur: 0.5, degree: Pbrown(10, 20, 1)).eventStream;
c.addTrig(\ctrig);
\ctrig <<! { Impulse.kr(5) };
\ctrig <<! { Impulse.kr(15) };
\ctrig <<! { Impulse.kr(0.5) };
\ctrig <<! { Impulse.kr(2.5) };