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
~a.addTrigSynth({ Impulse.kr(5)});
~a.addTrigSynth({ Dust.kr(5)});