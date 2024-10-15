//IZ January 2021 - posted in sc forum
//:Implementation 1 (plain)
(
~br = { | o = 1, p = 4 |
	(o / p * (0..p - 1)).floor.differentiate.asInteger.put(0, 1);
};
// test:
(0..12) do: { | i | postf("bresenham plain. i: %, rhythm: %\n", i, ~br.(i, 8)) };
)
/* // RESULT 1:
bresenham plain. i: 0, rhythm: [ 1, 0, 0, 0, 0, 0, 0, 0 ] // error
bresenham plain. i: 1, rhythm: [ 1, 0, 0, 0, 0, 0, 0, 0 ]
bresenham plain. i: 2, rhythm: [ 1, 0, 0, 0, 1, 0, 0, 0 ]
bresenham plain. i: 3, rhythm: [ 1, 0, 0, 1, 0, 0, 1, 0 ]
bresenham plain. i: 4, rhythm: [ 1, 0, 1, 0, 1, 0, 1, 0 ]
bresenham plain. i: 5, rhythm: [ 1, 0, 1, 0, 1, 1, 0, 1 ]
bresenham plain. i: 6, rhythm: [ 1, 0, 1, 1, 1, 0, 1, 1 ]
bresenham plain. i: 7, rhythm: [ 1, 0, 1, 1, 1, 1, 1, 1 ]
bresenham plain. i: 8, rhythm: [ 1, 1, 1, 1, 1, 1, 1, 1 ]
bresenham plain. i: 9, rhythm: [ 1, 1, 1, 1, 1, 1, 1, 1 ]
bresenham plain. i: 10, rhythm: [ 1, 1, 1, 1, 2, 1, 1, 1 ]
bresenham plain. i: 11, rhythm: [ 1, 1, 1, 2, 1, 1, 2, 1 ]
bresenham plain. i: 12, rhythm: [ 1, 1, 2, 1, 2, 1, 2, 1 ]
*/
//:Implementation 2 (allow o == 0, o > p)
(
~br1 = { | o = 1, p = 4 |
	(o / p * (0..p - 1)).floor.differentiate.asInteger.min(1)[0] = if (o <= 0) { 0 } { 1 };
	// Note: the if (o <= 0) statement covers the case when we want 0 beats
	// in the pattern
};
//
(0..12) do: { | i | postf("bresenham improved. i: %, rhythm: %\n", i, ~br1.(i, 8)) };
)
/* // RESULT 2:
bresenham improved. i: 0, rhythm: [ 0, 0, 0, 0, 0, 0, 0, 0 ]
bresenham improved. i: 1, rhythm: [ 1, 0, 0, 0, 0, 0, 0, 0 ]
bresenham improved. i: 2, rhythm: [ 1, 0, 0, 0, 1, 0, 0, 0 ]
bresenham improved. i: 3, rhythm: [ 1, 0, 0, 1, 0, 0, 1, 0 ]
bresenham improved. i: 4, rhythm: [ 1, 0, 1, 0, 1, 0, 1, 0 ]
bresenham improved. i: 5, rhythm: [ 1, 0, 1, 0, 1, 1, 0, 1 ]
bresenham improved. i: 6, rhythm: [ 1, 0, 1, 1, 1, 0, 1, 1 ]
bresenham improved. i: 7, rhythm: [ 1, 0, 1, 1, 1, 1, 1, 1 ]
bresenham improved. i: 8, rhythm: [ 1, 1, 1, 1, 1, 1, 1, 1 ]
bresenham improved. i: 9, rhythm: [ 1, 1, 1, 1, 1, 1, 1, 1 ]
bresenham improved. i: 10, rhythm: [ 1, 1, 1, 1, 1, 1, 1, 1 ]
bresenham improved. i: 11, rhythm: [ 1, 1, 1, 1, 1, 1, 1, 1 ]
bresenham improved. i: 12, rhythm: [ 1, 1, 1, 1, 1, 1, 1, 1 ]
*/
