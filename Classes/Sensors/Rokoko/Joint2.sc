// 金  9  5 2025 20:52 Redoing Joint
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
Joint2 {
	classvar <>jointNames = #[
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

	classvar jointNameDict; // for translating from long name to short name

	classvar <>varNames = #[\x, \y, \z, 'X', 'Y', 'Y', 'W'];
	var <actor, <name, <jointIndex;

	jointNameDict {
		^jointNameDict ?? {
			jointNameDict = IdentityDictionary();
			jointNames do: { | key, index |
				jointNameDict[key] = shortJointNames[index];
			};
			jointNameDict;
		}
	}

	shortName { ^this.jointNameDict[name] }

	*makeJointsFor { | actor | // make all joints for actor
		^jointNames collect: { | jn | this.new(actor, jn); };
	}

	*new { | actor, name |
		^this.newCopyArgs(actor, name).makeBusses.storeInEnvir;
	}

	makeBusses {
		var busNames, shortBusNames, globalIndex, envir, sn;
		jointIndex = jointNames.indexOf(name) * 7;
		globalIndex = actor.bus.index;
		envir = actor.envir;
		busNames = varNames collect: { | bn | (name ++ bn).asSymbol };
		sn = this.shortName;
		shortBusNames = varNames collect: { | bn | (sn ++ bn).asSymbol };
		busNames do: { | n, i |
			var busIndex, bus;
			busIndex = globalIndex + i;
			bus = Bus(\control, busIndex, 1, Server.default);
			envir[n] = bus;
			envir[shortBusNames[i]] = bus;
		}
		// postln("actor" + actor + "joint" + name);
		// postln("busNames" + busNames);
		// postln("shortBusNames" + shortBusNames);
	}

	storeInEnvir { actor.envir[name] = this }

	getBus { | dim = \x |
		var busname, bus;
		busname = (name ++ dim).asSymbol;
		bus = this.envir[busname];
		^actor.envir[(name ++ dim).asSymbol];
	}

	envir { ^actor.envir }
	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << name.asString;
		stream << ">" ;
	}
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