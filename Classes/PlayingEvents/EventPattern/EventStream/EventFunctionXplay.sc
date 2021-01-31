/* 31 Jan 2021 15:54
Play an Event explicitly as EventStream.

xplay is useful when dealing with a collection of Functions and Events
that one wants to play as Synths or EventStreams.
*/

+ Function {
	xplay { | target, outbus = 0, fadeTime = 0.02, addAction=\addToHead, args |
		^this.play(target, outbus, fadeTime, addAction, args);
	}
}

+ Event { xplay { | parent, quant, clock | ^this.splay(parent, quant, clock)} }
