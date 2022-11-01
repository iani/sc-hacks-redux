/*  1 Nov 2022 08:10
Handle incoming OSC messages of the form:

[</incomingmessage>, id, x, y z]

Where </incomingmessage> is received from a sensor.
Normalize the values x, y, z received from the sensor by applying
linear mapping from lo - hi to 0.0 - 1.0.
Send the id and the normalized value to to oscgroups and to self,
using a different message: </forwardMessage> (to avoid infinite loops).

Does not use instances. Example:

MapXyz('/pinetime/data', '/pine', -1200.0, 1200.0);
does this:

When /pinetime/data is received, the x, y, z data are normalized
from -1200-1200 to 0-1, and then the message '/pine'
sent to oscgroups and to self, with the id, and the mapped x, y, z data.

*/

MapXyz {
	classvar maps, <inputs, <values;
	classvar localAddr;
	// var <inputMessage, <forwardMessage, <>lo = 0, <>hi = 1;

	*new { | inputMessage, forwardMessage, lo = 0, hi = 1 |
		inputMessage.asOscMessage >>>.mapxyz { | n, msg |
			var xyz, message;
			xyz = msg[2..].linlin(lo, hi, 0.0, 1.0);
			message = [forwardMessage, msg[1]] ++ xyz;
			OscGroups.send(*message);
			LocalAddr().sendMsg(*message);
		}
	}

}
