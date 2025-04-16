/*  8 Jul 2023 11:33
Add number to symbol.
For creating symbols from sensor input symbols \x, \y, \z, etc.

\x.snum.postln.class;
\x.snum(8);
*/

+ Symbol {
	snum { | id = 1 | ^format("%%", this, id).asSymbol }
}