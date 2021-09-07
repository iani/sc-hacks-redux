/*  8 Sep 2021 05:40

*/
IdeFantasy {
	// computers sending sensor data to OSCGroups:
	classvar <nodes = #[\corfu, \sapporo];
	classvar <localNode = \corfu;

	*gui {
		this.tr_.v(
			StaticText().string_("Select the name of your node:")
			.font_(Font("Helvetica", 24)),
			ListView()
			.font_(Font("Helvetica", 24))
			.items_(nodes)
			.action_({ | me |
				localNode = me.items[me.value]
			})
			.valueAction_(0)
		);
	}
}