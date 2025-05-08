// 木  8  5 2025 23:34 Adding data filters.
// Any number of filter functions can be added.
// functions are sorted in alphanumeric order of their names
~rokoko = \session1.rdata; // load some data in advance
~rokoko.addPostData; // post data to see how filter works
//: First test: post the data as played
~rokoko.playSegment(0, 10);
//: Add a filter
~rokoko.addFilter(\_01shortOsc, { | d | d[0] = '/r' });
~rokoko.playSegment(0, 10);
//: Add another Filter
~rokoko.addFilter(\_02truncate, { | d | d[..5] });
~rokoko.playSegment(0, 10);
//: And another Filter
~rokoko.addFilter(\_03hipxX100, { | d |
	d[4] = d[4] * 100
});
~rokoko.playSegment(0, 10);
//: