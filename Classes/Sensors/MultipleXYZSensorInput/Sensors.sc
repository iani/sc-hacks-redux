/* 18 Nov 2022 15:28
Utility class
Creates some currently used configurations.
*/

Sensors {
	*enable {
		MapXyz('/minibee/data', '/minibee', 0.44, 0.56);
		MapXyz('/pinetime/data', '/pine', 0.44, 0.56);
		InputXyz.enable(24);
		InputXyz.addMessage('/minibee');
		InputXyz.addMessage('/pine');
	}

	*gui {
		InputXyz.gui;
	}
}
