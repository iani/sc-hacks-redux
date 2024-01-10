/* 29 Jul 2023 21:22
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

PlayerIdConverter {
	var <player, <conversionTables;

	*new { | player, conversionTables |
		^this.newCopyArgs(player, conversionTables);
	}

	convert { | id |
		var source, target, result;
		conversionTables do: { | t |
			#source, target = t;
			if (source includes: id) { result = target };
		};
		result ?? {
			postln("no conversion was found for id" + id);
		}
		^result
	}

	targets { // used to set menus for assigning sensor ids for controls
		^conversionTables.flop[1]
	}
}