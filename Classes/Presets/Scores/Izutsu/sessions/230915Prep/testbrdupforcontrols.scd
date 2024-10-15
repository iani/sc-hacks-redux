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
{ LFNoise0.kr(20).range(500, 800) } @>.test \freq0;
{ LFNoise0.kr(2).range(400, 500) } @>.test \freq1;
{ LFNoise0.kr(3).range(300, 400) } @>.test \freq2;
{ LFNoise0.kr(1).range(200, 300) } @>.test \freq3;