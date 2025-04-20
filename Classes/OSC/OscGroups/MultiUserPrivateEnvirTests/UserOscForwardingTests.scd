
//: Recompile first!
123.rand; // no forwarding happens because OscGroups is disabled by default
//:
OscGroups.enable;
//:Osc forwarding happens now.
123.rand; //
