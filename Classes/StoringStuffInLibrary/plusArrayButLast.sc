/* 14 Oct 2022 14:15

Tentative.

2 Hacks that may be used.


*/

+ String {
	butLast {  ^this[..this.size-2] }
}

+ Array {
	butLast {
		^this[..this.size-2]
	}
	putLast { | object |
		this.put(this.size - 1, object);
	}
}

