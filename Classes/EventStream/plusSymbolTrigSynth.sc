/*  1 Feb 2021 05:47
Experimental operator : trigSynth
See EventStream:trigSynth
*/

+ Symbol {
	
	<<! { | source, synthKey = \default |
		this.trigSynth(source, synthKey);
	}

	trigSynth { | source, synthKey = \default |
		OscTrig.fromLib(this).addSynth(source, synthKey);
	}
}