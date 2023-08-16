/* 16 Aug 2023 18:31
Redoing SensorCtl to use snippets.
*/

SensorCtl2 {
	var <id = 0;
	var <map = \exp, <lo = 0.1, <hi = 1.0, <lag = 0.01;
	var <ufunc; // the function

	play { | player, param |
		if (id == 0) {
			this.stop(player, param);
		}{
			ufunc.(id, map, lo, hi, lag)
		};
	}

	stop { | player, param |
		// blah blah nil@>.player param; share!
	}
}