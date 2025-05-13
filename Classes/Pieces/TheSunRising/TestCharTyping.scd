// 火 13  5 2025 21:17
//:
Tsr doOnType: { | n, char |
	postln("Received type. Args:" + char);
	Synth(\pinch);
};
//:
Tsr doOnVerse: { | n ... stuff |
	postln("Received verse. Args:" + stuff);
};
//:
Tsr.undoOnType;
//:
Tsr.type($a);
Tsr.type($b);
//:
$a.ascii;
//:


Tsr
OscMonitor.gui;


OSC.add(\char, { "char received".postln; });

10000.rand;

OscMonitor.gui;