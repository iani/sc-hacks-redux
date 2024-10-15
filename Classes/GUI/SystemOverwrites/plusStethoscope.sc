/* 11 Nov 2022 14:56
Extend Stethoscope to focus on busses chosen from user.
*/

+ Stethoscope {
	init { // do nothing.
		// enables use of function "fromLib"
	}
	setIndex { | n = 0 |
		setIndex.(n);
		idxNumBox.value = n;
	}

	setNumChannels { | n = 1 |
		setNumChannels.(n);
		chNumBox.value = n;
	}

	setRate { | argRate = \control |
		if (argRate === \control) {
			setRate.(1);
			rateMenu.value = 1;
		}{
			setRate.(0);
			rateMenu.value = 0;
		}
	}

	setName { | name = \x1 |
		window.name = name
	}

	*fromLib { | key ... args |
		// new first argument must be a server
		var new;
		new = this.atLibKey(key);
		new ?? {
			new = this.new(Server.default).init(key, *args);
			Library.global.put(this, key, new);
			this.changed(\fromLib, key, *args);
		};
		^new;
	}

	stop {
		if( view.notNil ) { {scopeView.stop}.defer };
		synth.stop;
		// notify to remove instance from library
		// see bus.scope
		this.changed(\stopped);
	}

	yZoom_ { | n |
		setYZoom.(n)
	}
}