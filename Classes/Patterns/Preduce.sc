//: 21 Jan 2021 07:12
/*
Play less elements from a pattern at each repeat.

(Note: Compare code of patterns Pdrop, Pgate to see some related techniques.)

For example: 

Preduce(Pseries(1, 1, 5)).asStream.all;

should produce: 

[1, 2, 3, 4, 5, 1, 2, 3, 4, 1, 2, 3, 1, 2, 1]
*/