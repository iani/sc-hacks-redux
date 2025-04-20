// Push and pop envirs
// 土 19  4 2025 20:08

Stack {
	var <stack;
	push { | something | stack = stack add: something }
	pop {
		var last;
		last = (stack ? []).last;
		stack = (stack ? []).butLast;
		^last;
	}
}