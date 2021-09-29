/* 26 May 2021 13:18
First tests by iani using aucotsi's tweets, preparing rehearsal for the 
workshop.
*/

//:
Ndef(\sa, { x=SinOsc; Pan2.ar(x.ar(310+232*LFPulse.kr(11,x.kr(3),0.2+0.1*x.kr(3))*x.kr(13)),x.kr(12)) }).play
//:
Ndef(\sa).stop;
Ndef(\sa).play;
