// 火 13  5 2025 21:17
//:
Tsr.doOnType({ | char |
	postln("Received type. Args:" + char);
	// Synth(\pinch);
}, \iani1);
//:
Tsr.undoOnType;
//:
Tsr doOnVerse: { | n, stuff  |
	postln("Received verse. n" + n + "Args:" + stuff);
};
//:
Tsr.verse(0);
//:
Tsr.undoOnType;
//:
Tsr.type($c);
Tsr.type($b);
//:
$a.ascii;
//:


Tsr
OscMonitor.gui;


OSC.add(\char, { "char received".postln; });

10000.rand;

OscMonitor.gui;