/* 21 Feb 2022 08:10
Shortcuts for using BeatCounter.
*/

+ Symbol {
	beat { | dt |
		var bc;
		bc = Registry(BeatCounter, this, {
			BeatCounter()
		});
		dt !? { bc.dt = dt };
		^bc;
	}

	startBeat { ^this.beat.start }
	stopBeat { ^this.beat.stop }
}

+ Function {
	// there is no way to remove the function unless you have stored it somewhere
	beat { | beatName, actionName, dt |
		^beatName.beat(dt).add(actionName ? beatName, this);
	}
}

+ EventStream {
	addBeat { | beat |
		// FIXME: avoid adding self to same beat.
		// removee previous actions bound to this beat and self:
		beat.beat.remove(this); // remove previous action for self if it exists
		beat.beat.add(this, { this.next.play; }); // add new play action
	}

	removeBeat { | beat |
		beat.beat.remove(this);
	}
}

