/* 20 Jul 2023 00:23
Shortcut for \pos.br mapped with mapdur:
mapdur { | buf | // map from 0-1 to scale matching duration of buffer selection
		^this.linlin(0, 1,
				\startframe.br(~startframe ? 0) / buf.sampleRate,
				\endframe.br(~endframe ?? { buf.numFrames }) / buf.sampleRate)
	}

*/

Pos {
	*new { | buf |
		^\pos.br(~pos ? 0).mapdur(buf);
	}
 }