/* 30 Nov 2022 08:56
Poll a bus at regular intervals, using bus.get and returns
the last value obtained, when used in an EventStream
*/

Pb { // NOTE: Pbus is taken
	classvar all, <pollRoutine, <>pollRate = 8;
	var <name, <player, <bus, <value = 0;

	*initClass {
		ServerTree add: this;
	}

	*doOnServerTree { // on server boot, or after CmdPeriod ...
		pollRoutine = {
			loop {
				// "polling Pb".postln;
				all do: _.poll;
				pollRate.reciprocal.wait;
			}
		}.fork;
	}

	all { ^this.class.all }
	*all {
		all ?? { all = Set() };
		^all;
	}

	*new { | name = \bus, player = \pb |
		// only one instance per bus and player
		^Registry(this, name, player, {
			this.newCopyArgs(name, player).init;
		});
	}

	init {
		bus = name.bus(nil, player);
		this.all add: this;
	}

	poll {
		bus.get({ | val | value = val; });
	}
	asStream {} // returns self

	next { ^value }
}
