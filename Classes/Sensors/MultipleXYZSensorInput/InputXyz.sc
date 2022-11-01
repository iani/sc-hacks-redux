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
		values = 0.dup(numIds * 3);
		Server.default.waitForBoot({
			instances =  numIds collect: { | i | this.new(i) }
		})
	}

	*new { | id |
		^this.newCopyArgs(id, id * 3).makeBusses;
	}

	makeBusses {
		busses = [\x, \y, \z] collect: { | d |
			format("%%", d, id).asSymbol.bus;
		}
	}

	*addMessage { | message = '/minibee' |
		message.asOscMessage >>>.inputxyz { | n, msg |
			instances[msg[1]].setValues(msg[2..])
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
			.addNotifier(this, \sensors, { | n, values |
				n.listener.value_(values);
			})
		)
	}
}
