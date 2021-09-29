/* 31 Jan 2021 12:22
Convert a trigger source to a synth function.
- Provide \in control
- Provide SendTrig ugen
- Provide gate control for releasing the synth
  (for compatibility with Mediator / handleReplacement method)
*/

+ Function {
	makeTrig { | values = 0 |
		^{ | id = 0 |
			//			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
			SendTrig.kr(this, id, values);
			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
		}
	}
	
}

+ Array {
	makeTrig {
		^this[0] makeTrig: this[1]
	}
}