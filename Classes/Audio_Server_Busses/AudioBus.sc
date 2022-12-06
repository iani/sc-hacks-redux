/*  5 Dec 2022 01:07
A central place for getting AudioBusses by name.
Since Audio Busses are freed by CmdPeriod, AudioBus
recreates these at ServerTree.

Environments that use these obtain them from AudioBus.

*/

AudioBus {
	classvar all, specs;
	// var bus, <numChannels = 1; // we don't use instances of AudioBus

	*initClass { ServerTree add: this;}
	*doOnServerTree {
		all = ();
		this.specs keysValuesDo: { | name, numChannels |
			all[name] = this.make(numChannels)
		};
	}

	*make { | numChannels = 2 |
		^Bus.audio(Server.default, numChannels);
	}

	*store { | name, bus | this.all[name] = bus; ^bus; }

	// all { ^this.class.all } // we don't use instances of AudioBus
	*all { ^all ?? { all = () }; }
	// specs { ^this.class.specs } // we don't use instances of AudioBus
	*specs { ^specs ?? { specs = () };}

	*addSpec { | name, numChannels = 2 |
		this.specs[name] = numChannels;
	}

	*new { | name, numChannels = 2 |
		var new;
		if ([\out, \outbus] includes: \out) {
			new = Bus(\audio, 0, numChannels, Server.default);
		}{
			new = this.all[name];
		};
		if (new.notNil) {
			^new
		}{
			this.addSpec(name, numChannels);
			^this.store(name, this.make(numChannels));
		}
	}

	*free { | name |
		this.all[name].free;
		all[name] = nil;
		specs[name] = nil;
	}
}
