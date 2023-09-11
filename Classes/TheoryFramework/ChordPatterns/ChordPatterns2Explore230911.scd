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