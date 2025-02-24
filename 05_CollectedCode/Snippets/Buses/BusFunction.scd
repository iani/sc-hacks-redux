/* 25 Sep 2022 15:53
*/

//:
{
	SinOsc.ar(\freq.bin(200), 0, 0.1).dup;
} +> \test1;
//: Start func writing to \freq
{ LFNoise0.kr(10.05).range(200, 1000) } @> \freq;
//: Start func writing to \freq, but store in player \freq2
{ LFNoise1.kr(5).range(0, 1000) } @>.freq2 \freq;
//: Stop funcion stored in \freq2
nil @>.freq2 \freq;
//: Stop function stored in \freq
nil @> \freq;
//:
{
	SinOsc.ar(\freq2.bin, 0, 0.1).dup;
} +> \test2;
//:
660 @> \freq;
//:
1660 @> \freq2;
//:
