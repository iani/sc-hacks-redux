// from: http://sccode.org/1-5aE
//\\##//\\##//\\##//\\##//\\##//\\##//\\##//\\##//\\##
//\\##//\\##                        //\\##//\\##//\\##
//\\##//\\##      SynthDEFaults     //\\##//\\##//\\##
//\\##//\\##   Patterns Examples    //\\##//\\##//\\##
//\\##//\\##                        //\\##//\\##//\\##
//\\##//\\##//\\##//\\##//\\##//\\##//\\##//\\##//\\##


/*
- A Collection of Tradicional SynthDefs-
//under GNU GPL 3 as per SuperCollider license
//By Zé Craum

SynthDefs at: http://sccode.org/1-5aD

Contribuitions, comments, advises, criticisms, etc are very welcome: zecraum at gmail com
*/

//--//..//--//..//--//..//--//..//--//..//--//..
//--//..       Condesed Examples        //--//..
//--//..//--//..//--//..//--//..//--//..//--//..

//Pianos
Synth(\piano_MDA)
//Electric Pianos
Synth(\Piano_rhodeySC)
Synth(\everythingrhodes	)
//Harpsichord
Synth(\harpsichord_simple)
Synth(\harpsichord_pluck)
//Organ
Synth(\organ_tonewheel0)
Synth(\organ_tonewheel1)
Synth(\organ_tonewheel2)
Synth(\organ_tonewheel3)
Synth(\organ_tonewheel4)
Synth(\organ_donor)
Synth(\organ_reed)
//Woodwinds
Synth(\flute_waveguide)
//Strings
Synth(\strings)
Synth(\violin)
//Percussion
Synth(\kalimba)
Synth(\marimba1)
Synth(\bell_tubular)
Synth(\glockenspiel)
Synth(\xilophone)
Synth(\marimba)
Synth(\prayer_bell)
//Drum Kits
Synth(\snare909,[\mul,0.5,\velocity, rrand(0.5, 1.0)]);
Synth(\neurosnare, [amp: 0.4]);
Synth(\hihat)
Synth(\snare)
Synth(\kick)
Synth(\kick1, [amp: 0.4]);
Synth(\kick2, [amp: 0.4]);
Synth(\kick3, [amp: 0.4]);
Synth(\kick_808)
Synth(\SOSkick)
Synth(\SOSsnare)
Synth(\SOShats)
Synth(\SOStom)
Synth(\kick_electro)
Synth(\snare_electro)
Synth(\hihat_electro)
Synth(\clap_electro)
//Others
Synth(\blips1)
Synth(\sawSynth)
Synth(\noisy)
~voice = Buffer.read(s, Platform.resourceDir +/+ "sounds/a11wlk01.wav"); // remember to free the buffer later.
Synth(\sampler_PlayBuf, [\buf, ~voice])
Synth(\sine_simple)
Synth(\sine_vintage)
Synth(\prophet5pwmstrings)


//--//..//--//..//--//..//--//..//--//..//--//..
//--//..       Extended Examples        //--//..
//--//..//--//..//--//..//--//..//--//..//--//..


//  //  //  //  //  //
////    Pianos    ////
//  //  //  //  //  //

// Hanon exercise
(
p = Pbind(
	\instrument, \piano_MDA,
	\octave, 3,
    \degree, Ppatlace([
        Pseries(0, 1, 8),    // first, third etc. notes
        Pseries(2, 1, 7)    // second, fourth etc. notes
    ], inf),
	\legato, 1,
    \dur, 0.25
).play;
)

//  //  //  //  //  //  //  //  //
////     Electric Pianos      ////
//  //  //  //  //  //  //  //  //

(
Pbind(
    \instrument, \Piano_rhodeySC,
    \scale, Scale.mixolydian,
    \octave, 4,
    \root, 2,
    \legato, Pseq([0.9, 0.5, 0.5, 0.9, 0.9, 0.9, 0.9, 0.5, 1, 0.5, 1, 0.6, 0.3], inf),
    \dur, Pseq([1 + (1/3), 1/3, 1/3, 1/7, 6/7, 5/6, 1/6, 1/2, 2/6, 1/6, 2 + 1/2, 1, 1/2], inf),
    \degree, Pseq([
        [0, 2, 4], 2, 4, 7, 8, 7, 0, [1, 3, 6], 5, [1, 3, 6], Rest(), [-1, 1, 3], [1, 3, 5],
        [0, 2, 4], 2, 4, 8, 9, 7, 0, [1, 3, 6], 5, [1, 3, 6], Rest(), [-1, 1, 3], [1, 3, 5],
    ], inf),
    \mix, 0.2,
    \modIndex, 0.2,
    \lfoSpeed, 0.5,
    \lfoDepth, 0.4,
    \vel, Pgauss(0.8, 0.1, inf),
    \amp, 0.3
).play(TempoClock(1.5));
)


(
Pfx(
	Pbind(
		\instrument,\everythingrhodes,
		\midinote,Pseq([12,8,7,0,[-12,4,12],[-12,4,12],[-12,4,12],[-11,5,12],[-11,5,12],[-9,7,12],7]+48,inf),
		\dur,Pseq([0.5,0.5,0.5,0.5,1.5,1.0,1.0,1.0,1.0,2.0,0.5] ,inf),
		\cutoff,Pstutter(11,Pn(Pseries(500,500,4),inf))
	),
	\choruscompresseffect
).play
)

//  //  //  //  //  //  //  //
////    Harpsichord     ////
//  //  //  //  //  //  //  //

Synth(\harpsichord_simple)
(
Pbind(
	\instrument, \harpsichord_simple,
	\scale, Scale.harmonicMinor,
	\degree, Pseq([0, 1, 2, 3, 4, 5, 6, [-7, -3, 0, 2, 4, 6], 7], inf),
	\amp, Pseq([Pn(0.1, 8), 0.06], inf),
	\dur, Pseq([0.3, Pn(0.1, 6), 1.3, 1], inf),
	\strum, 0.09
).play
)


(
Pbind(\instrument, \harpsichord_pluck,
	\degree, Phprand(-14, 14, 50),
	\scale, Scale.harmonicMinor,
	\dur, Pseq([Pseries(0.02 * 15, -0.02, 15).clip(0.01, 0.1*15), Pseries(0.01, 0.01, 15), ],1),
	\db, -10,
	\pan, Pseries(-1, 0.08, 30).clip(-1,1) ,
	).play;
)

//  //  //  //  //  //  //  //
////       Organs          ////
//  //  //  //  //  //  //  //


(
Pbind(
			\instrument, \organ_tonewheel0,
			\dur, Prand([ 4, 8, 2, 2, 1, 1, 0.25,0.25, 0.25], inf)/3,
			\sus, 3.5 ,
	        \db, Pwhite(-20, -10),
			\mix, Pwhite(0.05, 0.15),
			\octave, 3,
			\degree, Pgauss(15, 5).round,
			\scale, Scale.harmonicMinor,
			\oct, Pbrown(0.01, 1, 0.01),
			\nazard, Pbrown(0.01, 1, 0.01),
			\blockFlute, Pbrown(0.01, 1, 0.01),
			\tierce, Pbrown(0.01, 1, 0.01),
			\larigot, Pbrown(0.01, 1, 0.01),
			\sifflute, Pbrown(0.01, 1, 0.01),
		).play;
)


(
Pfx(
	Pfx(
		Pbind(
			\instrument, \organ_tonewheel1,
			\dur, Prand([ 4, 8, 2, 2, 1, 1], inf)/3,
			\sus, 2.5 ,
			\amp, 0.1*(2**Pgauss(0, 0.1)),
			\mix, Pwhite(0.05, 0.15),
			\octave, 3,
			\degree, Pgauss(15, 5).round,
			\scale, Scale.melodicMinor,
			\oct, Pbrown(0.01, 1, 0.01),
			\nazard, Pbrown(0.01, 1, 0.01),
			\blockFlute, Pbrown(0.01, 1, 0.01),
			\tierce, Pbrown(0.01, 1, 0.01),
			\larigot, Pbrown(0.01, 1, 0.01),
			\sifflute, Pbrown(0.01, 1, 0.01),
		),
		\choruscompresseffect
	), \reverb
).play
)




(
Pfx(
	Pbind(
    \instrument, \organ_tonewheel3,
    \dur, Pseq([0.3, 0.15, 0.15], inf)/0.9,
	\sus, 1.2,
    \amp, 0.1*(2**Pgauss(0, 0.1)),
    \mix, Pwhite(0.05, 0.15),
	\octave, 3,
	\degree, Prand((0..24), inf)
),
	\choruscompresseffect
).play;
)


(
Pfx(
	Pbind(
    \instrument, \organ_tonewheel2,
    \dur, Pseq([0.3, 0.15, 0.15], inf)/0.9,
	\sus, 1.2,
    \amp, 0.1*(2**Pgauss(0, 0.1)),
    \mix, Pwhite(0.05, 0.15),
	\octave, 3,
	\degree, Prand((0..24), inf),
	\vrate, 5,
),
	\choruscompresseffect
).play;
)


(
Pbind(
	\instrument,\organ_tonewheel4,
	\midinote,Pseq([48,50,52,60,64,67,58,72],inf),
	\dur,1.0,
	\rq,Pstutter(3,Pn(Pseries(0.75,-0.1,6),inf)),
	\lfowidth,Pstutter(7,Pn(Pseries(0.0,0.05,10),inf)),
	\cutoff,Pstutter(4,Pn(Pseries(2000,1000,4),inf))
).play
)


(
Pbind(
	\instrument, \organ_donor,
	\sustain,0.9,
	\amp, 0.2,
	\midinote,Pn(Pshuf([[0,4,7],[-1,2,7],[-3,0,5],[-1,2,7],[2,5,9],[-5,-1,4]]+60),inf),
	\dur,Pn(Pshuf([2.0,2.0,4.0,2.0],1),inf),
	\lfowidth,Pn(Pseries(0.0,0.001,16),inf),
	\lforate,Pn(Pseries(1,0.25,16),inf),
	\rq,Pn(Pseries(0.3,-0.01,8),inf),
	\cutoff,Pn(Pseries(6000,200,9),inf)
).play
)


//  //  //  //  //  //  //  //
////    Woodwinds      ////
//  //  //  //  //  //  //  //

(
Pbind(
    \instrument, \organ_reed,
    \amp, 0.1*(2**Pgauss(0, 0.1)),
    \dur, 5.0,
    \legato, 1,
    \root, 1,
    \attack, 0.2,
    \release, 0.2,
    \degree, Pseq([[-7, -3, 0, 2], [-7, -2, 0, 3], [-7, -1, 1, 4]].mirror1, inf)
).play;
)



(
Pbind(
	\instrument, \flute_waveguide,
	\degree, Pseq([0, 1, 2, 3, 4], inf),
	\dur, Pwhite(0.5, 1),
	\amp, Pwhite(0.1, 0.4),
	\ipress, Pseq([Pn(0.9, 5), Pn(0.6, 5)], inf),
	\ibreath, Pseq([Pn(0.09, 10), Pn(0.01, 10)], inf)
).trace.play
)

(
Pbind(
	\instrument, \waveguideFlute,
	\degree, Pbrown(-5, 14, 3),
	\dur, Pwrand([1/4, 1/8, 1/2], [0.7, 0.1 ,0.2], inf),
	\db, Pwhite(-23,-20 ),
	//\ipress, Pseq([Pn(0.9, 5), Pn(0.6, 5)], inf),
	\ibreath, Pbrown(0.09, 0.01, 0.005)
).play
)

//  //  //  //  //  //  //  //
////       Strings         ////
//  //  //  //  //  //  //  //

(//Need to wait a bit
	Pbind(
        \instrument, \strings,
        \degree, Pseq([
        			Pn(\rest, 2),
        			Pshuf([0, 2, 3, 5], 10),
        			Pseq([\rest], { 9.rand })
        			], inf)
        			+ Prand([0, 0, 0, [0, 3], [0, 1]], inf),
        \dur, Pseq([1, 2, 0.3, 0.5, 0.5], inf) + (Prand([0.3, 0, 0.2], inf) * 0.1),
        \detune, Pseg(Pwhite(-2, 2, inf), 1.3)
       ).play
);

(
p = Pbind(
	\instrument, \violin,
	\midinote, Prand( Scale.majorPentatonic.degrees, inf) + 60,
	\dur, 3,
	\legato, 1
).play;
)

// play a chord (farfisa-like sound)
(
[60, 64, 67].do ({
	| note |
	Synth(\violin, [\midinote, note]);
})
)



//  //  //  //  //  //  //  //
////      Percussion      ////
//  //  //  //  //  //  //  //


(
Pbind(
    \instrument, \kalimba,
    \dur, Pseq([0.3, 0.15], inf),
    \amp, 0.1*(2**Pgauss(0, 0.1)),
    \mix, Pwhite(0.05, 0.15),
	\degree, Pseq([0, -3, [1, 4], 2, Rest(), 1, -3, -2, -4, -2, [0, 5], 1, Rest(), 0, -2, Rest()], inf)
).play;
)

(
Pbind(
	\instrument, "marimba1",
	\degree, Pwhite(-10, 10),
	\dur, Pwhite(0.2, 0.3),
	\db, Pwhite(-20, -25)
).play;
)

(
Pbind(
	\instrument, \bell_tubular,
	\midinote, Prand( (60..72), inf),
	\dur, 1
).play;
)


(
Pbind(
	\instrument, \glockenspiel,
	\degree, Prand( (0..7), inf),
	\t60, 6,
	\dur, 0.25
).play;
)

// xilophone
(
Pbind(
	\instrument, \xilophone,
    \degree, Prand( (-7..14), inf),
	\dur, 0.25
).play;
)

// marimba
(
Pbind(
	\instrument, \marimba,
    \degree, Prand( (-7..14), inf),
	\dur, 0.12
).play;
)



(
Pdef(\bell_1,
  Pmono(\prayer_bell,
    \dur, Pseq([8, 20], inf),
    \freq, Pseq([2500, 500], inf),
    \amp, 0.5,
    \lag, 0,
    \trig, Pseq([0.1], inf) * Pwhite(0.5, 1, inf) * Pwrand([0, 1], [1, 5].normalizeSum, inf),
    \sing_switch, Pseq((0!4) ++ (1!4), inf)
  )
);

Pdef(\bell_2,
  Pmono(\prayer_bell,
    \dur, Pwhite(8, 20, inf),
    \trig, Pwhite(0.05, 0.09),
    \sing_switch, Pwrand([0, 1], [5, 3].normalizeSum, inf),
    \freq, Prand((240, 360 .. 2000), inf),
    \amp, 0.5
  )
);

Pdef(\bell_3,
  Ppar([
    Pmono(\prayer_bell,
      \freq, 100,
      \dur, 1,
      \trig, 0,
      \sing_switch, Pwrand([0, 1], [10, 3].normalizeSum, inf),
      \amp, Pwhite(0.1, 0.5)
    ),
    Pmono(\prayer_bell,
      \freq, 200,
      \dur, 1,
      \trig, 0,
      \sing_switch, Pwrand([0, 1], [10, 3].normalizeSum, inf),
      \amp, Pwhite(0.1, 0.5)
    ),
    Pmono(\prayer_bell,
      \freq, 300,
      \dur, 1,
      \trig, 0,
      \sing_switch, Pwrand([0, 1], [10, 3].normalizeSum, inf),
      \amp, Pwhite(0.1, 0.5)
    )
  ])
);

Pdef(\bell_1).play;
Pdef(\bell_2).play;
Pdef(\bell_3).play;
//by wondersluyter
//http://sccode.org/wondersluyter
)



//  //  //  //  //  //  //  //
////      Drum Kits      ////
//  //  //  //  //  //  //  //

(//straight
Ppar([
	Pbind(
		\instrument, "hihat",
		\dur, Pseq([Rest(1/4), 1/4], inf),
		\ffreq, 9000,
		\rel, 0.1,
		\amp, 0.2
	),
	Pbind(
		\instrument, "kick",
		\dur, 1/2,
		\amp, 0.9,
		\rel, 0.12,
		\glissf, 0.99
	),
		Pbind(
		\instrument, "snare",
		\dur, Pseq([Rest(1/2), 1/2], inf),
		\amp, 0.25,
		\rel, 0.1
	)
]).play;
)

(// a little bit groovy
//Ppar does not need to be used always
Pbind(
	\instrument, "hihat",
	\dur, Pseq([Rest(1/4), 1/8], inf),
	\att, 0.01,
	\rel, 0.1,
	\ffreq, 9000,
	\pan, 0,
	\amp, 0.3
).play;

Pbind(
	\instrument, "snare",
	\dur, Pseq([Rest(1/2), 1/2], inf),
	\att, 0.01,
	\rel, 0.1,
	\sinfreq, 180,
	\ffreq, 2000,
	\amp, 0.25

).play;

Pbind(
	\instrument, "kick",
	\dur, Pseq([1/2 + (1/4), 1/4], inf),
	\att, 0.01,
	\rel, 0.22,
	\sinfreq, 60,
	\glissf, 0.9,
	\amp, 1,
).play;
)



(// "hard coded" samba
//Ppar does not need to be used always
Pbind(
	\instrument, \SOShats,
	\dur, Pseq([ 1/8, Rest(1/8)], inf),
   \freq, 8000,
	\sustain, 0.4,
	\db, -35,
).play;

Pbind(
	\instrument, \SOSsnare,
	\dur, Pseq([1/4, Rest(1/2), 1/4], inf),
	\db, -35,

).play;

Pbind(
	\instrument, \SOStom,
	\dur, Pseq([Rest(1), 1 ], inf),
	\db, -20,
	\freq, 120,
).play;

Pbind(
	\instrument, \SOSkick,
	\dur, Pseq([1/2 + (1/4), 1/4], inf),
    \freq, 50,
	\db, -10,
).play;
)


(
var base;

base = Pbind(\amp, 0.3);

Ppar([
    Pbindf(
        base,
        \instrument, Pseq([\kick_electro, \snare_electro, \kick_electro, \kick_electro, \snare_electro], inf),
        \dur, Pseq([4, 3, 3, 2, 4], inf)
    ),
    Pbindf(
        base,
        \instrument, Pseq([Pn(\hihat_electro, 16), Pn(\clap_electro, 16)], inf),
        \dur, Pseq([Rest(2), 2, Rest(2), 2], inf)
    )
]).play(TempoClock(2.3 * 4));
)

(
Pbind(
	\instrument, \kick_808,
	\dur, 1/2,
	\db, -10,
	\rel, 0.7,
	\dist, 0.1
).play;
)


Synth(\SOSkick);
Synth(\SOSsnare);
Synth(\SOShats);
Synth(\SOStom);
Synth(\kick_electro);
Synth(\snare_electro);
Synth(\hihat_electro);
Synth(\clap_electro);
//  //  //  //  //  //  //  //
////       Others         ////
//  //  //  //  //  //  //  //

(
Pbind(
	\instrument, \blips1,
	\degree, Pseq([Pn(Pwhite(-10, 10, 1), 12), Pn(Rest(),3) ],inf ),
	\dur, 1/4,
	\db, -21,
	\numharm, Pbrown(5, 40,1),
	\beatsPercentage, Pstutter(12, Pseq([1.001, 1.005,  1.1, 1.5, 2, 2.001, 2.5, 3, 3.5, 4, 4.5, 5, 7,], inf)),
	\atk, 0.01,
	\rel, Pwhite(1/2, 1),
	\pan, Pwhite(-1, 1.0)
).play;
//80's like synth
//By Bruno Ruviaro
//https://github.com/brunoruviaro/SynthDefs-for-Patterns/blob/master/blips.scd
//Edits by Zé Craum
)


(
Pbind(
    \instrument, "sawSynth",
    \midinote, Pseq([
        [50, 53, 55, 57],
        [53, 56, 58, 60],
		Prand([
			[56, 59, 61, 63],
            [49, 52, 54, 56],
		], 1)
		],
        inf
    ),
    \dur, Prand([1, 3, 4, 4.5], inf),
	\att, 0.1,
	\rel, Pkey(\dur) + 1,
	\hifreq, 5000,
    \amp, 0.2
).play;

Pbind(
	\instrument, "sawSynth",
	\midinote, Pseq([36, 32, 32, 37], inf),
	\dur, Pseq([1/4, 1/4, 1/2, Rest(3), 1/4], inf),
	\ctranspose, [0, -12],
	\att, 0.01,
	\rel, 0.2,
	\amp, 0.4,
	\lofreq, 100,
	\hifreq, 1000
).play;
)

~voice = Buffer.read(s, Platform.resourceDir +/+ "sounds/a11wlk01.wav"); // remember to free the buffer later.
Synth(\sampler_PlayBuf, [\buf, ~voice, \rel, 14]);
Synth(\sampler_PlayBuf, [\buf, ~voice, \rel, 14, \midiratio, 5]);

(
Pbind(\instrument, \sampler_PlayBuf,
		\buf, ~voice.bufnum,
	    \midiratio, 0,
	    \pos, Pbrown(0.0, 1.0, 0.1, inf),
		\dur, Pwhite(0.07, 0.09, inf),
	    \rel, 0.2,
		\amp, 1.0
	).play
)

(
Pbind(
	\instrument, \sine_simple,
	\degree, Pwhite(0, 14),
	\detune, Pbrown(0, 50, 1),
	\atk, Pseq([Pn(0.001, 35),Pn(0.01, 20), Pn(0.1, 20)],inf),
	\sus, 0.2,
	\rel, 0.2,
	\db, Pwhite(-30, -20) ,
	\dur, 0.1
).play
)

(
Pbind(
	\instrument, \sine_vintage,
	\degree, Pwhite(-7, 14),
	\atk, Pseq([Pn(0.001, 35),Pn(0.01, 20), Pn(0.1, 20)],inf),
	\vdepth, Pseq([Pn(0.0001, 75),Pn(0.01, 75), Pn(0.1, 75)],inf),
	\vrate, Pseq([Pn(10, 75),Pn(1, 75), Pn(0.1, 75)],inf),
	\sus, 0.2,
	\rel, 0.2,
	\db, Pwhite(-30, -20) ,
	\dur, 0.1
).play
)


(
Pbind(
	\instrument, "noisy",
	\degree, Pseq([0, 1, 2, 3, 4, -10, -5], inf),
	\dur, Pwhite(0.09, 0.15),
	\amp, Pwhite(0.05, 0.2)
).play;
)




(
Pbind(
	\instrument,\prophet5pwmstrings,
	\midinote,Pseq([0,3,8,7,5,8,7,3]+48,inf),
	\dur,Pseq((0.5!8) ++ (0.25!8) ++ (2.0!8),inf),
	\lfowidth,Pn(Pseries(0.0,0.025,7),inf),
	\lforate,Pn(Pseries(2,1,11),inf),
	\rq,Pn(Pseries(0.9,-0.1,5),inf)
).play
)