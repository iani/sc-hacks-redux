/* 18 Jul 2023 09:41
Simplify the loading of files from a directory, keeping
- action: the action to run with the result of the file loaded
  defaults to { | result | result.postln; }

*/

Load {
	*new { | object, action, message = "load files from path:" |
		///		Preferences2 ....
		// object.defaultpath, object.class ...
	}
}