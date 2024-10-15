/* 23 Jul 2023 18:54
Xyz greater than threshold, with lag.
*/

XyzGt {
	*new { | id = 1 |
		^{ (Xyz(id).lag(1) - 0.2 max: 0 * 2).lag(0.5) };
	}
}