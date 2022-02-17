/* 17 Feb 2022 07:17
Convert message to standard osc message format by prepending / if needed
*/

+ Object {
	asOscMessage {
		var message;
		message = this.asString;
		if (message[0] != $/) { message = "/" ++ message };
		^message.asSymbol
	}
}