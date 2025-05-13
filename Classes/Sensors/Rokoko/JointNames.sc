// 火 13  5 2025 12:22
// Store all variable names arrays, in 4 formats:
// 1. As sent by rokoko OSC.
// 2. As sent by rokoko OSC, with shortened names
// 3. Corresponding to the control bus channel array

JointNames {
	classvar <fullNames = #[
		'hip', 'spine', 'chest', 'neck', 'head',
		'leftShoulder', 'leftUpperArm', 'leftLowerArm', 'leftHand',
		'rightShoulder', 'rightUpperArm', 'rightLowerArm', 'rightHand',
		'leftUpLeg', 'leftLeg', 'leftFoot', 'leftToe', 'leftToeEnd',
		'rightUpLeg', 'rightLeg', 'rightFoot', 'rightToe', 'rightToeEnd'
	];

	classvar <shortNames = #[
		'hi', 'sp', 'ch', 'ne', 'he',
		'ls', 'lu', 'lo', 'lh',
		'rs', 'ru', 'ro', 'rh',
		'lul', 'll', 'lf', 'lt', 'lte',
		'rul', 'rl', 'rf', 'rt', 'rte'
	];

	classvar <varNames = #[\x, \y, \z, 'X', 'Y', 'Z', 'W'];
	classvar oscNames; // translate from name to index in data received by osc
	classvar busNames; // translate from name to index in data received from bus array polling;
	classvar <fullAndShortNames; // help variable for constructing osc+bus names

	*oscNames {
		oscNames ?? { this.makeOscNames };
		^oscNames;
	}

	*makeOscNames {
		var inc = 3;
		this.makeFullAndShortNames;
		oscNames = IdentityDictionary();
		fullAndShortNames pairsDo: { | full, short |
			oscNames[full] = inc;
			oscNames[short] = inc;
			inc = inc + 1;
			varNames do: { | vn |
				oscNames[(full++vn).asSymbol] = inc;
				oscNames[(short++vn).asSymbol] = inc;
				inc = inc + 1;
			}
		}
	}

	*makeFullAndShortNames { fullAndShortNames = [fullNames, shortNames].flop.flat; }

	*busNames {
		busNames ?? { this.makeBusNames };
		^busNames;
	}

	*makeBusNames {
		var inc = 0;
		this.makeFullAndShortNames;
		busNames = IdentityDictionary();
		fullAndShortNames pairsDo: { | full, short |
			busNames[full] = inc;  // do not increment
			busNames[short] = inc; // these stand for the entire joint
			varNames do: { | vn |
				busNames[(full++vn).asSymbol] = inc;
				busNames[(short++vn).asSymbol] = inc;
				inc = inc + 1;
			}
		}
	}

	*osc { | key | ^this.oscNames[key] }
	*bus { | key | ^this.busNames[key] }

	*toBus { | key, increment = 0 |

	}
}
