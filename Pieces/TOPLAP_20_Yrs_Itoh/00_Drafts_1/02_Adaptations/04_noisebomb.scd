//Sat 24 Feb 2024 20:39
//:
\noisebomb +> () +> \noisebomb;
//:
(freq: [30, 200].pwhite) ++> \noisebomb;
(legato: 1.5) ++> \noisebomb;
//:
(dur: 3) ++> \noisebomb;
//:
(dur: 0.3) ++> \noisebomb;
//:
(freq: (1..100).normalize(30, 300).pseq) ++> \noisebomb;
(dur: 0.03) ++> \noisebomb;
(freq: (1..100).normalize(30, 300).pseq * [0.8, 1.2].pwhite) ++> \noisebomb;
(freq: (1..100).normalize(30, 300).reverse.pseq * [0.8, 1.2].pwhite) ++> \noisebomb;

(mul: (1..10).normalize(0.5, 5).prand) ++> \noisebomb;

(legato: 0.1) ++> \noisebomb;
(legato: 10.1) ++> \noisebomb;
//:
nil +> \noisebomb;
//:
\noisebomb +> () +> \noisebomb2;
//:
(freq: [30, 200].pwhite) ++> \noisebomb2;
(legato: 1.5) ++> \noisebomb2;
//:
(dur: 3) ++> \noisebomb2;
//:
(dur: 0.3) ++> \noisebomb2;
//:
(freq: (1..100).normalize(30, 300).pseq) ++> \noisebomb2;
(dur: 0.03) ++> \noisebomb2;
(freq: (1..100).normalize(30, 300).pseq * [0.8, 1.2].pwhite) ++> \noisebomb2;
(freq: (1..100).normalize(30, 300).reverse.pseq * [0.8, 1.2].pwhite) ++> \noisebomb2;
//:
nil +> \noisebomb;
