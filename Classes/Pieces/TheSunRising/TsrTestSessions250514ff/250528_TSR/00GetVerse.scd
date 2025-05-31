//: Get verse incipit
// initialize
\v1 !!! ~verses1[0];
\v2 !!! ~verses2[0];
\v3 !!! ~verses3[0];
\v4 !!! ~verses4[0];
// trigger at each verse
Tsr.doOnVerse2({ | index, verse, incipit |
	\v1 !!! ~verses1[index];
	\v2 !!! ~verses2[index];
	\v3 !!! ~verses3[index];
	\v4 !!! ~verses4[index];
}, \ianiverse);
//:
// TEST

\v1 !!! ~verses1[0];

~v1;
//:
\test;

~verses1;

//:
		User setLAN: (
			iani: NetAddr("169.254.238.188", 57120),
			kosmas: NetAddr("169.254.238.187", 57120),
			vasilis: NetAddr("169.254.238.186", 57120),
			geodia: NetAddr("169.254.238.185", 57120),
			penny: NetAddr("169.254.238.184", 57120),
		);

//: