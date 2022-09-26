/* 26 Sep 2022 07:11

*/
//:
{ SinOsc.ar(\freq.bin, 0, 0.1).dup } +> \x;
//:------------------------------------------------------------
(dur: 0.12, freq: (40..80).midicps.pseq) @>.test \freq;
//:To start quantised, set quant in event:
(quant: 2, dur: 0.2, freq: ((80..85).midicps ++ [\_, \_] ++ (60..75).midicps.reverse ++ ({ \_ } ! 8)).pseq) @>.test \freq;
// To strart immediately, set quant to nil, or do not set quant:
(quant: nil, dur: 0.2, freq: ((80..85).midicps ++ [\_, \_] ++ (60..75).midicps.reverse ++ ({ \_ } ! 8)).pseq) @>.test \freq;
// This is equivalent to quant: nil :
(dur: 0.2, freq: ((80..85).midicps ++ [\_, \_] ++ (60..75).midicps.reverse ++ ({ \_ } ! 8)).pseq) @>.test \freq;
//:------------------------------------------------------------
(dur: 0.15, degree: (0..15).pseq) @> \freq;
//:
nil @>.test \freq;
//:
nil @> \freq;
//:
