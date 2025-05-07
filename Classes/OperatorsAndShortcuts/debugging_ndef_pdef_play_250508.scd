
\x.play;
\x.ndef(\default);
\x.ndef({ WhiteNoise.ar(0.1) });
\x.ndef({ SinOsc.ar(400, 0, 0.2) });
\x.ndef({ SinOsc.ar(400, 0, 0.2) });
\x.ndef({ SinOsc.ar(\freq.kr(400).lag(2), 0, 0.2) });
\x.ndef({ SinOsc.ar(\freq.kr(400).lag(2), 0, 0.2) }, [freq: 50.rrand(500)]);
\x.ndef({ WhiteNoise.ar(0.2) });
\x.ndef({ WhiteNoise.ar(0.2) });
\x.play;
\x.stop;
// The above are ok.
// now these:
~y;
~y.isPlaying;
\y.play;
currentEnvironment[\y];
currentEnvironment[\y].isPlaying;
\y.play;
\y.play({ WhiteNoise.ar(0.1) });
\y.play({ SinOsc.ar(400, 0, 0.2) });
\y.play({ SinOsc.ar(400, 0, 0.2) });
\y.play({ SinOsc.ar(\freq.kr(400).lag(2), 0, 0.2) });
\y.play({ SinOsc.ar(\freq.kr(400).lag(2), 0, 0.2) }, [freq: 50.rrand(500)]);
\y.play(\default);
\y.play({ WhiteNoise.ar(0.2) });
\y.play({ WhiteNoise.ar(0.2) });
~y;

\y.ndef({ WhiteNoise.ar(0.2) });
//: ========= pdef, EventStream, PatternTemplate

\y.pdef;
\y.play;
\y play: (dur: 0.1);


\z.pdef;
\z.play;
\z play: (dur: 0.1);
\z.play({ WhiteNoise.ar(0.1) });
\z.pdef()
\z.clear;
~z.source.stream[\dur] = nil;
