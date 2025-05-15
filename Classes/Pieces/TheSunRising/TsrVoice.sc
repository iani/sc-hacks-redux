// 水 14  5 2025 16:54
// Hold all buffers with the verse samples,
// Access these by shortcut or by number.

TsrVoice {
	classvar all; // all 4 possible instances
	classvar incipits, verses;
	classvar synths;
	var <index = 0, <bufArray, <bufDict;

	incipits { ^this.class.incipits }
	*incipits { ^incipits ?? { incipits = ~incipits } }
	verses { ^this.class.verses }
	*verses { ^verses ?? { verses = ~verses } }

	*synths { ^synths ?? { synths = IdentityDictionary() } }

	*at { |index=0|

	}

	all { ^this.class.all }
	*all {
		^all ?? {
			all = [~verses1, ~verses2, ~verses3, ~verses4] collect: { | bufs, i |
				this.new(i, bufs).init
			}
		}
	}

	*new { | index, bufArray | ^this.newCopyArgs(index, bufArray).init; }

	init {
		bufDict = IdentityDictionary();
		incipits do: { | i, j |
			bufDict[i] = bufArray[j]
		}
	}

	*play { | bufname = \i1 |
		var synth;
		synth = this.synths[bufname];
		synth ?? { synth.release };
		synth = currentEnvironment[bufname].play;
		synths[bufname] = synth;
	}

}