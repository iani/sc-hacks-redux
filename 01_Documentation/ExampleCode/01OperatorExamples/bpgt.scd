//Fri 23 Feb 2024 09:25
//SmbolOperators.sc


+ Symbol {
		// // playing as synth is hardly used. Keep it for compatibility
	!+> { | player, envir |
	^this.pushPlayInEnvir(player, envir);
    }
}