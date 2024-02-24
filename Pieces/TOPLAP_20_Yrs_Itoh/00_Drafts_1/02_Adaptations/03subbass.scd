//Sat 24 Feb 2024 20:39
//:
\subbass +> () +> \subbass1;
//:
(freq: [30, 200].pwhite) ++> \subbass;
(legato: 1.5) ++> \subbass;
//:
(dur: 3) ++> \subbass;
//:
(dur: 0.3) ++> \subbass;
//:
(freq: (1..100).normalize(30, 300).pseq) ++> \subbass;
(dur: 0.03) ++> \subbass;
(freq: (1..100).normalize(30, 300).prand * [0.8, 1.2].pwhite) ++> \subbass;
(freq: (1..100).normalize(30, 300).reverse.pseq * [0.8, 1.2].pwhite) ++> \subbass;
//:
nil +> \subbass1;

(mul: [0.2, 0.7].pwhite / 300) ++> \subbass;

ubbass +> () +> \subbass;