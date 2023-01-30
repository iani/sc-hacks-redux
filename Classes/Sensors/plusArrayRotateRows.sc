/* 27 Jan 2023 14:48
Utilites for real-time plotting
*/

+ Array {
	replace { | index = 0, subarray | // modifies me!
		subarray do: { | val, i |
			this.put(i + index, val)
		}
	}
	rotateRows { | i = 1 | ^this collect: _.rotate(i) }
	rotateAddColumn { | col | // modifies me!
		col do: { | val, i | this[i][0] = val };
		^this.rotateRows(-1);
	}
}
