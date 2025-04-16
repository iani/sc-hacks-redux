/* 10 Sep 2023 15:28

Some ad hoc temporary experimental operators for playing or
combining and playin chords.

Dedined both for Symbol and for String.

*/

+ Symbol {

	/> { | player, envir |
		Chord(this).playInEnvir(player, envir);
	}

}