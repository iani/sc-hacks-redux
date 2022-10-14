/* 14 Oct 2022 14:15

Tentative.

2 Hacks that may be used.


*/


+ Array {
	butLast {
		^this[..this.size-1]
	}
	putLast { | object |
		this.put(this.size - 1, object);
	}
}

