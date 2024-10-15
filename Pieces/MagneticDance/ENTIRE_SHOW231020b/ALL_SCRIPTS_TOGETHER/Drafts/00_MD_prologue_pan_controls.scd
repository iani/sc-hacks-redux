/* 21 Oct 2023 07:43
We do not use these because yoshiya only has stereo.
*/
// controls for pan
// justine pan control
{ \x5.sr.lag(0.5) } @>.prologuejustineL \lpos;
{ \x6.sr.lag(0.5) } @>.prologuejustineR \lpos;
{ \z5.sr.lag(0.5) } @>.prologuejustineL \rpos;
{ \z6.sr.lag(0.5) } @>.prologuejustineR \rpos;
// yoshimitsu pan control
{ \x1.sr.lag(0.5) } @>.prologueyoshiyaL \lpos;
{ \x2.sr.lag(0.5) } @>.prologueyoshiyaR \lpos;
{ \z1.sr.lag(0.5) } @>.prologueyoshiyaL \rpos;
{ \z2.sr.lag(0.5) } @>.prologueyoshiyaR \rpos;