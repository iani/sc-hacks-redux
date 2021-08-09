/*  9 Aug 2021 17:34
	Shortcuts for getting a buffer from a symbol.
	When using method Hacks.loadBuffers, each
	audio file found in ~/sc-hacks-config/audiofiles/ is loaded
	into a buffer, and stored in the default Library under Buffer class key,
	under a name generated from the its filename.

	Thus, an file placed here:
	~/sc-hacks-config/audiofiles/awlk10.wav
	will be loaded into a buffer and the buffer can be accessed like this:

	Library.at(Buffer, \awlk10);

	The shortcuts here allow one to access the buffers just from the
	symbol which is the name under which they are stored under the sceme
	explained above.

*/

+ Symbol {
	buffer { ^Library.at(Buffer, this); }
	// synonyms:
	b { ^this.buffer; }
	buf { ^this.buffer; }

}