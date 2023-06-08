/*  7 Jun 2023 15:45
Simple specialized class for handling input from Minibee sensors.

~spec = [0.44, 0.56].asSpec;

~spec.unmap(0.55)

*/

Minibee {
	classvar <>sensormsg = '/minibee/data';
	classvar <all;
	classvar <>defaultmin = 0.44;
	classvar <>defaultmax = 0.56;
	var <id = 0;
	var <>specs
	var <busses;

	*initClass { ServerBoot add: this; }
	*doOnServerBoot { | server |
		// "Sensors do on server boot".postln;
		// workaround for a bug: make sure the server is booted:
		server doWhenBooted: { // remake busses
			// this.makeMessages;
			// this.all.leaves.flat do: _.makeInputs;
		}
	}

	*makeBusses {
		all do: _.makeBusses;
	}

	makeBusses {

	}

	*enable { OSC addDependant: this }
	*disable { OSC removeDependant: this }

	*update { | sender, cmd, msg |
		if (cmd === sensormsg) { msg.postln; }
		// Prototype 1:

		// msg[2..] do: { | val, index |
		// 	busses[index].set(specs[index].unmap(val)))
		// }
		//
		// Prototype 2
		// all do: {  }
		// busses do: { | b | b.map(msg[2+index]) }

		// Prototype 3
		// var index, inputvalues, mappedvalues;
		//   index = msg[1];
		// this.changed(\values, index,
		//    busses.collect()
		//)
	}
}
