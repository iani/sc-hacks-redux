// Note: The first time you run this, Paths will
// open a FileDialog to prompt you to select a file to run.
// Select the file load_voices.scd from inside this folder.
// Paths will save the path that you selected.
// The next time you run this, it will load load_voices.scd
// from the same path.
Paths.doGetPath({ | p, paths |
	var loader;
	loader = User load: p;
	loader.(p);
}, \tsrvoices);