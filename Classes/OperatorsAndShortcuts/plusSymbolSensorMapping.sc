/* 12 Jul 2023 15:42

* Summary:

1. Define shortcut methods sensor: Symbol:ml, mw (maplin and mapexp) args: (min, max, player, param)
2. Define shortcut methods Function:gt and lt (sensor, thresh, player, lagtime)
3. Minibee should also sum all x,y,z inputs from each sensor and send them to bus xyz.
4. Define shortcut method Function:xyzenv(sensorid, player, lag)

*/

+ Symbol {
	ml { | min, max, param, player |
		{ this.sr.lin(min, max) }.perform('@>', param, player);
	}
	el { | min, max, param, player |
		{ this.sr.exp(min, max) }.perform('@>', param, player);
	}
}