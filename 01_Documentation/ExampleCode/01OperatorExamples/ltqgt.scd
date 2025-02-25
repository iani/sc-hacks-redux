//Fri 23 Feb 2024 12:14
//SymbolOperators

+ Symbol {
	// ============================ Toggle. UNTESTED!!!
	<?> { | player, envir |
		^this.toggle(player, envir);
	}
	toggle { | player, envir |
		var process;
		Mediator.wrap({
			process = currentEnvironment[this];
			if (process.isPlaying) {
				process.stop
			}{
				process = player.playInEnvir(this, envir);
			}
		}, envir);
		^process;
	}
}