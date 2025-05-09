//金  9  5 2025 08:12 Test modifying stream of
//rokoko data received over oscgroups, and sending
//back the modified data to oscgroups.
//
//: Load data and store in ~rokoko
~rokoko = \session1.rdata; // load some data in advance
//: Start modifying data and sending
~rokoko.pollAddSumSend;
//: Write