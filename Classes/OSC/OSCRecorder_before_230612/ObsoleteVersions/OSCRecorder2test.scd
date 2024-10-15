OSCRecorder2.maxItems = 100;
OSCRecorder2.fileHeader = "datatest";
OSCRecorder2.subFolder = "recordingtests4";
OSCRecorder2.enable;
//:
{
	var addr;
	addr = NetAddr.localAddr;
	150 do: { | i |
		0.1.wait;
		i.postln;
		addr.sendMsg(\test, *Array.rand(10, 0, 10));
	};
	"DONE".postln;
}.fork;