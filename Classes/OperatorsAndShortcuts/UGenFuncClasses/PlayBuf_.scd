//:dur -- not settable while running.
//:rate
0.1 @> \rate;
{ LFNoise0.kr(10).range(0.4, 3) } @> \rate;
//:trigger
{ Impulse.kr(2) } @> \trigger
//:startpos
0.85 @> \startpos
//:loop
1 @> \loop
//: