//: 21 Aug 2023 17:36 From rehearsal of August 21 2023
// Use this as template for creating scores with absolute timestamps.
// Crossing sines. Glissandoes starts at 80 seconds
//:--[0]
//:source jun 1
{
	SinOsc.ar(\freq.br(200)).dup.fader(amp: 0.2) * (\x1.sr > 0.5).lag(1);
} +> \jun;
//source asayo 1
{
	SinOsc.ar(\freq.br(200 * 8)).dup.fader(amp:0.2) * (\z4.sr > 0.5).lag(1);
} +> \asayo;
//:--[80]
//:glissando jun
{ XLine.kr(200, 200 * 8, 180) } @>.jun \freq;
{ XLine.kr(200 * 8, 200, 180) } @>.asayo \freq;

//the end
