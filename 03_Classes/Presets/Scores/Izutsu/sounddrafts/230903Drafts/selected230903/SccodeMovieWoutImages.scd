(
s.options.memSize_(650000);
s.waitForBoot({
	var perc1, perc2;
	var fastchimes1, phonedialing, phonebusy, animals;

	Ndef(\background,{
		var sig1;
		var si=SinOsc;
		var no=LFNoise1;

		sig1= DynKlang.ar(`[[si.kr(freq:1.32, phase:no.kr(5,1)).range(25,330),
			si.kr(freq:0.23, phase:no.kr(0.1,1)).range(20,800),
			si.kr(freq:0.1, phase:no.kr(0.1,1)).range(33,1000)] +
		si.ar([2, 3, 4.2], 0, [13, 24, 12]), [0.3, 0.3, 0.3], [pi, pi, pi]]);

		sig1 = 0.3*(sig1 + si.ar(265*no.kr(0.5,1), phase:si.kr(256, no.ar(0.1,3)), mul:no.kr(3,6)));

		9.do{
			sig1=AllpassL.ar(sig1,0.3,{0.2.rand+0.1}!2,5)
		};
		sig1 = sig1 / (SinOsc.ar(0.05,pi/2).range(3,10));
		sig1.tanh;
	});

	Ndef(\slowchimes, {
		var s, z, y;
		// 10 voices of a random sine percussion sound :
		s = Mix.ar(Array.fill(8, { Resonz.ar(Dust.ar(0.2, 50), 200 + 1000.0.rand, 0.003)}) );
		s = s + Mix.ar(Array.fill(2, { Resonz.ar(Dust.ar(0.1, 50), 200 + 4000.0.rand, 0.003)}) );
		// reverb predelay time :
		z = DelayN.ar(s, 0.048);
		// 7 length modulated comb delays in parallel :
		y = Mix.ar(Array.fill(7,{ CombL.ar(z, 0.1, LFNoise1.kr(0.05.rand, 0.04, 0.05), 15) }));
		// two parallel chains of 4 allpass delays (8 total) :
		4.do({ y = AllpassN.ar(y, 0.050, [0.050.rand, 0.050.rand], 1) });
		// add original sound to reverb and play it :
		(3*(s+(0.2*y))).tanh
	});

	SynthDef(\explosion, {
		var m = Line.kr(0.01, 0.8, Rand(0.01, 0.04));
		var x = SinOsc.ar(LFDNoise3.ar(m.range(18000, 2) ! 2) * (Rand(1, 2) * 600)) * m.exprange(1, 8) * XLine.kr(0.3, 0.0001, 5, doneAction:Done.freeSelf);
		Out.ar(0, x);
	}).add;

	SynthDef(\explosion2, {arg i_out, freq = 200, pan=0.5, amp=0.1;
		var ae,sig,finalsig;
		ae = EnvGen.kr(Env.asr(0, 1, 0.05, 1, 0));
		sig = Impulse.ar(0);
		sig = LPF.ar(FreeVerb2.ar(sig,sig,1,0.95,0.4),freq);
		sig = (sig*5000).tanh;
		sig = LPF.ar(sig,8000);
		finalsig = amp*ae*sig;
		DetectSilence.ar(finalsig, doneAction:Done.freeSelf);
		Out.ar(0, finalsig);
	}).add;

	Ndef(\irregularheartbeat, {
		//arg hold = 1;
		var env = EnvGen.ar(Env([0, 1, 0], [1, 3], 2, 1), /*hold,*/ doneAction: 2);
		var snd =
		SinOsc.ar
		(
			Impulse.ar(Saw.ar(1.3).range(1,5)).lagud(0,0.2)*360,
		) * env;
		Splay.ar(Array.fill(3, {0.2*snd.tanh}), spread:0, center:LFTri.ar(0.03).range(-1,1));
	});

	Ndef(\angrymandolin, {
        var freq, numparts;
        numparts = 50;
        freq = SinOsc.kr(Array.fill(numparts, {Rand(0.05, 0.2)}),
            Array.fill(numparts, {Rand(0, 1.0)})).range(1000, 3000);
        LeakDC.ar(
            Pan2.ar(
                Pluck.ar(
                    WhiteNoise.ar(0.1).dup(numparts),
                    Impulse.kr(Array.fill(numparts, {Rand(10, 12)})),
                    100.reciprocal, freq.reciprocal, 2, Rand(0.01, 0.2), mul: 1),
                Array.fill(numparts, {Rand.new(-1.0, 1.0)}))
            .sum
            );
	});

	perc1 = {
		// using CombC as an echo device
	{{(CombC.ar(Decay.ar(Dust.ar(0.1,0.5), LFTri.kr(0.11).range(0.5,0.7), BrownNoise.ar), 1, LFTri.ar(0.1).range(0.2,0.4), 3)  +
	   CombC.ar(Decay.ar(Dust.ar(0.1,0.5), LFTri.kr(0.13).range(0.2,0.3), Resonz.ar(WhiteNoise.ar)), 1, LFTri.kr(0.1).range(0.2,0.4), 3))}!2}
	};


	fastchimes1 = {
		var sig = Splay.ar(Array.fill(8, { CombC.ar(Decay.ar(Dust.ar(LFTri.kr(0.1).range(0.1,0.4),0.5), LFTri.kr(0.11).range(0.1,0.2), Pulse.ar(3000.rrand(6000),0.5)), 1, LFTri.ar(0.1).range(0.2,0.4), 3) }), spread:1);
		sig;
	};

	phonedialing = {
		var onoff;
		onoff = LFPulse.ar(1/6, width: 1/3);
		{SinOsc.ar([480, 440], 0, onoff).sum * 0.2}!2
	};

	phonebusy = {
		var onoff;
		onoff = LPF.ar(LFPulse.ar(2), 100);
		{SinOsc.ar([480, 620], 0, onoff).sum * 0.2}!2
	};

	animals = {
		var tr, e, eg, fm, maus;
		maus = LFTri.kr(0.23).range(0.1,2);
		e =  Env([0, 1, 0.4, 1, 0], [0.03, 0.2 * maus, 0.2, maus]);
		tr = Dust.kr(0.3);
		eg = EnvGen.kr(e, tr);
		fm = SinOsc.ar(30, 0, 0.2 * (1 - eg), 1);
		Pan2.ar(SinOsc.ar(100 * eg + 200 * fm) * 0.1 * eg, LFTri.ar(0.1).range(-1,1));
	};

	SynthDef(\breathe_in, {
		| out = 0, gate = 1, in1=0.11, in2=0.63, in3=0.18, amdb= -10 |
		var menv = EnvGen.kr(Env.new([0,1,1,0],[in1, in2, in3], curve:'lin'),
			                 gate,
			                 doneAction:Done.freeSelf);
		var noise = WhiteNoise.ar(amdb.dbamp);
		var specs = [[5790, 0.1, -29], [3400, 0.2, -16], [1990, 0.1, -23], [1250, 0.1, -26]];
		var filteredsig = specs.collect({ | spec | BPF.ar(in:noise, freq:spec[0], rq:spec[1], mul:(spec[2].dbamp)); }).sum;
		Out.ar(out, menv*filteredsig!2);
	}).add;

	SynthDef(\breathe_out, {
		| out = 0, gate=1, out1=0.343, out2=0.387, amdb= -4.33 |
		var menv = EnvGen.kr(Env.new([0, 1, 0], [out1,out2], curve:'lin'),
		                    gate,
		                    doneAction:Done.freeSelf);
		var noise = WhiteNoise.ar(amdb.dbamp);
		var specs = [[2290, 0.1, -17], [1290, 0.1, -19], [490, 0.1, -18]];
		var filteredsig = specs.collect({ | spec | BPF.ar(in:noise, freq:spec[0], rq:spec[1], mul:(spec[2].dbamp)); }).sum;
		Out.ar(out, menv*filteredsig!2);
	}).add;


	Tdef(\breathing, {
		loop {
			var in1 = 0.11.rrand(0.22);
			var in2 = 0.6.rrand(0.8);
			var in3 = 0.16.rrand(0.30);
			var pausein = 0.11.rrand(0.20);
			var out1 = 0.30.rrand(0.50);
			var out2 = 0.36.rrand(0.50);
			var pauseout = 0.11.rrand(0.20);
			var amdb = -10.0.rrand(-14.0);
			var amdb2 = amdb/2.5;
			Synth(\breathe_in, [\in1, in1, \in2, in2, \in3, in3, \amdb, amdb]);
			(in1+in2+in3+pausein).wait;
			Synth(\breathe_out, [\out1, out1, \out2, out2, \amdb, amdb2]);
			(out1+out2+pauseout).wait;
		}
	});

	s.sync;

	Ndef(\animals).fadeTime = 2;
	Ndef(\background).fadeTime = 0;
	Ndef(\slowchimes).fadeTime = 2;
	Ndef(\percussion).fadeTime = 2;
	Ndef(\fastchimes).fadeTime = 2;
	Ndef(\irregularheartbeat).fadeTime = 2;
	Ndef(\angrymandoline).fadeTime = 2;

	// the score
	fork {
		Tdef(\breathing).play;
		Ndef(\background).play;
		20.wait;
		Ndef(\animals, animals).play;
		10.wait;
		Ndef(\slowchimes).play;
		20.wait;
		Ndef(\percussion, perc1).play;
		Ndef(\fastchimes, fastchimes1).play;
		20.wait;
		Ndef(\fastchimes, phonedialing);
		20.wait;
		Ndef(\fastchimes, phonebusy);
		5.wait;
		Ndef(\fastchimes, fastchimes1);
		10.wait;
		Synth(\explosion);
		Ndef(\irregularheartbeat).play;
		Ndef(\angrymandolin).play;
		4.wait;
		Synth(\explosion2);
		Ndef(\fastchimes).stop(5);
		Ndef(\percussion).stop(7);
		Ndef(\background).stop(9);
		10.wait;
		Ndef(\slowchimes).stop(12);
		Ndef(\animals).stop(14);
		Ndef(\angrymandolin).stop(12);
		Ndef(\irregularheartbeat).stop(12);
		15.wait;
		Tdef(\breathing).stop;

		// clean-up after yourself
		Ndef(\fastchimes).end(5);
		Ndef(\percussion).end(5);
		Ndef(\slowchimes).end(5);
		Ndef(\background).end(5);
		Ndef(\animals).end(5);
		Ndef(\angrymandolin).end(5);
		Ndef(\irregularheartbeat).end(5);
		10.wait;
	};
});

)