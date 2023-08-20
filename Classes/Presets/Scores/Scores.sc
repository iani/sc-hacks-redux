/* 20 Aug 2023 19:56
Utility class: Return all score files in this folder
*/

Scores {

	*parentPath { ^PathName(this.filenameSymbol.asString).parentPath }

	*scores { ^PathName(this.parentPath +/+  "*.scd").pathMatch;}
	*scoreNames { ^this.scores collect: _.name; }

	*scorePath { | name | ^this.parentPath +/+ name ++ ".scd" }
}