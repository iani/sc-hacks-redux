/* 20 Aug 2023 20:01
Utility: Return scd files in this folder.
*/

Scripts {

	*parentPath { ^PathName(this.filenameSymbol.asString).parentPath }

	*scripts { ^PathName(this.parentPath +/+  "*.scd").pathMatch;}
	*scriptNames { ^this.scripts collect: _.name; }

}