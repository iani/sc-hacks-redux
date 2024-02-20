//Sun 18 Feb 2024 12:45 testing PathName methos for string
// When
"~/Dev/Test.scd".folderName.postln;
"~/Dev/".folderName.postln;
//:
PathName("~/Dev/Test.scd").fileNameWithoutExtension;
PathName("~/Dev/").fileNameWithoutExtension;
PathName("~/Dev").fileNameWithoutExtension;

PathName("~/Devx/").isFolder;

PathName("~/Dev/").pathOnly;
PathName("~/Dev/Test.scd").pathOnly;
PathName("~/Dev").pathOnly;
PathName("~/Dev").fullPath;

PathName("~/Dev/").pathOnly;
PathName("~/Dev/").fullPath;

"~/Dev/".basicName;

"~/Dev/Test.scd".basicName;
//:testing parentPath:
[
	// "",
	"/",
	"/usr",
	"/usr/",
	"~",
	"~/",
	"~/Dev",
	"~/Dev/",
	"~/NOTDev",
	"~/NOTDev/",
	"~/Dev/",
	"~/Dev/Test.sc", "~/Dev/Test"
] do: { | p |
	var pp;
	pp = PathName(p);
	postln("string" + p + "fullpath:" + pp.fullPath // + "pathOnly" + p.pathOnly
		// + "Pathname parentPath:" + pp.parentPath
		+ "folder" + p.pathOnly
		+ "superFolder (= up):" + p.up
	);
}

//:--- older tests
var s;
s = "~/Dev/Test.scd";
	// scroot { ^PathName.scroot }
s.scroot.postln;
    // fileName { ^PathName(this).fileName }
s.fileName.postln;
    // fileNameWithoutExtension { ^PathName(this).fileNameWithoutExtension }
s.fileNameWithoutExtension.postln;
    // extension { ^PathName(this).extension }
s.extension.postln;
    // pathOnly { ^PathName(this).pathOnly }
s.pathOnly.postln;
    // folder { ^this.pathOnly }
s.folder.postln;
	// lastColonIndex { ^PathName(this).lastColonIndex }
s.lastColonIndex.postln;
	// up { ^this.superFolder }
s.up.postln;
	// superFolder {
s.superFolder.postln;
    // isAbsolutePath { ^PathName(this).isAbsolutePath }
s.isAbsolutePath.postln;
    // isRelativePath { ^PathName(this).isRelativePath }
s.isRelativePath.postln;
    // asRelativePath { | relativeTo | ^PathName(this).asRelativePath(relativeTo) }
/// !!!!!!! Crash?
// s.asRelativePath("~/Documents/").postln;
    // folderName { ^PathName(this).folderName }
s.folderName.postln;
	// // !!!!! this causes seggmentation faul!!!!!!!!!!
    // // fullPath { ^PathName(this).fullPath } // WHY ????????
    // entries { ^PathName(this).entries }
"~".entries.postln;
    // files { ^PathName(this).files }
s.files.postln;
    // folders { ^PathName(this).folders }
//:
"~/Dev/".isFolder.postln;
//:
"~/Dev".folders.postln;
    // isFile { ^PathName(this).isFile }
    // isFolder { ^PathName(this).isFolder }
    // filesDo { | func | PathName(this).filesDo(func) }
	// allFolders { ^PathName(this).allFolders }
	// diskName { ^PathName(this).diskName }
	// endNumber { ^PathName(this).endNumber }
	// // +/+ { | path | ^(PathName(this) +/+ path).fullPath } // already exists
	// noEndNumbers { ^PathName(this).noEndNumbers }
	// nextName { ^PathName(this).nextName }
	// absolutePath { ^PathName(this).absolutePath }
	// deepFiles { ^PathName(this).deepFiles }
	// fileNameWithoutDoubleExtension { ^PathName(this).fileNameWithoutDoubleExtension }
	// parentPath { ^PathName(this).parentPath }
//:
"~/Dev".parentPath;
"~/Dev".up;