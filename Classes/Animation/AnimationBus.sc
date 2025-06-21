// Specialized bus allocator for control buses used by AnimationPlayer
// Since these have many channels, it is preferabe to allocate
// new ones only by special request.

AnimationBus {
	classvar all;

	*initClass {
		ServerBoot add: { all = MultiLevelIdentityDictionary(); }
	}

	*new { | player, role, numChannels |
		var bus;
		bus = all.at(player, role, numChannels);
		bus ?? {
			bus = Bus.control(Server.default, numChannels);
			all.put(player, role, numChannels, bus);
		};
		^bus;
	}
}