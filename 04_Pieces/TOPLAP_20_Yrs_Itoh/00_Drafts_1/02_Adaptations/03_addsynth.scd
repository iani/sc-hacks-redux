//Sat 24 Feb 2024 20:39
//:
\addsynth +> () +> \addsynth;
//:
(freq: [30, 200].pwhite) ++> \addsynth;
(legato: 1.5) ++> \addsynth;
//:
(dur: 3) ++> \addsynth;
//:
(dur: 0.3) ++> \addsynth;
//:
(freq: (1..100).normalize(30, 300).pseq) ++> \addsynth;
(dur: 0.03) ++> \addsynth;
(freq: (1..100).normalize(30, 300).pseq * [0.8, 1.2].pwhite) ++> \addsynth;
(freq: (1..100).normalize(30, 300).reverse.pseq * [0.8, 1.2].pwhite) ++> \addsynth;

(mul: (1..10).normalize(0.5, 5).prand) ++> \addsynth;

(mul: 0.1) ++> \addsynth;
(mul: 0.1) ++> \addsynth2;

(legato: 0.1) ++> \addsynth;
(legato: 0.1) ++> \addsynth2;
//:
(legato: 0.01) ++> \addsynth;
(legato: 0.01) ++> \addsynth2;
//:
nil +> \addsynth;
//:
\addsynth +> () +> \addsynth2;
//:
(freq: [30, 200].pwhite) ++> \addsynth2;
(legato: 1.5) ++> \addsynth2;
//:
(dur: 3) ++> \addsynth2;
//:
(dur: 0.3) ++> \addsynth2;
//:
(freq: (1..100).normalize(30, 300).pseq) ++> \addsynth2;
(dur: 0.03) ++> \addsynth2;
(freq: (1..100).normalize(30, 300).pseq * [0.8, 1.2].pwhite) ++> \addsynth2;
(freq: (1..100).normalize(30, 300).reverse.pseq * [0.8, 1.2].pwhite) ++> \addsynth2;
(mul: (1..10).normalize(0.5, 5).prand) ++> \addsynth2;

//:
nil +> \addsynth2;
