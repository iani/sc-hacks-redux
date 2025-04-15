// 火 15  4 2025 18:56
// hold the buses for writing all data from an actor wearing Rokoko
// tracker suit. Provide methods for writing + reading parts of the data,
// Provide interface for adding behaviors.

Actor {
	var <name; // the name of this actor
	var <scene; // the name of the scene containing the actor
	var <envirName; // unique name for each actor in each scene.
	// used to create the envir of the actor
	var <envir; // a Mediator storing the actor and all its joints
	*new { | name = \defaultActor, scene = \defaultScene |
		^this.newCopyArgs(name.asSymbol, scene.asSymbol).init;
	}

	init {
		envirName = (name ++ "_" ++ scene).asSymbol;
		this.makeEnvir;
	}

	makeEnvir {
		envir = envirName.envir;
		envir[\actor] = this; // store self as actor in envir;
	}

	setJoint { | data |
		// postln("Actor" + name + "Makes joint" + data[0]);
		// var jointName, joint;
		// jointName = data[0].asSymbol;
		// joint = joints[jointName];
		// joint ?? { joint = Joint(data, joints); };
	}

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << name.asString;
		stream << ">" ;
	}
}