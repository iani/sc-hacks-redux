// load session data
~rokoko = \session1.rdata; // load some data in advance
//: Start resending received data to OSC Groups
~rokoko.resendRokokoOsc;
//: stop resending OSC data
~rokoko.muteRokokoOsc;
//: OSC Monitor
OscMonitor.gui;
