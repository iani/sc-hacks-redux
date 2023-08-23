/* 22 Feb 2022 22:41
Shortcut for SendTrig with adjustable id.
Note: as of 2023 we use SendReply rather than SendReply in this library.
Example:
{ STrig(Impulse.kr(1)) } +> \x;

*/

STrig {
	*new { | ugen, id = 0 |
		^SendTrig.kr(ugen.kdsr, \id.kr(id))
	}
}
