/* 29 Aug 2022 17:24
Simple scheme for loading scripts stored in the same directory
as a class.

Usage:

1. 	Define a subclass of this class, inside the folder that you want to put your scripts in.
	For example, folder "a".
	Note: This folder must be inside user extensions directory in order for your class to compile!
2. 	Place any scripts that you want to load inside folder "a" - i.e. the folder where your class is.
3. 	Send this class messages with the same name as
4.  Alternatively, you can specify the name of the script explicitly by sending the :
	MyScript
*/

ScriptLoader {
	// *testing {
	// 	"this is a test".postln;
	// }

	*doesNotUnderstand { | methodName |
		var path;
		path = (PathName(this.filenameSymbol.asString).pathOnly +/+ methodName.asString) ++ ".scd";
		postln("LOADING: " + path);
		OscGroups.disableCodeForwarding;
		path.load;
		OscGroups.enableCodeForwarding;
	}
}