// 金 20  6 2025 20:05
// Store joints of Rokoko figure in one
// small class, for easy modification.
// (The joints were copied from class Joint.)
// Construct a valid Rokoko Osc message from a numerical array
// received from AnimationPlayer.
// This class is used by AnimationPlayer.
/*
RokokoJoints makeOscMessage: (1..161);
*/
// RokokoJoints instances can construct messages with a subset
// of the joints.

RokokoJoints {
	var <activeJoints;
	var <jointFilter;
	classvar <>joints = #[
		'hip', 'spine', 'chest', 'neck', 'head',
		'leftShoulder', 'leftUpperArm', 'leftLowerArm', 'leftHand',
		'rightShoulder', 'rightUpperArm', 'rightLowerArm', 'rightHand',
		'leftUpLeg', 'leftLeg', 'leftFoot', 'leftToe', 'leftToeEnd',
		'rightUpLeg', 'rightLeg', 'rightFoot', 'rightToe', 'rightToeEnd'
	];

	classvar <>shortJointNames = #[
		'hi', 'sp', 'ch', 'ne', 'he',
		'ls', 'lu', 'lo', 'lh',
		'rs', 'ru', 'ro', 'rh',
		'lul', 'll', 'lf', 'lt', 'lte',
		'rul', 'rl', 'rf', 'rt', 'rte'
	];

	classvar <>vars = #[\x, \y, \z, \qx, \qy, \qz, \qw];

	classvar <synthArgs; // for setting synth from OSC message

	*new {
		this.makeSynthArgs;
		^super.new;
	}

	*makeSynthArgs {
		joints do: { | j |
			vars do: { | v |
				synthArgs = synthArgs.add(format("%%", j, v).asSymbol)
			}
		}
	}


	*makeOscMessage { | nums, name = 'Baubo' |
		nums = nums clump: 7;
		^this.makeHeader ++
		(joints collect: { | j, i | [j, nums[i]]; }).flat;
	}

	*makeHeader { | argName = 'Baubo' |
		^['/rokoko/', Clock.seconds, argName];
	}

	activeJoints_ { | active |
		activeJoints = active;
		this.makeJointFilter;
	}

	makeJointFilter {
		jointFilter = joints collect: { | j |
			if (activeJoints includes: j) { j } { nil }
		}
	}

	makeOscMessage { | nums, argName = 'Baubo' |
		^this.makeHeader(argName) ++ this.selectJoints(nums.clump(7));
	}

	makeHeader { | argName |
		^this.class.makeHeader(argName);
	}

	selectJoints { | nums |
		var selected;
		jointFilter do: { | j, i |
			if (j.notNil) { selected = selected add: [j, nums[i]] };
		};
		^selected.flat;
	}

	busIndex { | joint = \hip, argVar = \x, bus |
		^bus.index + this.jointVarIndex(joint, argVar);
	}

	jointVarIndex { | joint = \hip, argVar = \x |
		^this.jointIndex(joint) * 7 + this.varIndex(argVar);
	}

	jointIndex { | joint = \hip |
		^joints indexOf: joint;
	}

	varIndex { | argVar = \x |
		^vars indexOf: argVar;
	}

	numChannels {
		^joints.size * 7
	}

	enumerate { | func |
		var i = 0;
		joints do: { | j |
			vars do: { | v |
				func.(i, j, v);
				i = i + 1;
			}
		}
	}

	osc2synth { | osc |
		// construct synth args for setting bus for animation
		osc = osc[3..].clump(8).flop;
		osc = osc[1..].flop;
		^[synthArgs, osc.flat].flop.flat;
		// ^osc;
	}
}