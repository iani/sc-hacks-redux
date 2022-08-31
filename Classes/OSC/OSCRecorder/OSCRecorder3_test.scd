OSCRecorder3.maxItems = 100;
OSCRecorder3.fileHeader = "datatest";
OSCRecorder3.subFolder = "recordingtests5";
OSCRecorder3.enable;
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
//:
OSCRecorder3.closeFile;