/* 27 Jan 2023 15:50
Adapting uppdr and lower bounds of data in running plot
- can be used also to adaptively
update mapping specs for input data from sensors.
*/


InputBuffer : Array2D {
	var <minSeed = -1.0, <maxSeed = 1.0;
	var min, max;

	calc { | cols |
		min ?? {
			min = minSeed ! cols.size;
			max = maxSeed ! cols.size;
		};
		// min =
	}
}
