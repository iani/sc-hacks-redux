// 火 15  4 2025 05:05
// Holds the 7 buses for a Joint data from Rokoko: x, y, z, ....
/* Geometry data format is
	Position: x, y, z    (cartesian)
	Rotation: x, y, z, w (quaternion)

Corresponding variables are:
	Position: x, y, z
	Rotation: rx, ry, rz, rw

The formula for converting a quaternion to euler notation (i.e. x, y, z)
is given in Python code in file Quaternion2Euler.py in the present folder.

*/
Joint {
	classvar <>jointNames = #[
		'hip', 'spine', 'chest', 'neck', 'head',
		'leftShoulder', 'leftUpperArm', 'leftLowerArm', 'leftHand',
		'rightShoulder', 'rightUpperArm', 'rightLowerArm', 'rightHand',
		'leftUpLeg', 'leftLeg', 'leftFoot', 'leftToe', 'leftToeEnd',
		'rightUpLeg', 'rightLeg', 'rightFoot', 'rightToe', 'rightToeEnd'
	];

	/*
	classvar <>shortJointNames = #[
		'hi', 'sp', 'ch', 'ne', 'he',
		'ls', 'lu', 'lo', 'la',
		'rs', 'ru', 'ro', 'ra',
		'lul', 'lg', 'lf', 'lt', 'le',
		'rul', 'rg', 'rf', 'rt', 're'
	]
	*/

	classvar varNames = #[\x, \y, \z, \rx, \ry, \rz, \rw];
	var <actor, <name, <jointIndex;

	*makeJointsFor { | actor | // make all joints for actor
		^jointNames collect: { | jn | this.new(actor, jn); };
	}

	*new { | actor, name |
		^this.newCopyArgs(actor, name).makeBusses.storeInEnvir;
	}

	makeBusses {
		// create the individual busses and store them in ector's envir
		var busNames, globalIndex, envir;
		globalIndex = actor.bus.index;
		jointIndex = jointNames.indexOf(name) * 7;
		envir = actor.envir;
		busNames = varNames collect: { | bn | (name ++ bn).asSymbol };
		// postln("global Index" + globalIndex, "my index" + jointIndex);
		busNames do: { | n, i |
			var localIndex;
			localIndex = globalIndex + jointIndex + i;
			envir[n] = Bus(\control, Server.default, localIndex, 1);
			/*
			postln(
				"name:" + n +
				"joint index" + jointIndex +
				"var index" + i
				+ "local index" + localIndex
			);
			*/
		}
	}

	storeInEnvir { actor.envir[name] = this }

	getBus { | dim = \x |
		var busname, bus;
		busname = (name ++ dim).asSymbol;
		bus = this.envir[busname];
		^actor.envir[(name ++ dim).asSymbol];
	}

	envir { ^actor.envir }

	// busses are set globally by actor for efficiency
	// See Actor.setBus
	/*
		setBuses { | argValues |
		// Set bus values from value array in order sent by Rokoko
		var xv, yv, zv, rxv, ryv, rzv, rwv;
		#xv, yv, zv, rxv, ryv, rzv, rwv = argValues;
		x.set(xv);
		y.set(yv);
		z.set(zv);
		rx.set(rxv);
		ry.set(ryv);
		rz.set(rzv);
		rw.set(rwv);
		}
	*/
}