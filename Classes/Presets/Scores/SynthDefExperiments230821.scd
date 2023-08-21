//: 21 Aug 2023 17:36 From rehearsal of August 21 2023
// Experimenting with synthdefs inline in score
//:--[1]
{ Decay2.kr(Dust.kr(1)) * 0.2 } @>.test \amp;
{ LFNoise2.kr(5).range(100, 10000) } @>.test \freq;
{
	SinOsc.ar(\freq.br(~freq ? 400)).dup* \amp.br(1)
} +> \test;
//:--[5]
{ Decay2.kr(Dust.kr(1)) * 0.2 } @>.test \amp;
{ LFNoise2.kr(0.5).range(100, 4000) } @>.test \freq;
{
	SinOsc.ar(\freq.br(~freq ? 400)).dup* \amp.br(1)
} +> \test;
{ Decay2.kr(Dust.kr(5), 0.01, 0.1) * 0.2 } @>.test2 \amp;
{ LFNoise2.kr(5).range(1000, 5000) } @>.test2 \freq;
{
	SinOsc.ar(\freq.br(~freq ? 400)).dup* \amp.br(1)
} +> \test2;
{ Decay2.kr(Dust.kr(1/5), 0.5, 2) * 0.2 } @>.test3 \amp;
{ LFNoise2.kr(0.5).range(50, 1000) } @>.test3 \freq;
{
	SinOsc.ar(\freq.br(~freq ? 400)).dup* \amp.br(1)
} +> \test3;
