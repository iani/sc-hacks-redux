/* 28 Nov 2022 23:43
Safety to build into code evaluation.
Suggested by Julian Rohrhuber 28 Nov 2022 23:43 JST
*/

+ String {
	isSafe {
	// code from OpenObject avoidTheWorst method
	^this.find("unixCmd").isNil
	and: { this.find("systemCmd").isNil }
	and: { this.find("File").isNil }
	and: { this.find("Pipe").isNil }
	and: { this.find("Public").isNil }
	}
}
