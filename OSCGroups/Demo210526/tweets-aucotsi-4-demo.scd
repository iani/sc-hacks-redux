// =====================================================================
// SuperCollider Workspace
// =====================================================================


// 63 ms is the auditory limit for discrete events (see Snyder, 2000; p. 13)
//: as Ndef
Ndef(\limits, { x = Impulse; Pan2.ar(Ringz.ar(x.ar(1/(0.063+(Stepper.kr(x.kr(13),0,0,60)/1000))), 200+(Stepper.kr(x.kr(7),0,1,5)*20), Stepper.kr(x.kr(11),0,3,7)/10), SinOsc.kr(12 + SinOsc.kr(5)),level:0.5) }).play;
//:
Ndef(\limits).stop;

//:Using +>
{ x = Impulse; Pan2.ar(Ringz.ar(x.ar(1/(0.063+(Stepper.kr(x.kr(13),0,0,60)/1000))), 200+(Stepper.kr(x.kr(7),0,1,5)*20), Stepper.kr(x.kr(11),0,3,7)/10), SinOsc.kr(12 + SinOsc.kr(5)),level:0.5) } +> \limits
//:
\limits.stop;


// and a liquid one
//:as NDef
Ndef(\liquid,{x=LagUD.kr(LFPulse.kr(2,0,1,4),0.2,0.8)*SinOsc.kr(TRand.kr(9,12,Dust.kr(3))).lag(1);f=Slope.kr(x);Trig.kr(x,0.24)*SinOsc.ar(x**f!2)}).play;
//:
Ndef(\liquid).stop;

//:Using +>
{x=LagUD.kr(LFPulse.kr(2,0,1,4),0.2,0.8)*SinOsc.kr(TRand.kr(9,12,Dust.kr(3))).lag(1);f=Slope.kr(x);Trig.kr(x,0.24)*SinOsc.ar(x**f!2)} +> \liquid;
//:
\liquid.stop;

// misterious
//:as NDef
Ndef(\bombbass,{FreeVerb.ar(EnvGen.ar(Env([0.0,0.2,0.0],[0.7,0.5]).circle)*PMOsc.ar(4.4*Saw.kr(9).abs,8.8*Saw.kr(19.sin).abs,94,0.5*Saw.kr(7).lag)!2)}).play;
//:
Ndef(\bombbass).stop;

//:using +>
{FreeVerb.ar(EnvGen.ar(Env([0.0,0.2,0.0],[0.7,0.5]).circle)*PMOsc.ar(4.4*Saw.kr(9).abs,8.8*Saw.kr(19.sin).abs,94,0.5*Saw.kr(7).lag)!2)} +> \bombbass;
//:
\bombbass.stop;

//This one uses a routine ...
a=(-917);r{inf.do{a=a.asInteger.postln;if(a.even){a=a/2}{a=a*3+1};play{SinOsc.ar(a,0,0.1)!2*EnvGen.ar(Env.perc,1,1,0,1,2)};0.1.wait}}.play;
// more work needed to convert to sc-hacks ...

// Hithatmel
//:as Ndef
Ndef(\hihatmel,{a=SinOsc.ar(_);g=Stepper.ar(_);f=111!2+(148*a.(3.0166));FreeVerb.ar(HPF.ar(a.(2**f)/3,7e3)+a.(g.(a.(2))*444/g.(f)))}).play;// #SuperCollider
//:
Ndef(\hihatmel).stop;
//:using +>
{a=SinOsc.ar(_);g=Stepper.ar(_);f=111!2+(148*a.(3.0166));FreeVerb.ar(HPF.ar(a.(2**f)/3,7e3)+a.(g.(a.(2))*444/g.(f)))} +> hithatmel;
//:
\hithathmel.stop;

//airy
//:as Ndef
Ndef(\airy,{a=SinOsc.ar(_);b=LFPulse.ar(_);g=Stepper.ar(_);Splay.ar(HPF.ar(a.({|i| f=111!2+(148*a.(1/i));g.(b.(i.sqrt))*(444/i)/g.(f) }!12,123)))}).play;
//:
Ndef(\airy).stop;
//:using +>
{a=SinOsc.ar(_);b=LFPulse.ar(_);g=Stepper.ar(_);Splay.ar(HPF.ar(a.({|i| f=111!2+(148*a.(1/i));g.(b.(i.sqrt))*(444/i)/g.(f) }!12,123)))} +> \airy
//:
\airy.stop;

//stepped
//:as Ndef
Ndef(\stepped,{a=SinOsc;b=LFPulse;g=Stepper;Splay.ar(HPF.ar(a.ar({|i|f=111!2+(148*a.ar(1/i));g.ar(b.ar(i))*(444/i)/g.ar(f)}!8,123).tanh))}).play;
//:
Ndef(\stepped).stop;

//:using +> \stepped
{a=SinOsc;b=LFPulse;g=Stepper;Splay.ar(HPF.ar(a.ar({|i|f=111!2+(148*a.ar(1/i));g.ar(b.ar(i))*(444/i)/g.ar(f)}!8,123).tanh))} +> \stepped;
//:
\stepped.stop;
