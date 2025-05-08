\x.ndef({ | amp = 0.1 | WhiteNoise.ar(amp)}, outbus: 1);
\x.play;
\x.set(\out, 0);
\x.set(\out, 1);
\x.set(\amp, 0.1);

~x.argDict;

\x.set(\out, 0);
\x.ndef({ | amp = 0.1 | WhiteNoise.ar(amp)}, outbus: 0);

~x.outbus;
~x.argDict;
//:
\x.play;
\x.ndef(\default);
\x.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [freq: 500]);
\x.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [freq: 500.rrand(1000)]);
\x.ndef(\default);
\x.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp });
\x.ndef({ WhiteNoise.ar(0.1) });
\x.ndef({ WhiteNoise.ar(0.1) }, [freq: 5000]);
\x.ndef({ WhiteNoise.ar(0.1) }, [freq: 50]);
\x.ndef({ WhiteNoise.ar(0.1) }, [freq: 50.rrand(100).midicps]);
\x.ndef({ WhiteNoise.ar(0.1) }, outbus: 1);
\x.ndef({ WhiteNoise.ar(0.1) }, outbus: 0);
//:
\x.ndef({ | amp = 0.1 | WhiteNoise.ar(amp)}, outbus: 1);
\x.ndef({ | amp = 0.1 | WhiteNoise.ar(amp)}, outbus: 0);
\x.ndef({ | amp = 0.1 | WhiteNoise.ar(amp)}, [amp: 0.01]);
\x.ndef({ | amp = 0.1 | SinOsc.ar(300, 0, amp)}, [amp: 0.01]);
\x.ndef({ | amp = 0.1 | PinkNoise.ar(amp)}, outbus: 1);
\x.ndef({ | amp = 0.1 | PinkNoise.ar(amp)}, [amp: 0.1]);
\x.ndef({ | amp = 0.1 | PinkNoise.ar(amp)}, outbus: 0);
//: ====== testing merge args ======
\y.play;
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [freq: 500]);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [freq: 59 rrand: 99]);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [amp: 0.3]);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp });
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [freq: 200]);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [amp: 0.01]);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, outbus: 1);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, outbus: 0);
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) });
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, outbus: 1);
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, outbus: 0);
//:

\y.play;

\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) });
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, [amp: 0.01]);
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, outbus: 1);
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, outbus: 1);
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, outbus: 0);
//:
//: set not yet implemented 木  8  5 2025 12:15
\x.set(\out, 0);
//
\x.ndef({ | amp = 0.1 | PinkNoise.ar(amp)}, outbus: 1);
\x.ndef({ | amp = 0.1 | PinkNoise.ar(amp)}, outbus: 0);
//: ====== testing merge args ======
\y.play;
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [freq: 500]);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [freq: 59 rrand: 99]);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [amp: 0.3]);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp });
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [freq: 200]);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, [amp: 0.01]);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, outbus: 1);
\y.ndef({ | freq = 400, amp = 0.1 | SinOsc.ar(freq) * amp }, outbus: 0);
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) });
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, outbus: 1);
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, outbus: 0);
//:

\y.play;

\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) });
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, [amp: 0.01]);
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, outbus: 1);
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, outbus: 1);
\y.ndef({ | amp = 0.1 | WhiteNoise.ar(amp) }, outbus: 0);
//:
//: set not yet implemented 木  8  5 2025 12:15
\x.set(\out, 0);
//
