/* 27 Feb 2022 09:49

*/

+ Event {

	addEvent { | argEvent | // add all key-value pairs from argEvent to myself
		argEvent keysValuesDo: { | key, value | this[key] = value }
	}

	pp { // prettyprint
		^String.streamContents({ arg s;
			s << "(\n";
			this.keys.asArray.sort do: { | k |
				s << "'" << k << "': ";
				s << this[k].asCompileString;
				s << ",\n"
			};
			s << ")";
		})
	}

	splay { | filter | ^EventStream(this, filter).start; }

	getParent { if (this.parent.isNil) { ^defaultParentEvent } { ^this.parent }  }

	// see OperatorFix240222.sc
	// +> { | player, envir | ^this.pushPlayInEnvir(player, envir ? player, true) }

	playInEnvir { | player, envir, start = true |
		var atKey, new;
		Mediator.pushWrap({
			atKey = currentEnvironment[player];
			atKey.stop;
			new = EventStream(this);
			if (start) { new.start };
			currentEnvironment[player] = new;
		}, envir ? player);
		^new;
	}

	playbuf {
		^this filter: {
			var buf;
			buf = ~buf.next;
			~instrument = [nil, \playbuf1, \playbuf2][buf.numChannels];
			~bufnum = buf.bufnum;
		}
	}
	filter { | function |
		^EventStream(this, function);
	}
}
