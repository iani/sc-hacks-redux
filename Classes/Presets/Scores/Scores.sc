/* 20 Aug 2023 19:56
Utility class: Return all score files in this folder
*/

Scores {

	*parentPath { ^PathName(this.filenameSymbol.asString).parentPath }

	*scores { ^PathName(this.parentPath +/+  "*.scd").pathMatch;}
	*scores1 { ^PathName(this.parentPath +/+ "scores1" +/+ "*.scd").pathMatch;}
	*scores2 { ^PathName(this.parentPath +/+ "scores2" +/+ "*.scd").pathMatch;}
	*scores3 { ^PathName(this.parentPath +/+ "scores3" +/+ "*.scd").pathMatch;}
	*scoreNames { ^this.scores collect: _.name; }

	*scorePath { | name | ^this.parentPath +/+ name ++ ".scd" }
}