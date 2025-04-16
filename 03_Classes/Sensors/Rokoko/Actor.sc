// 火 15  4 2025 18:56
// hold the buses for writing all data from an actor wearing Rokoko
// tracker suit. Provide methods for writing + reading parts of the data,
// Provide interface for adding behaviors.
/* joint names as sent by Rokoko OSC are:
[ 'hip', 'spine', 'chest', 'neck', 'head', 'leftShoulder', 'leftUpperArm', 'leftLowerArm', 'leftHand', 'rightShoulder', 'rightUpperArm', 'rightLowerArm', 'rightHand', 'leftUpLeg', 'leftLeg', 'leftFoot', 'leftToe', 'leftToeEnd', 'rightUpLeg', 'rightLeg', 'rightFoot', 'rightToe', 'rightToeEnd' ]
*/

Actor {
	classvar <>numControls = 161; // 23 * 7
	var <name; // the name of this actor
	var <scene; // the name of the scene containing the actor
	var <envirName; // unique name for each actor in each scene.
	// used to create the envir of the actor
	var <envir; // a Mediator storing the actor and all its joints
	var <inbus; // holds all joint variable control values in 161 channels,
	// set at once from OSC input, for efficiency.
	var <outbus; // same as inbus, but for sending controls to Godot
	var <joints; // dictionary of individual joints by name
	*new { | name = \defaultActor, scene = \defaultScene |
		ServerBoot add: { this.makeBuses };
		^this.newCopyArgs(name.asSymbol, scene.asSymbol).init;
	}

	init {
		envirName = (name ++ "_" ++ scene).asSymbol;
		this.makeEnvir;
		this.makeGlobalBuses;
		this.makeJoints;
	}

	makeEnvir {
		envir = envirName.envir;
		envir[\actor] = this; // store self as actor in envir;
	}

	makeGlobalBuses {
		// make the global input and output bus buses;
		inbus !? { inbus.free; };
		inbus = Bus.control(Server.default, numControls);
		envir[\inbus] = inbus;
		outbus !? { outbus.free; };
		outbus = Bus.control(Server.default, numControls);
		envir[\outbus] = outbus;
	}

	makeJoints { // make the joints (and their buses)
		joints = ();
		Joint.makeJointsFor(this) do: { | j | joints[j.name] = j; };
	}

	writeDataToBus { | data |
		// postln("writing joint data" + data[3..].clump(8).flop.first);
		inbus.setn(Rokoko getControlValues: data);
	}

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << name.asString;
		stream << ">" ;
	}

	at { | argKey | ^envir.at(argKey) }
}