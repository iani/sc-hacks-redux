OSCRecorder3.maxItems = 100;
OSCRecorder3.fileHeader = "newformattest5withcode_B";
OSCRecorder3.subFolder = "newformattest5withode_B";
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
OSCRecorder3 jll.closeFile;
//:
OscGroups.enable;
//:
OscDataReader.openDialog;
//:
~x = ~oscdata.data;
~x.flop[0] do: _.postln;
~x.flop[1] do: _.postln;
~x.flop[1].first;
~oscdata;
//:
FileDialog({ | argPath |
	argPath.postln;
})
//:
Dialog.openPanel({ | argPath |
	argPath.postln;
});
//:
"This is a code thing executed from the user".postln;
2000.rand;
//:
Project.gui;