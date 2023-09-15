/* 15 Sep 2023 10:19
Testing brdup
*/

//:
{
	var freqs;
	// freqs = ((5..8) * 100).brdup(\freq);
	freqs = ((5..8) * 100) @@> \freq;
	DynKlang.ar(`[freqs, 0.01])
} +> \test;
//:
3000 @>.test \freq0;
//:
{ LFNoise0.kr(2).range(500, 800) } @>.test \freq2;