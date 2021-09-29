/*
11 Aug 2020 18:14

Objects that work with ActionQue.

Create a Buffer or Synthdef, and as soon as the Server has 
completed their creation, continue to the next que object.

Alternatively, evaluate a function and immediately continue
to the next object.

*/

ActionQueTemplate {
	// abstract superclass
	var  <actionQue, <template;

	*new { | actionQue, template |
		^this.newCopyArgs(actionQue, template);
	}
}

FunctionAction : ActionQueTemplate {
	sync { // runs next action without waiting for sync
		template.value;
		actionQue.next(this);
	}
}

BufferReadAction : ActionQueTemplate {
	sync { // waits for buffer to read before executing next action
		Buffer.read(
			actionQue.server,
			template,
			{ actionQue.next(this) }
		)
	}
}

SynthDefAction : ActionQueTemplate {
	sync { // INCOMPLETE!
		Buffer.read(
			actionQue.server,
			template,
			{ actionQue.next(this) }
		)
	}
}

SynthAction : ActionQueTemplate {
	sync { // INCOMPLETE!
		Buffer.read( // WRONG
			actionQue.server,
			template,
			{ actionQue.next(this) }
		)
	}
}