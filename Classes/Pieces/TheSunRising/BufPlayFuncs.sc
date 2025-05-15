// 水 14  5 2025 17:48
// Collection of Functions for playing Tsr voice buffers

TsrBuf {
	*bufCheck { // only perform a method if the buffer is not nil
		^BufCheck(this)
	}
	*play { | b |
		^b.play;
	}

	*loopb { | b |
		^{ | out = 0, rate = 1, amp = 1, pan = 0 |
			PlayBuf.ar(1, b,
				BufRateScale.kr(b) * rate,
				\t_trig.kr(0),
				\startPos.kr(0),
				1, 2
			) * Env.fader * amp
		}.play;
	}
}

BufCheck {
	var <player;
	*new { | player | ^this.newCopyArgs(player) }
	doesNotUnderstand { | message, bufName ... args |
		var buf;
		buf = currentEnvironment[bufName];
		buf.isNil.if {
			postln("Did no find a buffer named" + bufName);
		}{
			^player.perform(message, buf, *args);
		}

	}
}