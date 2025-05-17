//金 16  5 2025 17:06
//Diffusion of the performance to 8 channels for today at the Ionian Academy
\a play: { PinkNoise.ar(0.1) };

z = Diffuser(\iani);
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};
//;
z.start;
z.stop;
//:
z.scope;
//:
z.p = { | out = 1 | Out.kr(out, LFSaw.kr(5.reciprocal)) };
//:
z.pos = 0;
z.pos = 0.1;
//:
z.mainsynth.set(\width, 1.5);
z.mainsynth.set(\pos, 0.3);
z.mainsynth.set(\pos, 0.5);
z.mainsynth.set(\level, 0.5);
