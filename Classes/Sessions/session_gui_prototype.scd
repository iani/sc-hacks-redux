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
		[llv.("Bookmarks", ["a", "beta", "c"]), stretch: 1],
		[llv.("Outer List", ["a", "beta", "c"]), stretch: 2],
		[llv.("Inner List"), stretch: 2]
	),
		HLayout(
		StaticText().string_("Menu"),
		TextField()
	),
	HLayout(
		llv.("Sessions"),
		blv.("Config"),
		blv.("Server"),
		blv.("Runtime"),
	)
);

//: