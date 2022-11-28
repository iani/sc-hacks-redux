/* 28 Nov 2022 23:43
Safety to build into code evaluation.
Suggested by Julian Rohrhuber 28 Nov 2022 23:43 JST
*/

+ String {
	isSafe {
	// code from OpenObject avoidTheWorst method
	^code.find("unixCmd").isNil
	and: { code.find("systemCmd").isNil }
	and: { code.find("File").isNil }
	and: { code.find("Pipe").isNil }
	and: { code.find("Public").isNil }
	}
}
