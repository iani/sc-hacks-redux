/* 28 Jan 2023 22:09
Semi-automated detection and mapping of sensor input.
*/

SensorMapper {
	classvar all, specs, messages;
	var <message, <id, <inputs, <values, busses, <key, <envir;

	*initClass { ServerBoot add: this; }
	*doOnServerBoot { | server |
		// workaround for a bug: make sure the server is booted:
		server doWhenBooted: { // remake busses
			this.all.leaves.flat do: _.makeInputs;
		}
	}

	doesNotUnderstand { | message |
		var bus;
		bus = busses[message];
		if (bus.isNil) {
			postln("Message" + message + "not understood by" + this);
		}{
			^bus;
		};
	}

	all { ^this.class.all }
	*all { ^all ?? { all = MultiLevelIdentityDictionary() } }
	*specs { ^specs ?? { specs = this.makeSpecs } }
	*makeSpecs {
		specs = ();
		specs['/accldata'] = [
			[\x, -86, 86],
			[\y, -100, 100],
			[\z, -189, 172]
		]
	}

	*addMessage { | message | this.messages add: message.asSymbol }
	*messages { ^messages ?? { messages = Set() } }

	*new { | message, id = 0 |
		^this.newCopyArgs(message.asSymbol, id).init;
	}

	init {
		key = format("%%", message, id).asSymbol;
		envir = key.envir;
		Mediator.addGlobal(this);
		this.makeInputs;
		this.class.addMessage(message);
		this.all.put(message, id, this);
	}

	makeInputs {
		inputs = this.specs[message] collect: { | spec, valueIndex |
			ScalarMap(message, id, valueIndex + 2, *spec)
		};
		busses = ();
		inputs do: { | i |
			busses[i.valuename] = i.bus
		};
	}

	*init { this.enable; }
	*enable { this.all; OSC addDependant: this }
	*disable { OSC removeDependant: this }

	*update { | osc, message, args |
		if (all includes: message) { this.respondTo(message, args) }
	}

	*respondTo { | message, args |
		var input;
		input = all.at(args[0], args[1]);
		input ?? {
			input = this.new(message, args[1]);
		};
		input(args);
	}

	update { | args |
		values = inputs collect: _.input(args);
	}


	*plot { | message, id |
		Registry(\plot, message, id, {  }) // ...
	}

	*monitor { | message, id |
		Registry(\plot, message, id, {  }) // ...
	}
}

ScalarMap {
	var <sensorname, valueindex, <valuename = \x, <>min = -1, <>max = 1;
	var <val = 0, <bus, <busindex;
	// var <>server; // may be used for faster bus setting method

	*new { | sensorname, valueindex = 1, valuename = \x,
		min = -1, max = 1 |
		^this.newCopyArgs(sensorname, valueindex, valuename, min, max).init;
	}

	init {
		bus = valuename.bus(nil, sensorname);
		busindex = bus.index;
		// server = Server.default; // may be used for faster bus setting method
	}

	input { | args |
		// this.adaptBounds(args[valueindex]);
		val = args[valueindex].linlin(min, max, 0, 1);
		bus.set(val);
		^val;
	}

	adaptBounds { | inputValue | // adapt min or max if required
		min = min min: inputValue;
		max = max max: inputValue;
	}
}