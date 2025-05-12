// 日 11  5 2025 12:21
// First tests with Actor2
// Using same session name as in previous days.
// Testing chaining of messages.
// Data are loaded asynchronously, only once, when first needed.
//:Test 1. Play entire data recording onced
\session1.rdata.playSegment;
\session1.rdata.verbose = true;
\session1.rdata.verbose = false;
\session1.rdata.stop;
//:Test 2. Loop entire data recording forever
\session1.rdata.loopSegment;
\session1.rdata.verbose = true;
\session1.rdata.verbose = false;
\session1.rdata.stop;
//:
\session1.rdata;

a = Actor2(\baubo, \r1)
a.envir[\leftLegy];

a.envir[\hipx];
a.envir[\hix];
a.envir[\hiy];
a.envir[\rteW];