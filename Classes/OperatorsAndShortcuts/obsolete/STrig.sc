/* 22 Feb 2022 22:41
Shortcut for SenTrigger with adjustable id.

Example:
{ STrig(Impulse.kr(1)) } +> \x;

*/

STrig {
	*new { | ugen, id = 0 |
		^SendTrig.kr(ugen.kdsr, \id.kr(id))
	}
}