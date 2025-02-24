/* 28 Jan 2023 22:09
Semi-automated detection and mapping of sensor input.
*/

Sensors {
	classvar all, specs, messages;
	var <message;
	var <id;
	var <inputs;
	var <values;
	var <busses;
	var <key;
	var <envir;

	*initClass { ServerBoot add: this; }
	*doOnServerBoot { | server |
		// "Sensors do on server boot".postln;
		// workaround for a bug: make sure the server is booted:
		server doWhenBooted: { // remake busses
			this.makeMessages;
			this.all.leaves.flat do: _.makeInputs;
		}
	}

	*makeMessages {
		this.messages;
		this.specs.keys do: messages.add(_);
	}

	*new { | message, id = 0 |
		^this.newCopyArgs(message.asSymbol, id).init;
	}

	init {
		key = format("%%", message, id);
		if (key[0] === $/) { key = key[1..] };
		key = key.asSymbol;
		envir = key.envir;
		this.makeInputs;
		this.class.addMessage(message);
		this.all.put(message, id, this);
		Mediator.putGlobal(key, this);
	}

	makeInputs {
		inputs = this.specs[message] collect: { | spec, valueIndex |
			Sensor2Bus(message, valueIndex + 2, *spec)
		};
		values = inputs collect: _.val;
		busses = ();
		inputs do: { | i |
			busses[i.valuename] = i.bus
		};
	}

	// enable access to busses by their name.
	doesNotUnderstand { | message |
		var bus;
		bus = busses[message];
		if (bus.isNil) {
			postln("Message" + message + "not understood by" + this);
		}{
			^In.kr(bus.index);
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
		];
		^specs;
	}

	*addSpec { | message, spec |
		this.specs[message] = spec;
		this addMessage: message;
	}

	*addMessage { | message | this.messages add: message.asSymbol }
	*messages { ^messages ?? { messages = Set() } }


	specs { ^this.class.specs }

	*init { this.enable; }
	*enable { this.all; OSC addDependant: this }
	*disable { OSC removeDependant: this }

	*update { | osc, message, args |
		if (messages includes: message) { this.respondTo(message, args) }
	}

	*respondTo { | message, args |
		var input;
		input = all.at(args[0], args[1]);
		input ?? {
			input = this.new(message, args[1]);
		};
		input.input(args);
	}

	input { | args | values = inputs collect: _.input(args);}

	plot { ^this.class.plot(this); }
	*plot { | sensor |
		var poller, plotter;
		poller = this.pollRoutine;
		if (poller.isRunning.not) { poller.start };
		plotter = Registry(sensor, \plot, {
			0.dup(100).dup(sensor.values.size)
			.plot(sensor.key, minval: 0, maxval: 1.0);
		});
		plotter.parent.addNotifier(this, \sensors, { | n |
			plotter.setValue(
				plotter.value.rotateAddColumn(sensor.values), minval: 0, maxval: 1.0
			);
		});
		plotter.addNotifier(plotter.parent, \objectClosed, { plotter.objectClosed; });
		this.enable;
		^plotter;
	}

	*pollRoutine { ^PollRoutine(this, \sensors, 0.1) }
	*stopPlot { this.pollRoutine.stop; }

	*monitor { | message, id | // INCOMPLETE
		Registry(\plot, message, id, {  }) // ...
	}
	minval { ^inputs collect: _.min }
	maxval { ^inputs collect: _.max }
}

