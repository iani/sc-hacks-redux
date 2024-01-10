/* 13 Sep 2023 09:50
Examples of filter in an EventStream.

Unclear: the filter function does not have access to the values produced by the
play function. What uses can it have?

*/

// Debug this! should not replace the original stream in ~degree here
// event:                         // filter:
(degree: (1..10).pseq).splay({ ~degree = ~degree * 2 });
//:better example - not replacing any stream:
// event:                         // filter:
(degree: (1..10).pseq).splay({
~freq.postln; // ~freq is nil because the event has not played yet.
});
