/* 10 Feb 2021 22:04
Reading files from subfolders of a folder.
*/
//:Finding all non-hidden items in a folder

//:Select folders only
PathName("/home/iani/").entries select: _.isFolder;
PathName("/home/iani/").entries select: _.isFile;
//:Select folders only

PathName
PathName("/home/iani/Dev/SC/LibsByOthers/microloop-sounds/Material").entries
.select({ | p | p.isFolder });