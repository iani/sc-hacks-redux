// 日 27  4 2025 23:59
// Customizing ports for OSC send and receive.
// Open ports and configure User to use these.
// This is useful for testing User from 2 SuperCollider instances
// running on the same machine (one on EMACS and one on ScIDE).
//: open monitor to check reception of OSC.
OscMonitor.gui;
//: Open a custom UDP port for OSC reception
thisProcess openUDPPort: 57125;
//: Send a message to this port
NetAddr("127.0.0.1", 57125).sendMsg(\testCustomPort, 1, 2, 3);
//:
