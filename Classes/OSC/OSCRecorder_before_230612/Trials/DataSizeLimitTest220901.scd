//:
{
	var millions;
	~timeStarted = Main.elapsedTime;
	4 do: { | i |
		{  millions = millions ++ { Array.rand(10, 0, 10) }.dup(100000); }.dup(10);
		[i + 1, millions.size, millions.size / 1000000].postln;
		0.1.wait;
	};
	~timeEnded = Main.elapsedTime;
	[~timeStarted, ~timeEnded, ~timeEnded - ~timeStarted].postln;
}.fork;
//: result:
[ 1, 1000000, 1.0 ]
[ 2, 2000000, 2.0 ]
[ 3, 3000000, 3.0 ]
[ 4, 4000000, 4.0 ]
[ 351.925274083, 359.120283083, 7.195009 ]

//:
((10 ** 6).asInteger / 100) / 60;

//: 10hr 600 min 36000 sec  3.600.000 messages
(((10 * 60).postln * 60).postln * 100);