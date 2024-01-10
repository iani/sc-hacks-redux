/* 11 May 2022 15:04
For data received from Vasilis Agiomyrgianakis RPI-zero based sensor system.
*/

RpiGlove {
	*connect {
		// create osc listeners for messages sent by Vasilis' RPI based glove system.

	}

	*addButtons { | ... actions |
		\filter >>> { | n, args |
			actions[args[1]].value;
		}
	}
}