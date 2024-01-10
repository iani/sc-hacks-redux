/* 28 Aug 2023 10:51

Play Function like this:
1. in player 'player'
2. of environment 'envir',
3. writing its kr output to bus 'bus'.


The existing operator serving as model to extend is:

Function @>.player  \bus;
Where bus and player names are the same.
e.g.:

{ SinOsc.ar(10) } @>.player1 \amp;

Plays the function in player 'amp', in environment 'player1'

Here we want the function to play in player 'amp2', but still write
its ugen output to bus 'amp'. In the existing solution bus and player
names are the same, and envir name is different.
In the present solution, bus, player and envir names are different.

Proposed prototype:

{  } @@>.player [\bus, \envir];

All 3 arguments MUST be provided.

Existing implementation to extend:
+ Function {
	@> { | bus, player | // play as kr function in bus (or player) name
		player ?? { player = currentEnvironment.name };
		{
			Out.kr(bus.bus(nil, player), this.value.kdsr);
		}.playInEnvir(bus, player); // do not push envir !!!
	}
}

{ SinOsc.kr(5) } @@>.freq2 [\freq, \jun];

Example:

//:
{ SinOsc.ar(\freq.br(400), 0, 0.1).pan } +> \test;
//:
{ LFNoise1.kr(1/3).range(400, 4000) } @>.test \freq;
//:
{ SinOsc.kr(15).range(-100, 500) } @@>.freq1 [\freq, \test];
//:

*/

+ Function {
	@@> { | busenvir, player |
		var bus, envir;
		#bus, envir = busenvir;
		// postln("@@> bus" + bus + "envir" + envir + "player" + player);
		{
			Out.kr(bus.bus(nil, envir), this.value.kdsr);
		}.playInEnvir(player, envir)
	}
}