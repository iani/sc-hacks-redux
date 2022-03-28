/*  2 Mar 2022 14:53
Simulating a 1d cellular automaton with rules from Steven Wolfram.

https://mathworld.wolfram.com/ElementaryCellularAutomaton.html
https://en.wikipedia.org/wiki/Rule_30

*/

Wolfram {
	var <ruleCode; // the number that produced the rule. For checking purposes.
	var <>startingState; // the state at the very beginning of the simulation.
	// startingState can be used to reset the simulation to the beginning at any point.
	var <>rule; // the rule for producing nextState from current state.
	var <currentState; // the current state being proceessed to produce the next state.
	var <nextState; // the next state being produced from the current state.
	var <states; // a history of all current states procudec.

	*new { | rule = 30, startingState |
		^this.newCopyArgs(rule, startingState).init;
	}

	init {
		this.makeRule(ruleCode);
		startingState ?? { startingState = (0 ! 10) ++ [1] ++ (0 ! 10) };
		if (startingState isKindOf: String) {
			var newss;
			startingState do: { | d | newss = newss add: d.asString.interpret };
			startingState = newss;
		};
		currentState = startingState;
		states = [currentState];
	}

	makeRule { | argRuleCode = 30 |
		// calculate the array of integer -> next state correspondences
		// from the single 8-bit integer encoding of the rule
		// according to the Wolfram classification system.
		// For example, see https://en.wikipedia.org/wiki/Rule_30
		ruleCode = argRuleCode;
		rule = ruleCode.asBinaryDigits(8).reverse;
	}

	postRule {
		// post rule in human readable form
		(7..0) do: { | i |
			postln("current pattern value" + i
				+ "from cell pattern" + i.asBinaryDigits(3)
				+ "new state for center cell" + rule[i]
			)
		}
	}

	triadCode { | cells |
		// binary value of 3 binary cell aray
		^(cells * [4, 2, 1]).sum;
	}

	nextCell { | neigbors |
		// get the value for the next cell based on the values of the 3 previous neigbor cells
		^rule[this.triadCode(neigbors)];
	}

	getNeigbors { | pos = 0 |
		// get the values of the cell neigbors at pos 0 from the currrent state array.
		// these are found at positions -1, 0 and 1 (left, center and right) relative
		// to the current position.
		^[currentState@@(pos -1), currentState@@(pos), currentState@@(pos + 1)];
	}

	nextCellAt { | pos |
		// get next value of cell for pos based on neighbor values at pos-1, pos, pos + 1
		// and the rule.
		^this.nextCell(this.getNeigbors(pos));
	}

	test1Step {
		// collect the next state array from the startingState;
		//		currentState = startingState;
		// ^(0..currentState.size).collect({ | pos | this.nextCellAt(pos) });
		^this.getNextState;
	}

	getNextState {
		^(0..currentState.size).collect({ | pos | this.nextCellAt(pos) });
	}

	run1Step {
		nextState = (0..(currentState.size-1)).collect({ | pos | this.nextCellAt(pos) });
		^currentState = nextState;
	}

	makeStates { | size = 100 |
		this.init;
		size do: {
			states = states add: this.run1Step;
		}
	}

	stateString { | index = 0 |
		^"".catList(states[index] collect: _.asString);
	}

	listStates {
		states do: { | s, i |
			this.stateString(i).postln;
		}
	}
	plot {
		// /* this. */// reuse window when replotting.
		ruleCode.asSymbol.window({ | w |
			w.view.background_(Color.white)
		})
		.name_(format("rule %", ruleCode))
		.bounds_(Rect(0, 0, 800, 800))
		.drawFunc_({
			states do: { | row, i |
				row do: { | s, j |
					if (s == 1) {
						Pen.color = Color.black;
						Pen.addRect(Rect(j % 400 * 2, i * 2, 2, 2));
						Pen.fill;
					}
				}
			}
		})
	}
}