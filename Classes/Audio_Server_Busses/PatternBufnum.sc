/*  3 Dec 2022 19:06
// Returns the bufnum corresponding to a symbol, for playing the buffer with Ppb

(instrument: \playbuf1, bufnum: \g.bufnum) +> \test;

(instrument: \playbuf1, bufnum: [\g, \crickets].pseq.bufnum) +> \test;
(instrument: \playbuf1, bufnum: [\g, \crickets].prand.bufnum) +> \test;

*/
+ Pattern {
	bufnums {
		^Pcollect({ | a |
			~bufnum = a.bufnum;
			~numChannels = a.numChannels;
			a.bufnum;
		}, this)
	}
}

+ Symbol {
	bufnums { ^[this].pseq.bufnums }
}