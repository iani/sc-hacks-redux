/*
Convert the ids stored in a dict - possibly from another player - into the ids required for the
current player.  This follows a mapping of all left hand ids and all right hand ids into
the left and right hand ids of the current player. Example:

For Jun:
[1, 3, 5, 9, 11] maps to: 1
[2, 4, 7, 8, 10, 12] maps to: 2 // NOTE: Mary uses 7 instead of 6!!!
For Yoshiitsu:
[1, 3, 5, 9, 11] maps to: 9
[2, 4, 7, 8, 10, 12] maps to: 10

There is one exception: Mary uses 7 instead of 6. TODO: Check the
sets for consistency with the Mary rule.



*/
// later substitute this with specs for players that translate sensor numbers?
[
	[\jun, [[[1, 3, 5, 9, 11], 1], [[2, 4, 7, 8, 10, 12], 2]]],
	[\asayo, [[[1, 3, 5, 9, 11], 3], [[2, 4, 7, 8, 10, 12], 4]]],
	[\justine, [[[1, 3, 5, 9, 11], 1], [[2, 4, 7, 8, 10, 12], 2]]],
	[\mary, [[[1, 3, 5, 9, 11], 5], [[2, 4, 7, 8, 10, 12], 7]]],
	[\yoshimitsu, [[[1, 3, 5, 9, 11], 9], [[2, 4, 7, 8, 10, 12], 10]]],
	[\sanya, [[[1, 3, 5, 9, 11], 11], [[2, 4, 7, 8, 10, 12], 12]]]
] collect: PlayerIdConverter(_);