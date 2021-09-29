/* 25 May 2021 13:13
First examples for testing with osc groups.
*/

() +> \test;
{ PinkNoise.ar(0.02).dup } +> \test;
(dur: 1/4) ++> \test;
(degree: 1) ++> \test;
(degree: Pwhite(-10, 10)) ++> \test;
(degree: Pwhite(-10, 10.0)) ++> \test;
(degree: Pbrown(-10, 10, 2)) ++> \test;
(dur: Prand([0.1 !! 10, 1/4 !! 4, 1/2, 1], inf)) ++> \test;
\test.stop;
\test.play;
{ PinkNoise.ar(0.02).dup } +> \test;
\test.stop;
// you cannot restart stopped synths.
(dur: Prand([0.1 !! 10, 1/4 !! 4, 1/2, 1], inf)) ++> \test;