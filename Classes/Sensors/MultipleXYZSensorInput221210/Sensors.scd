/* 18 Nov 2022 15:28
!!!!!!!!!!!
30 Jan 2023 05:29 ---- replaced by newer version
!!!!!!!!!!!

Utility class
Creates some currently used configurations.

Configurable through these classvars:
	maps
	numSensorIds


*/

Sensors {
	classvar <>maps = #[
		['/minibee/data', '/minibee', 0.44, 0.56],
		['/pinetime/data', '/pine', 0.44, 0.56]
	];
	classvar <>numSensorIds = 72;

	*addSensor { | ... specs | maps = maps add: specs; }

	*enable {
		maps do: { | map |
			MapXyz(*map);
			InputXyz.addMessage(map[1]);
		};
		InputXyz.enable(numSensorIds);
		OscGroups.enable;
	}

	*enable_OLD_TEST {
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
