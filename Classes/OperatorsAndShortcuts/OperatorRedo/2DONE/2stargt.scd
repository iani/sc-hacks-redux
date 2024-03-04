//Fri 23 Feb 2024 11:41
//FunctionOperators

+ Function {
	**> { | player, envir | // infinite loop in envir
		{
			inf do: {
				var dur;
				dur = this.(player, envir);
				if (dur.isKindOf(SimpleNumber).not) { dur = 1 };
				dur.wait;
			}
		}.routineInEnvir(player, envir);
	}
}