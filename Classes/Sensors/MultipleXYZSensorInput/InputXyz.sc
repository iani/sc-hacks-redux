/*  1 Nov 2022 11:00
Receive messages forwarded from MapXyz, in following format:
[<message>, id, x, y, z]

Where id is the sensor id and x, y, z, are normalized sensor values.

Store the values in corresponding slots in a values array, which can be used
for display (e.g. MultiSliderView).
Also set the corresponding busses to these values.

Example: receiving ['/pinetime/data', 2, 0.1, 0.2, 0.3]
will set the following bus-value pairs:

	[\x2, 0.1]
	[\y2, 0.2]
	[\z2, 0.3]

To enable this class, one must call its enable method.
The enable method does this:
- creates a value array
- creates the busses (named x1, y1, z1, x2, y2, ...)
- creates an array of instances which store the busses.
- binds the messages
*/

InputXyz {
	classvar <values, <instances;
	var <id, <valueIndex, <busses;
	*enable { | numIds = 30 |
		postln("InputXyz: Creating" + numIds + "x-y-z bus groups");
		values = 0.dup(numIds * 3);
		Server.default.waitForBoot({// start ids at 1 instead of 0:
			instances =  numIds collect: { | i | this.new(i + 1) }
		})
	}

	*new { | id |
		^this.newCopyArgs(id, id * 3).makeBusses;
	}

	makeBusses {
		post("InputXyz creates busses for id" + id + " : ");
		busses = [\x, \y, \z] collect: { | d |
			post(format("%%, ", d, id));
			format("%%", d, id).asSymbol.sensorbus
			// Mediator.at(\sensors);
		};
		postln("");
	}

	*addMessage { | message = '/minibee' |
		message.asOscMessage >>>.inputxyz { | n, msg |
			var index; // provide safe index:
			// Silent automatic correction could overwrite data from other sensor:
			// index = instances.size - 1 min: msg[1] max: 0;
			index = msg[1];
			if (index > instances.size or: { index < 0 }) {
				postln("index" + index + "out of range for message" + message);
			}{
				instances[index].setValues(msg[2..])
			}
		}
	}

	setValues { | xyz |
		xyz do: { | v, i |
			values[i + valueIndex] = v;
			busses[i].set(v);
		};
		this.class.changed(\sensors, [values]);
	}
	*gui {
		this.tr_.vlayout(
			MultiSliderView()
			.size_(3 * 25)
			.addNotifier(this, \sensors, { | n, values |
				n.listener.value_(values);
			})
		)
	}
}
