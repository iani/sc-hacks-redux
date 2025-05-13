// 火 13  5 2025 21:17
//:
Tsr doOnType: { | n, char |
	postln("Received type. Args:" + char);
};
//:
Tsr.undoOnType;
//:
Tsr.type($a);