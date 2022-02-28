/* 31 Jan 2021 12:22
Convert a trigger source to a synth function.
- Provide \in control
- Provide SendTrig ugen
- Provide gate control for releasing the synth
  (for compatibility with Mediator / handleReplacement method)
*/

+ Function {
	makeTrig { | values = 0 |
		// IS THIS USED???? DEPRECATED?
		^{ | id = 0 |
			//			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
			SendTrig.kr(this, id, values);
			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
		}
	}
}

+ Symbol {
	// 27 Feb 2022 16:25 : this is better!
	// This does not yet work. Need to rebuild the code in detail
	makeTrig { | trigger, values |
		var message;
		message = this.asOscMessage;
		trigger ?? { trigger = { Impulse.kr(2) } };
		values ?? { values = 0 };
		{   // Make trigger available to values function!
			var trigger;
			trigger = trigger.value;
			SendReply.kr(trigger, message, values.(trigger));
			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
		}.play;
	}
	// Return synth func to use with +>
	makeTr { | trigger, values |
		var message;
		message = this.asOscMessage;
		trigger ?? { trigger = { Impulse.kr(2) } };
		values ?? { values = 0 };
		^{
			trigger = trigger.value;
			SendReply.kr(trigger, message, values.(trigger));
			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
		};
	}
}
