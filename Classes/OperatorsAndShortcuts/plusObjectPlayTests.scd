// 月  5  5 2025 13:00
\a.play(\default);
\a.play(\default, [freq: 600]);
\a play: ();
\a play: (degree: -10.rrand(10));
\a play: (dur: 0.2);
\a play: ();

\a.set(\freq, 200);
\a.set(\freq, 400);
\a.set(\freq, 600);

\a.set((freq: 300))
\a.set(\dur, 0.1);
\a.play;
\a.stop;
\a set: [freq: 150, dur: 2.reciprocal];
~a;

[freq: 1, amp: 0].asEvent;

\b set: (degree: (0.3)*3, dur: 0.1);

~c = ();

~d = ();
~e = ();


\f set: (degree: -10 rrand: 10, dur: 0.1);
~f;

\f.play;

\g set: (degree: -10 rrand: 10, dur: 0.1);
\g.play;

\h set: [degree: [-2, 20, 1].pbrown, dur: 0.1];
\h set: [degree: (1..4).pseq, dur: 0.1];
\h set: [degree: [1, 10, 2].pbrown, dur: 0.2];
\h set: [\degree, (10..-10).pseq, \dur, 0.05];
\h.play;
\h.set(\degree, [2, -1, 10].pbrown, \dur, 0.01 exprand: 0.7);
\h.set(\legato, 0.5);
\h.stop;
