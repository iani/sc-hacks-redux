/* 12 Mar 2022 08:29
Testing new operators for mapping and setting busses.
*/

{ SinOsc.ar(\freq.kr(440), 0, 0.1).dup } +> \test;

\freq <@.test \freqbus;

\freqbus <+@ 20.squared;