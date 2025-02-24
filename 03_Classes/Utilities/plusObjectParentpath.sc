/* 13 Sep 2023 13:40
Return the path of the folder containing your definition.
*/

+ Object {
	*parentPath {
		^PathName(this.filenameSymbol.asString).parentPath
	}
}