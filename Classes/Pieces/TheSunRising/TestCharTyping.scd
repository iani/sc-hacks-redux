// 火 13  5 2025 21:17
//:
Tsr doOnType: { | n, char |
	postln("Received type. Args:" + char);
};
//:
Tsr doOnVerse: { | n ... stuff |
	postln("Received verse. Args:" + stuff);
};
//:
Tsr.undoOnType;
//:
Tsr.type($a);
//:
$a.ascii;
//:


Tsr
OscMonitor.gui;

10000.rand;