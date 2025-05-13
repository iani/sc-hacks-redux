(
// activate typing notifier on the Document "The Sun Rising"
// the script below can be executed from any path
~curr = Array.newClear(30);
~moving_diff = Array.newClear(30);
~moving_diff_scores = 0!30;
~moving_diff_scores  = ~moving_diff_scores.asArray;
~tmp = "";
~cnt = 0;
\gdmusic.addNotifier(\typing, \click, { | n, char |
	// char.asString[0].class.postln;
	case
	{ (char.asString[0] == $$) && (char.ascii != [ 36, 92, 114] ) }{
		char.asString.last.post;
		~tmp = ~tmp ++ char.asString.last;
	}
	{ char.ascii == [ 36, 92, 114 ] }{
		"".postln;
		~cnt = 0;
		~tmp = "";
		~curr = Array.newClear(30);
		~moving_diff = Array.newClear(30);
		~moving_diff_scores = 0!30;
	};
	// char.ascii.postln;
	// ~verses[0].editDistance(~tmp);
	// ~verses do: { |v, idx| idx.post; " - ".post; v.editDistance(~tmp).postln; };
	~verses do: { |v, idx|
		// idx.post; " - ".post;
		~dist = v.editDistance(~tmp);
		~curr[idx] = ~curr[idx] ++ [~dist];
	};
	~curr do: { |v, idx|
		// idx.post; " >> ".post;
		~moving_diff[idx] = v.last / v[0];
		// (v.last / v[0]).postln;
	};
	if(~cnt > 0){
			// "TRUE\nTRUE\nTRUE".postln;
		~currIdx = ~moving_diff.removeNil.minIndex;
		~moving_diff_scores[~currIdx] = ~moving_diff_scores[~currIdx] + 1;
	};
	// increase counter
	~cnt = ~cnt + 1;
	// reset ~cnt and ~moving_diff_scores and ~moving_diff
	if(~cnt > 10){
		~maxitem = ~moving_diff_scores.maxItem;
		~sumscores = ~moving_diff_scores.sum - ~maxitem;
		if(~maxitem > ~sumscores){
			"\n>> VERSE: ".post; ~verses[~moving_diff_scores.maxIndex].postln;
			// ~cnt = 0;
			// ~tmp = "";
			\verse !!! ~verses[~moving_diff_scores.maxIndex];
			// ~curr = Array.newClear(30);
			// ~moving_diff = Array.newClear(30);
			// ~moving_diff_scores = 0!30;
		};
	};
	// substitute the above with what you want to
	// do instead. for example:
	(instrument: \pinch, freq: char.ascii*4, ffreq: char.ascii / 2).play;
	(instrument: \pinch, freq: char.ascii*8, ffreq: char.ascii / 4).play;
});
)

//: Run this to deactivate/remove your reaction
\gdmusic.removeNotifier(\typing, \click);


(
// sounds funny for Enter, Space and backspace
SynthDef(\pinch, { |freq=330, ffreq=110, pos=0.0, amp=1.0|
	var signal, env, conv, f1;
	f1 = LFSaw.kr(ffreq * 0.001).range(0,1);
	env = EnvGen.ar(Env.perc(0.01,0.1), doneAction: 2);
	signal = SinOsc.ar(freq+SinOsc.ar(ffreq,0,freq)).fold(f1.neg,f1) * env;
	conv = Limiter.ar(Convolution.ar(signal, Decay2.ar(Dust.ar(ffreq),0.01,f1/3)), 0.95);
	Out.ar(0, Pan2.ar(signal, pos, amp))
}).add;
)