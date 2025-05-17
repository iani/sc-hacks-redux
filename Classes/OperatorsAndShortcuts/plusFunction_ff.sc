//土 17  5 2025 11:43
// Provide Out and fader for playing a function
/*
{ WhiteNoise.ar(0.1) }.ff;
	translates to:
{ Out.ar(0, WhiteNoise.ar(0.1).f)}.play

*/
// Args of Function:play:
// target, outbus: 0, fadeTime: 0.02, addAction: 'addToHead', args

+ Function {
		ff { | target, out = 0, fadeTime = 0.02, addAction = \addToHead, args |
		^{
			var src, outMethod;
			src = this.value;
			outMethod = (src.rate == \audio).if { \ar } { \kr };
			Out.perform(outMethod, out, src.f);
		}.play(target, out ,fadeTime, addAction, args);
	}
}

/*
// NOTE: This does not work because the UGen created outside
// the synth function remains silent
+ UGen {
		ff { | out = 0, fadeTime = 0.02, args |
		^{
			var src, outMethod;
			src = this.value;
			src.postln;
			outMethod = (src.rate == \audio).if { \ar } { \kr };
			Out.perform(outMethod, out, src.f);
		}.play(outbus: out, fadeTime: fadeTime);
	}
}
*/

// examples:
/*

f = { | in |
	in.value;
};
//:
{ f.({ WhiteNoise.ar(0.1) }) }.play;
{ f.( WhiteNoise.ar(0.1) ) }.play;


*/