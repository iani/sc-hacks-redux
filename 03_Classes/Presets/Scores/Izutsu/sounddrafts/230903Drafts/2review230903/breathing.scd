//from: https://sccode.org/1-57l#c1037
//
//:
//
(
s.options.memSize_(64000);

TempoClock.tempo_(1.0);

s.waitForBoot({
	SynthDef(\breathe_in, {
		| out = 0, gate = 1, in1=0.11, in2=0.63, in3=0.18, amdb= -10 |
		var menv = EnvGen.kr(Env.new([0,1,1,0],[in1, in2, in3], curve:'lin'),
			                 gate,
			                 doneAction:Done.freeSelf);
		var noise = WhiteNoise.ar(amdb.dbamp);
		var f1 = BPF.ar(in:noise, freq:5790, rq:0.1, mul:-29.dbamp);
		var f2 = BPF.ar(in:noise, freq:3400, rq:0.2, mul:-16.dbamp);
		var f3 = BPF.ar(in:noise, freq:1990, rq:0.1, mul:-23.dbamp);
		var f4 = BPF.ar(in:noise, freq:1250, rq:0.1, mul:-26.dbamp);
		Out.ar(out, menv*(f1+f2+f3+f4)!2);
	}).add;

	SynthDef(\breathe_out, {
		| out = 0, gate=1, out1=0.343, out2=0.387, amdb= -4.33 |
		var menv = EnvGen.kr(Env.new([0, 1, 0], [out1,out2], curve:'lin'),
		                    gate,
		                    doneAction:Done.freeSelf);
		var noise = WhiteNoise.ar(amdb.dbamp);
		var f1 = BPF.ar(in:noise, freq:2290, rq:0.1, mul:-17.dbamp);
		var f2 = BPF.ar(in:noise, freq:1290, rq:0.1, mul:-19.dbamp);
		var f3 = BPF.ar(in:noise, freq:490, rq:0.1, mul:-18.dbamp);
		Out.ar(out, menv*(f1+f2+f3)!2);
	}).add;


	s.sync;

	fork {
		50.do({
			var in1 = 0.11.rrand(0.22);
			var in2 = 0.6.rrand(0.8);
			var in3 = 0.16.rrand(0.30);
			var pausein = 0.11.rrand(0.20);
			var out1 = 0.30.rrand(0.50);
			var out2 = 0.36.rrand(0.50);
			var pauseout = 0.11.rrand(0.20);
			var amdb = -10.0.rrand(-14.0);
			var amdb2 = amdb/2.5;
			//("in1: " ++ in1 ++ " in2: " ++ in2 ++ " in3: " ++ in3 ++ " pausein: " ++ pausein ++ " out1: " ++ out1 ++ " out2: " ++ out2 + " pauseout: " ++ pauseout).postln;
			Synth(\breathe_in, [\in1, in1, \in2, in2, \in3, in3, \amdb, amdb]);
			(in1+in2+in3+pausein).wait;
			Synth(\breathe_out, [\out1, out1, \out2, out2, \amdb, amdb2]);
			(out1+out2+pauseout).wait;
		});
	};

});
);