// 金 16  5 2025 10:54
// Multichannel diffusion.
// Only for the one user diffusing the piece in a multichannel system.
// Plays diffusion multichannel functions sent after the target node
// Plays synths after the root node.
// Reads output from users from channels 0, 1
// Writes output of diffusion with ReplaceOut to channels 0-7.
// ReplaceOut.ar();

Diffuser {
	classvar <mainsynth;
	classvar <posb, <widthb, <levelb, <diffb; // controlbuses
	classvar <psynth, <wsynth, <lsynth, <dsynth; // synths writing to buses above

	*new { | name |
		^LocalUserAction(name, this);
	}

	*start { //
		this.startMainSynth;
		posb = Bus.control;
		widthb = Bus.control;
		levelb = Bus.control;
		diffb = Bus.control;
		{ this.reset }.defer(0.1);
	}

	*stop { mainsynth.free }

	*resetBuses {
		posb.set(0);
		levelb.set(1);
		widthb.set(2);
		diffb.set(0.125);
	}

	*startMainSynth {
		this play: { | pos = 0, level = 1, width = 2, posdif = 0.125 |
			var left, right;
			left = PanAz.ar(8, In.ar(0), pos, level, width);
			right = PanAz.ar(8, In.ar(1), pos + posdif, level, width);
			ReplaceOut.ar(0, left + right)
		}
	}
	*startMainSynthBuggy {
		mainsynth = {  | pos = 0, level = 1, width = 2 |
			var src;
			src = In.ar(0, 2);
			ReplaceOut.ar(0, PanAz.ar(8, src, pos, level, width));
		}.play(addAction: \addAfter )
	}

	*play { | func |
		mainsynth.free;
		mainsynth = func.play(addAction: \addAfter);
	}

	*pos_ { | pos = 0 | mainsynth.set(\pos, pos) }
	*level_ { | level = 1 | mainsynth.set(\level, level) }
	*width_ { | width = 2 | mainsynth.set(\width, width) }

	*p_ { | func |
		psynth.isPlaying.if { psynth.free };
		psynth = func.play(args: [\out, posb.index]);
		mainsynth.map(\pos, posb.index);
	}

	*w_ { | func |
		wsynth.isPlaying.if { wsynth.free };
		wsynth = func.play(args: [\out, widthb.index]);
		mainsynth.map(\width, widthb.index);
	}

	*l_ { | func |
		lsynth.isPlaying.if { lsynth.free };
		lsynth = func.play(args: [\out, levelb.index]);
		mainsynth.map(\level, levelb.index);
	}

	*d_ { | func |
		dsynth.isPlaying.if { lsynth.free };
		dsynth = func.play(args: [\out, diffb.index]);
		mainsynth.map(\posdif, diffb.index);
	}

	*scope {
		Server.default.scope(numChannels: 8)
	}

	*tree { Server.default.synthTree }
}