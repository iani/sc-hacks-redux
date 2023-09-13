{arg out = 0, freq = 25, numharm = 10, atk = 0.01, rel = 1, amp = 0.1, pan = 0.5, beatsPercentage = 1.001;
	var snd, env;
	env = Env.perc(atk, rel, amp).kr(doneAction: Done.freeSelf);
	snd = LeakDC.ar(Mix(Blip.ar([freq, freq*beatsPercentage], numharm, env)));
	Out.ar(out, Pan2.ar(snd, pan));

//80's like synth
//By Bruno Ruviaro
//https://github.com/brunoruviaro/SynthDefs-for-Patterns/blob/master/blips.scd
//Edits by Zé Craum
}