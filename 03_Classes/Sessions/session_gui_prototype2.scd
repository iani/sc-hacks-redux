//Mon  5 Feb 2024 15:56

//:
var llv, blv;
llv ={ | label = "default", items |
	VLayout(
			StaticText().string_(label),
			ListView().items_(items)
		)
};
blv ={ | label = "default", items |
	VLayout(
			Button().states_([[label]]),
			ListView().items_(items)
		)
};
\sessions.bl_(800, 400).vlayout(
	HLayout(
		[blv.("+ Bookmark", ["audiofiles", "synthdefs", "scores"]), stretch: 1],
		[llv.("Outer List", ["water", "othonoi", "sumidagawa"]), stretch: 2],
		[llv.("Inner List"), stretch: 2]
	),
		HLayout(
		StaticText().string_("Menu"),
		TextField()
	),
	HLayout(
		blv.("+ Session"),
		blv.("+>Config"),
		blv.("+>Server"),
		blv.("+>Runtime"),
	)
);

//: