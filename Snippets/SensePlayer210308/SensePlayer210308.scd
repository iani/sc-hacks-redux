//:  8 Mar 2021 06:28
/* read a file containing recorded osc data from sensor system.
Play back the data.
*/
//:Select file using dialog window
SensePlayer.readDialog;
//:Set the address for sending to local + your port of choice
SensePlayer.setLocalPort(12345);
//:
SensePlayer.play;