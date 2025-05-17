// 土 17  5 2025 11:54
// f1 Play a function with output to a bus,
// add a fader to make it releaseable.
// Works for control rate as well as audio rate
// Args of Function:play :
// target, outbus: 0, fadeTime: 0.02, addAction: 'addToHead', args
// ff: Like f1, but release synths previously playing into the synth
// with ff.

+ Bus {

	ff { | func, target, fadeTime = 0.02, addAction = 'addToHead', args |
		var synth;
		this.changed(\play);
		synth = this.f1(func, target, fadeTime, addAction, args);
		synth.addNotifier(this, \play, { | n | n.listener.release });
		^synth;
	}

	f1 { | func, target, fadeTime = 0.02, addAction = 'addToHead', args |
		^func.ff(target, index, fadeTime, addAction, args);
	}

}