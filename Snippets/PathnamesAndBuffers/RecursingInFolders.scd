/* 10 Feb 2021 22:04
Reading files from subfolders of a folder.
*/
//:Finding all non-hidden items in a folder
PathName("/home/iani/Dev/SC/LibsByOthers/microloop-project").entries;
//:
PathName("/home/iani/Dev/SC/LibsByOthers/microloop-sounds/Material").entries do: { |e|
	e.fullPath.asCompileString.postln;
	e.isFolder.postln;
}
//:Select folders only

PathName("/home/iani/Dev/SC/LibsByOthers/microloop-sounds/Material").entries
.select({ | p | p.isFolder });