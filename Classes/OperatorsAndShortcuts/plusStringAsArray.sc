/* 18 Aug 2023 07:55
*/

+ String {
	asArray { ^[this] }
	asCharArray {
		var result = [];
		this do: { | c | result = result add: c };
		^result;
	}
	sendCode { User.sendCode(this) }
}