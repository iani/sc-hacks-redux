/* 11 Sep 2023 11:34
From https://doc.sccode.org/Classes/Place.html

Add more stuff below, with other pattern examples.

Substitute chord specs instead of midi numbers, and use chordcps to convert these to
freqs.

*/
(
c = Place(#[-20, [0, 6, 17], [1, 4, 28], [2, 3, 39]], inf) + 67;
x = c.asStream;
Routine({
    loop({
        Synth("help-sinegrain", [\freq, x.next.midicps]);
        0.17.wait;
    })
}).play;
)
//:============ Some more tries Tue 12 Sep 2023 11:17

//:
(freq: ["a", "b:dg", "G:d:f1"].pseq.chordcps, dur: 0.5) +> \x;
//:
(freq: ["a", "b:dg", "G:d:f1"].prand.stutter(5).chordcps, dur: 0.05) ++> \x;
//:
(dur: Pwhite(0.1, 0.5)) ++> \x;
//:
(freq: ["C1:C:c:bf", "b:d:g", "G:d:f1"].prand.stutter(5).chordcps) ++> \x;
//:
(freq: ["C1:C:c:bf", "C1:C:c:b"].prand.stutter(5).chordcps) ++> \x;
//:
(freq: ["C1:C:c:bf", "C1:C:c:b", "C1s:C:cs:b"].prand.stutter(5).chordcps) ++> \x;
//:
(freq: ["C1:C:c:bf", "C1s:C:c:bf", "C1ss:C:c:bf", "C1sss:C:c:bf"]
.pseq.stutter(5).chordcps, dur: 1, amp: 0.5) ++> \x;

//:
(freq: ["C1", "C1s", "C1ss", "C1sss"]
.pseq.stutter(5).chordcps, dur: 0.2, amp: 0.5) ++> \x;
//:
(freq: ["C1:c", "C1s:c", "C1ss:c", "C1sss:c"]
.pseq.stutter(5).chordcps, dur: 0.2, amp: 0.5) ++> \x;
//:
(dur: [Pn(0.05, 4), Pn(0.1, 4), Pn(0.2, 2), 0.4].pseq) ++> \x;
//:
(freq: ["C1:c", "C1s:cf", "C1ss:cff", "C1sss:cff"].pseq.stutter(5).chordcps) ++> \x;
//:
(freq: ["C1:G:c", "C1s:G:cf", "C1ss:G:cff", "C1sss:G:cff"]
.pseq.stutter(5).chordcps) ++> \x;
