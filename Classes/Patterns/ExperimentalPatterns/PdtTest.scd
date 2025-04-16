/* 29 Mar 2022 20:40
Testing Pdt
*/

a.inspect;
a = Pdt((1..3)).asStream;
a.next;

10.do({ a.next.postln });