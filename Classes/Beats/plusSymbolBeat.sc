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

// + EventStream {



// }
