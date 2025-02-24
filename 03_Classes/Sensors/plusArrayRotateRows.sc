/* 27 Jan 2023 14:48
Utilites for real-time plotting
*/

+ Array {
	// NOTE: defining a replace method here would conflict
	// with ArrayedCollection:replace
	// and breaks synth concurrent playing.
	// relevant error message dump passage is:
	// 	Array:replace
	// 	arg this = [*6]
	// 	arg index = <instance of OSCFuncAddrMessageMatcher>
	// 	arg subarray = <instance of OSCFuncAddrMessageMatcher>
	// FunctionList:replaceFunc
	// 	arg this = <instance of FunctionList>
	// 	arg find = <instance of OSCFuncAddrMessageMatcher>
	// 	arg replace = <instance of OSCFuncAddrMessageMatcher>
	// < FunctionDef in Method AbstractWrappingDispatcher:updateFuncForFuncProxy >

	replacePart { | index = 0, subarray | // modifies me!
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
