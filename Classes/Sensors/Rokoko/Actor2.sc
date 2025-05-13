// 金  9  5 2025 20:51 Redoing Actor
// Actor2 adds itself as dependant to RokokoData2 and listens to changed \play.
// It then access the actor named in an OSC message and passes the latest
// data to the joints of that Actor.
// Actor responds by filtering the data according to
// 火 15  4 2025 18:56
// hold the buses for writing all data from an actor wearing Rokok
// tracker suit. Provide methods for writing + reading parts of the data,
// Provide interface for adding behaviors.
/* joint names as sent by Rokoko OSC are:
[ 'hip', 'spine', 'chest', 'neck', 'head', 'leftShoulder', 'leftUpperArm', 'leftLowerArm', 'leftHand', 'rightShoulder', 'rightUpperArm', 'rightLowerArm', 'rightHand', 'leftUpLeg', 'leftLeg', 'leftFoot', 'leftToe', 'leftToeEnd', 'rightUpLeg', 'rightLeg', 'rightFoot', 'rightToe', 'rightToeEnd' ]
*/

Actor2 {
	classvar <>numControls = 161; // 23 * 7
	classvar all;   // dictionary of actors.
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

	classvar <>varNames = #[\x, \y, \z, 'X', 'Y', 'Y', 'W'];

	var <name;      // the name of this actor
	var <scene;     // the name of the scene containing the actor
	var <envirName; // unique name for each actor in each scene.
	// used to create the envir of the actor
	var <envir;  	// a Mediator storing the actor and all its joints
	var <bus;    	// holds all joint variable control values in 161 channels,
	var <indata;  	// the latest message received from Rokkoko.
	var <>outdata; 	// data modified by action, sent to GODOT etc.
	// var <joints; // dictionary of individual joints by name
	var action;  // action for reacting to data. Only one is allowed.
	*new { | name = \defaultActor, scene = \defaultScene |
		^this.newCopyArgs(name.asSymbol, scene.asSymbol).init;
	}

	*all { ^all ?? { all = IdentityDictionary() } }

	init {
		envirName = (name ++ "_" ++ scene).asSymbol;
		this.makeEnvir;
		ServerBoot add: { this.makeBusAndJoints; };
		Server.default.waitForBoot { this.makeBusAndJoints };
	}

	makeEnvir {
		envir = envirName.envir;
		envir[\actor] = this; // store self as actor in envir;
	}

	makeBusAndJoints {
		this.makeBus;
		this.makeJoints;
	}

	makeBus {
		// make the global input and output bus buses;
		bus !? { bus.free; };
		postln("Making bus for" + this);
		bus = Bus.control(Server.default, numControls);
		this.changed(\madeBus);
	}

	makeJoints { // make the joints (and their buses)
		[jointNames, shortJointNames].flop do: { | jn, i |
			var bus, jointIndex;
			jointIndex = i * 7;
			varNames do: { | vn, j |
				bus = Bus(\control, jointIndex + j, 1, Server.default);
				envir[(jn[0] ++ vn).asSymbol] = bus;
				envir[(jn[1] ++ vn).asSymbol] = bus;
			}
		};
	}

	at { | jointDim | ^envir[jointDim] }
	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << name.asString;
		stream << ">" ;
	}

	// action group modifying data
	action { ^action ?? { action = ActorAction(this); }}

	numControls { ^numControls } // instance method for class variable
	push { envir.push; }
}