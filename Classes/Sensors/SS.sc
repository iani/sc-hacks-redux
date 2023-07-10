/*  9 Jul 2023 11:03
Create and feed the threshold tracking busses for input from 12 SenseStage sensors,
as described in file GlobalSensorEnvir.org.
*/

SS {
	classvar <numSensors = 12;
	classvar <envirName = \ss;

	*init { | argNumSensors = 12 |
		numSensors = argNumSensors;

	}

	*makeAllAmpSlopeXyz {
		(1..numSensors) do: this.makeAmpSlopeXyz(_);
	}

	*makeAmpSlopeXyz { | id = 1, lag = 0.15, thresh = 1.5 |
		Xyz(id, lag).perform('@>', envirName)
	}

	*makeGtLt {

	}


	xyzName { | snum = 1 |
		^("xyz" ++ snum).asSymbol
	}
}