/* 10 Feb 2021 22:04
Reading files from subfolders of a folder.
*/
//:Finding all non-hidden items in a folder

//:Select folders only
PathName("/home/iani/").entries select: _.isFolder;
//:Select files only
PathName("/home/iani/").entries select: _.isFile;
//:Select folders and recurse over them.
PathName("/home/iani/Dev/SC/LibsByOthers/microloop-sounds/Material").entries
.select({ | p | p.isFolder }).do{ | q, i |
	postf("folder % : %\n", i+1, q.fullPath);
};
//:Recurse over all audio files in all subfolders
PathName("/home/iani/Dev/SC/LibsByOthers/microloop-sounds/Material").entries
.select({ | p | p.isFolder }) do: { | q |
	postf("AUDIO FILES CONTAINED IN SUBFOLDER '%' are:\n", q.folderName);
	q.fullPath.audioFiles do: _.postln;
	"================================================================".postln;
};
//:Read audio files from subfolders and place them in dictionary
// Dictionary keys are the folder names
// Dictionary values are all buffers read from each folder. 
~bufferFolders = IdentityDictionary();
PathName("/home/iani/Dev/SC/LibsByOthers/microloop-sounds/Material").entries
.select({ | p | p.isFolder }) do: { | q |
	~bufferFolders[q.folderName.asSymbol] = 
	q.fullPath.audioFiles collect: { | p |
		postf("Reading buffer at: %\n", p);
		Buffer.read(Server.default, p)
	};
};
//:check:
~bufferFolders.keys;
//:play a buffer:
~bufferFolders.values.first.first.play;
//:play all buffers:
{
	~bufferFolders do: { | f |
		f do: { | p |
			postf("playing %\n", p.path);
			p.play;
			0.25.wait;
		}
	};
}.fork;