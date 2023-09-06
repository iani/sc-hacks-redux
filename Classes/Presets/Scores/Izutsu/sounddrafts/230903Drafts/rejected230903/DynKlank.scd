/*  3 Sep 2023 16:18
From:

https://doc.sccode.org/Classes/DynKlank.html

*/

{ DynKlank.ar(`[[800, 1071, 1153, 1723], nil, [1, 1, 1, 1]], Impulse.ar(2, 0, 0.1)) }.play;
​
{ DynKlank.ar(`[[800, 1071, 1353, 1723], nil, [1, 1, 1, 1]], Dust.ar(8, 0.1)) }.play;
​
{ DynKlank.ar(`[[800, 1071, 1353, 1723], nil, [1, 1, 1, 1]], PinkNoise.ar(0.007)) }.play;
​
{ DynKlank.ar(`[[200, 671, 1153, 1723], nil, [1, 1, 1, 1]], PinkNoise.ar([0.007, 0.007])) }.play;

//:Changing parameters in realtime:

// change freqs and ringtimes with mouse
{    var freqs, ringtimes;
    freqs = [800, 1071, 1153, 1723] * MouseX.kr(0.5, 2, 1);
    ringtimes = [1, 1, 1, 1] * MouseY.kr(0.1, 10, 1);
    DynKlank.ar(`[freqs, nil, ringtimes ], Impulse.ar(2, 0, 0.1))
}.play;
//:

// set them from outside later:
SynthDef('help-dynKlank', { |out|
    var freqs, ringtimes, signal;
    freqs = Control.names([\freqs]).kr([800, 1071, 1153, 1723]);
    ringtimes = Control.names([\ringtimes]).kr([1, 1, 1, 1]);
    signal = DynKlank.ar(`[freqs, nil, ringtimes ], Impulse.ar(2, 0, 0.1));
    Out.ar(out, signal);
}).add;

//:
​
a = Synth('help-dynKlank');
​
a.setn(\freqs, Array.rand(4, 500, 2000));
a.setn(\ringtimes, Array.rand(4, 0.2, 4) );
a.setn(\ringtimes, Array.rand(4, 0.02, 0.4) );
​
//: create multichannel controls directly with literal arrays:
(
SynthDef('help-dynKlank', { |out,
    freqs (#[100, 200, 300, 400]),
    amps (#[1, 0.3, 0.2, 0.05]),
    rings (#[1, 1, 1, 2])|
​
    Out.ar(out, DynKlank.ar(`[freqs, amps, rings], WhiteNoise.ar * 0.001))
}).add
)
//:
​
a = Synth('help-dynKlank');
​
a.setn(\freqs, Array.rand(4, 500, 2000));
a.setn(\amps, Array.exprand(4, 0.01, 1));
​
{ Out.kr(102, MouseX.kr(1, 2) * Array.rand(4, 500, 2000)) }.play;
a.mapn(\freqs, 102, 4);


helpfile source: /usr/local/share/SuperCollider/HelpSource/Classes/DynKlank.schelp
link::Classes/DynKlank::