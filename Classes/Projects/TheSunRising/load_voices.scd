{ | p |
	var incipits, bufferDict;
	incipits = [
		\busy,
		\whyDost,
		\through,
		\must,
		\saucy,
		\late,
		\go,
		\call,
		\love,
		\nor,
		\thy,
		\whyShouldst,
		\i,
		\but,
		\if,
		\look,
		\whether,
		\be,
		\ask,
		\and,
		\shes,
		\nothing,
		\princes,
		\all,
		\thou,
		\in,
		\thine,
		\to,
		\shine,
		\this,
	];
	postln("I will load the voice audiofiles from this script file" + p.fileName);
	"The full path name is".postln;
	p.postln;
	p.folders do: {  | q, i |
		var audioFiles, bufkey;
		postln("Loading audio files from folder" + q.fullPath.folderName);
		i = (i + 1).asString;
		q.fullPath.entriesMatchingWav do: { | w |
			var num, buffers;
			buffers = Array.newClear(30);
			// w.fileName.postln;
			num = w.fileNameWithoutExtension.split($-)[2].interpret;
			buffers[num] = Buffer.read(Server.default, w);
			bufkey = (incipits[num] ++ i).asSymbol;
			// bufkey.postln;
			// bufferDict[bufkey] = buffers[num];
			currentEnvironment.parent[bufkey] = buffers[num];
		};
	};
	/*
	"The wav files contained in the first folder are:".postln;
	p.folders.first.fullPath.entriesMatchingWav.postln;
	buffers = Array.newClear(30);
	bufferDict = ();
	p.folders.first.fullPath.entriesMatchingWav do: { | w |
		var num;
		w.fileName.postln;
		num = w.fileNameWithoutExtension.split($-)[2].interpret;
		buffers[num] = Buffer.read(Server.default, w);
		bufferDict[incipits[num]] = buffers[num];
		currentEnvironment.parent[incipits[num]] = buffers[num];
	};
	*/
};
