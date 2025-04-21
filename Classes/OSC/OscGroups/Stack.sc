// Push and pop envirs
// 土 19  4 2025 20:08

Stack {
	var <>maxSize = 1000000;
	var <stack;
	push { | something |
		stack = stack add: something;
		if (stack.size > maxSize) {
			"Stack overflow. Discarding bottom element of stack".postln;
			stack = stack[1..];
		} // keep stack size finite
	}
	pop { // caller must check if stack is empty, when required
		var last;
		last = this.top;
		stack = (stack ? []).butLast;
		^last;
	}

	top { ^(stack ? []).last }
	size { ^stack.size }
	isEmpty { ^stack.size == 0 }
	reset { this.clear }
	clear { stack = nil }
}