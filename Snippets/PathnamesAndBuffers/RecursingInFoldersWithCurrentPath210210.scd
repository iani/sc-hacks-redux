/* 11 Feb 2021 17:44
Find current file path and calculate sound dir from it.
THe sound dir could be a symbolic link.
*/
//:Get the path of this file:
thisProcess.nowExecutingPath;
//:Get the directory path of the currently executing file:
PathName(thisProcess.nowExecutingPath).pathOnly;
//:Add Sounds Folder
(PathName(thisProcess.nowExecutingPath).pathOnly +/+ "sounds/");
//:Add Sounds Folder
PathName(PathName(thisProcess.nowExecutingPath).pathOnly +/+ "sounds/").entries;
//:The code finished
~bufferFolders = IdentityDictionary();
PathName(PathName(thisProcess.nowExecutingPath).pathOnly +/+ "sounds/").entries
.select({ | p | p.isFolder }) do: { | q |
	~bufferFolders[q.folderName.asSymbol] = 
	q.fullPath.audioFiles collect: { | p |
		postf("Reading buffer at: %\n", p);
		Buffer.readWithInfo(Server.default, p)
	};
};
//:test again:
{
	~bufferFolders.values.flat do: { | b, i |
		postf("playing: %, %\n", i, b.path);
	b.play;
	0.1.wait;
	};
	postf("PLAYED % BUFFERS\n", ~bufferFolders.values.flat.size);
}.fork;
//:
~bufferFolders[\Tonal] do: { | b | b.numChannels.postln };
~bufferFolders.keys;
~bufferFolders[\tampouraki] do: { | b | b.numChannels.postln };