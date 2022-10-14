/* 14 Oct 2022 15:50

EventStream history cannot be recorded.
Synth history is recorded.

*/

SynthHistory : MultiLevelIdentityDictionary {
	classvar default;

	*initClass {
		StartUp add: { this.enable; }
	}

	*enable {
		this.addNotifier(Function, \player, { | n, event, player, time, code |
			this.add(event, player, time, code);
		});
	}

	*disable {
		this.removeNotifier(Function, \player);
	}
	*default {
		default ?? { default = this.new; };
		^default;
	}

	*add { | event, player, time, code |
		var all, thisOne;
		all = this.default;
		thisOne = all.at(event, player);
		thisOne = thisOne add: [time, code];
		all.put(event, player, thisOne);
	}

	*at { | event, player |
		^this.default.at(event, player);
	}
}