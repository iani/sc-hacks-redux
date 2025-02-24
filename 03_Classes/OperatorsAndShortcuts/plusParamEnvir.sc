/*  1 Sep 2023 08:21
Implicit conversion of right arguments for +> etc.

Merge data provided in a binary operator in following order:
	[parameter, player] envir;
Return the following itemss in this order:
	[envir, player, parameter]
Use cases, providing 3, 2 and 1 arguments:

3 arguments (2 elements in array argument and one adverb):
	1 +>.test [\player1, \freq];
	Return: [\test, \player1, \freq];
2 arguments (one element in argument and one adverb):
	1 +>.test \freq;
	Return: [\test, \test, \freq];
1 argument (just one argument, no adverb):
	1 +> \freq;
	Return: [\default, \default, \freq];



*/

+ Nil {
	playerParam { | args |
		if (args.size == 0) { args = [\default, args] };
		^\default.playerParam(args);
	}
}

+ Symbol {
	playerParam { | args |
		if (args.size == 0) { args = [this, args] };
		^[this] ++ args;
	}
}
