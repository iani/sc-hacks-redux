/*  9 Mar 2022 05:30
- SenseData :: Actions to perform when receiving a message from SenseStage.
  - Attach a different action for each id (sensor module)
  - Write data received from an id to busses

*/

SenseData {
	classvar <>senseMessage = '/minibee/data';
	var <message; //
	var <>funcs;

	*new { | message |
		message = message ? senseMessage;
		^Registry(this, (message).asOscMessage,
			{ this.newCopyArgs(message).init }
		)
	}

	init { // subclasses may add stuff
		// this.enable;
		funcs = Array newClear: 10000;
	}

	enable {
		this.addNotifier(OSC, message, this);
	}

	disable {
		this.removeNotifier(OSC, message);
	}

	verbose { message.trace }
	silent { message.untrace }

	valueArray { | n, msg |
		funcs[msg[1] ? 0].value(*msg[2..]);
	}

	activeIds {
		var ids;
		funcs do: { | f, id | if (f.notNil) { ids = ids add: id } };
		^ids;
	}

	put { | id, action |
		funcs.put(id, action);
	}

	post { | ... ids |
		ids do: { | id |
			funcs[id] = { | x, y, z | postln("x:" + x + "y:" + y + "z:" + z) };
		};
		this.enable;
	}

	setxyz { | ... ids |
		ids do: { | id |
			var x, y, z;
			#x, y, z = ["x", "y", "z"].collect({ | s | format("%%", s, id).asSymbol.bus });
			funcs[id] = { | xv, yv, zv |
				x.set(xv);
				y.set(yv);
				z.set(zv);
			};
		};
		this.enable
	}

	*scope {
		Server.default.scope(12, rate: \control);
	}
}