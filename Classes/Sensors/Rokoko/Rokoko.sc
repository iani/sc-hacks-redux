//: 月 14  4 2025 19:47
//: Receive data messages from Rokoko suit and write the values into busses.
//: How many busses and channels per Rokoko actor?
/*
~numChans = 23 * 7 = 161
A 5 actor scenne would have:
 23 * 7 * 5
805 channels

Server.default.options.numControlBusChannels

Max number of actors:
16384 / 161 = 101.76397515528 actors
Max Number of actors if including additional 161 output busses per actor:
16384 / 161 / 2 = 50.88198757764

*/

Rokoko : Singleton {
	classvar <>verbose = false;
	var <name; // name of Rokoko instance
	var <envir; // the environment holding all actors
	var <>recvMsg = '/rokoko/'; // expect data from Rokoko at this message
	var <>sendMsg = '/rokokoOut/'; // send messages to Godot with this message

	//	*new { | name = \rokoko | ^this.named(name); }

	// return an instance named after today's date
	*atDate { | postfix = \rokoko, date |
		date ?? { date = Date.localtime.format("%y%m%d"); };
		^this.named((date ++ postfix).asSymbol);
	}

	init { | argName ... args |
		name = argName;
		envir = name.envir;
		envir[\rokoko] = this;
		this.enable;
	}

	enable {
		postln("enabling" + this + "named" + name);
		recvMsg.addAction({ | n, data, time |
			this.receiveOsc(data);
		}, name);
	}

	receiveOsc { | data |
		if (verbose) { data.postln };
		this.getActor(data[2].asSymbol).writeDataToBus(data);
	}

	getActor { | actorName |
		var actor;
		actorName = actorName.lowcap;
		actor = envir[actorName];
		actor ?? {
			actor = Actor(actorName, name);
			envir[actorName] = actor;
		};
		^actor;
	}

	actors {
		^envir.values.select({ | a | a isKindOf: Actor }).asArray;
	}

	disable {
		postln("disabling" + this + "named" + name);
		recvMsg.removeOSC(name)
	}

	oscDataGui {  | oscDataRef | this.oscData(oscDataRef, true); }

	oscData { | oscDataRef, makeGui = false |
		// get path of data folder from user (if not already known)
		// store path under your name as filename.
		// open an OscData gui with these paths
		// store OscData instance in oscDataRef for further processing;
		var data;
		Paths.doGetPath({ | p, paths |
			var oscdata;
			oscdata = OscData(paths);
			oscDataRef ?? { oscDataRef = Ref() };
			oscDataRef.value = oscdata;
			if (makeGui) { oscdata.gui };
			data = oscdata;
		}, name);
		^data
	}

	push { envir.push }

	/*
	*openPlayer { | argKey = \rokoko |
		PathAction({ | p, paths |
			"-----------------".postln;
			postln("path" + p);
			"================== PATHS:".postln;
			paths do: _.postln;
			postln("paths size" + paths.size);
			// OscData(paths).gui;
		}, argKey);
	}
	*/
	*openPlayer { | argKey = \rokoko |
		var dataRef;
		Paths.doGetPath({ | p, paths |
			dataRef = Ref(OscData(paths).gui)
		}, argKey);
		^dataRef;
	}

	*resetPath { | argKey = \rokoko |
		Paths.resetPath(argKey);
	}

	*getControlValues { | data |
		/// get control values from data
		/// also used to collect data for processing in sclang
		^data[3..].clump(8).collect({|j| j[1..]}).flat
	}

	printOn { | stream |
		if (stream.atLimit) { ^this };
		stream << this.class.name << "<" ;
		stream << name.asString;
		stream << ">" ;
	}
}
