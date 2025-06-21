// 土 21  6 2025 17:45
// Test multichannel conrol bus addition
//:
var databus, modbus, datasynth, modsynth, soundsynth;
databus = Bus.control(Server.default, 2);
modbus = Bus.control(Server.default, 2);
datasynth = {
	var freqsrc, ampsrc, datasrc, modsrc;
	freqsrc = LFNoise1.kr(1).range(400, 4000);
	ampsrc = SinOsc.kr(7).range(0.01, 0.1);
	modsrc = In.kr(modbus.index, modbus.numChannels);
	datasrc = [freqsrc, ampsrc];
	Out.kr(databus.index, datasrc + modsrc);
}.play;
{ SinOsc.ar(In.kr(databus.index, 1), 0, In.kr(databus.index + 1, 1)) }.play;
modsynth = {
	Out.kr(modbus.index, [
		LFPulse.kr(10).range(0, 150),
		LFPulse.kr(1).range(0, 0.2);
	]);
}.play;

//:

{

}.play;
