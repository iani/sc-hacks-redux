// 木 15  5 2025 20:31
// automatically map a collection of named buses to the controls of a synth
// also handle creation/stopping of control synths that write to these buses
BusMap {
	var <buses, synths;

	*new { | keyVals |
		^this.newCopyArgs((), ()).init(keyVals);
	}

	init { | keyVals |
		keyVals keysValuesDo: { | k, v | dict[k] = k.bus(v);};
	}

	mapArgs {
		var args;
		dict keysValuesDo: { | k, v | args = args add: k; args = args add: v.index };
		^args;
	}
}
// TODO: should use new paused?
/*
a = BusMap((freq: 400))
a.mapArgs;

*/
// target, outbus: 0, fadeTime: 0.02, addAction: 'addToHead', args, player, envir)
+ Function {
	bmap { | bmap, target, outbus = 0, fadeTime = 0.02, addAction = 'addToHead', args |
		var synth;
		synth = this.play(target, outbus, fadeTime, addAction, args);
		synth.onStart({ | s |
			s.map(*bmap.mapArgs);
		});
	}
}

/*
a = BusMap((freq: 4000));
{ | freq = 400 | SinOsc.ar(freq, 0, 0.1) }.bmap(a);
*/
