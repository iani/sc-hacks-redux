/* 30 Jan 2022 10:44
Adding some commmonly used ugens to a signal source:

Basic:
	Gated adsr envelope

To explore:
	PV effects
	Grain effects
	Pan (various versions)
	Resonance and filters
*/

+ Symbol {
	trigFilter { | trigger, start = 0, step = 1 |
		// filter the triggers by multiplying them with
		// your elements values in sequence
		^trigger * Demand.kr(trigger, 0, Dbufrd(this.buf, Dseries(start, step, inf)))
	}

	in { | numChannels = 2 | ^In.ar(this.ab(numChannels)) }
	ab { | numChannels = 2 | ^AudioBus(this, numChannels) }
	abc { | numChannels = 2 | ^\out.kr(AudioBus(this, numChannels).index.postln) }
}

