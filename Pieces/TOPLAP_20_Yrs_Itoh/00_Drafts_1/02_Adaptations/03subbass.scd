//Sat 24 Feb 2024 20:39
//:
\subbass +> () +> \subbass;
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
(freq: (1..100).normalize(30, 300).pseq * [0.8, 1.2].pwhite) ++> \subbass;
(freq: (1..100).normalize(30, 300).reverse.pseq * [0.8, 1.2].pwhite) ++> \subbass;
//:
nil +> \subbass;
