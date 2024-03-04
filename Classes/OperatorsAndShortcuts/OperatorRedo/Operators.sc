// Sun  3 Mar 2024 10:28
// All operators in one file, organized in groups.
// =============== +> ===================
// +>

// [x] ArrayUGenShortcuts.sc     Array-+>
// EventOperators.sc         Event-+>
// EventStream.sc            EventStream-+>
// FunctionOperators.sc      Function-+>
// SymbolOperators.sc        Nil-+>
// SimpleNumberOperators.sc  SimpleNumber-+>
// SymbolOperators.sc        Symbol-+>
// UGenShortcuts.sc          UGen-+>

// ~/Dev/SCdev/LibsByMe/sc-hacks-redux/Classes/OperatorsAndShortcuts/SystemOverwrites/ArrayUGenShortcuts.sc

+ Array {
	+> { | ugenfunc |
		// "This is Array+>ugenfunc!!!!!!!".postln;
		^ugenfunc.ar(this) } // play as input to other ugen
}

+ Event {

	+> { | player, envir |
		// "This is Event+>ugenfunc!!!!!!!".postln;
		^this.pushPlayInEnvir(player, envir ? player, true)
	}
}

+ EventStream {
		+> { | player, envir |
			// "This is EventStream+>ugenfunc!!!!!!!".postln;
			^this.pushPlayInEnvir(player, envir ? player, true) }

}

// FunctionOperators.sc      Function-+>
+ Function {
		+> { | player, envir |
			// "This is Function+>ugenfunc!!!!!!!".postln;
			^this.pushPlayInEnvir(player, envir ? player, true)
		}
	// older version:
		// See OperatorFix240222.sc
	// +> { | player, envir |
	// 	^this.pushPlayInEnvir(player, envir);
	// }


}

// SymbolOperators.sc        Nil-+>

+ Nil {
	+> { | player, envir |
		// "This is Nil+>player, envir!!!!!!!".postln;
		Mediator.wrap(
			{
				// currentEnvironment[player].playNext;
				// postln("debugging Nil+>. player is:" + currentEnvironment[player]);
				// currentEnvironment.postln;
				currentEnvironment[player].free;
			},
			envir ? player
		)
	}
}

// SimpleNumberOperators.sc  SimpleNumber-+>
+ SimpleNumber {
	+> { | param, envir |
		// "This is SimpleNumber+>player, envir!!!!!!!".postln;

		param ?? { ^"SimpleNumber +> requires a parameter adverb".warn };
		envir = envir ? \default;
		envir.envir.put(param, this);
	}
}
// SymbolOperators.sc        Symbol-+>

+ Symbol {
	+> { | player, envir |
		// 240224: Hack for setting instrument key in events.
		if (player isKindOf: Event) {
			^player.put(\instrument, this);
		}{
			^this.pushPlayInEnvir(player, envir);
		}
    }
}
// UGenShortcuts.sc          UGen-+>

+ UGen {

	+> { | ugenfunc |
		// "This is Symbol+>ugenfunc !!!!!!!".postln;
		^ugenfunc.ar(this) } // play as input to other ugen
}

// ++>

//Fri 23 Feb 2024 09:21
// EventOperators.sc
// SymbolOperators.sc

+ Event {
	++> { | key, envir |
		// Set all key-value pairs of the receiver to the object at key/envir
		// If object is EventStream: set keys of the Event.
		// Else if object is Synth, set all parameters corresponding to the keys
		Mediator.setEvent(this, key, envir);
		// var p;
		// Mediator.wrap({
		// 	p = currentEnvironment[key];
		// 	p ?? {
		// 		p = EventStream(this);
		// 		currentEnvironment.put(key, p);
		// 	};
		// 	// EventSream and Synth handle this differently:
		// 	currentEnvironment[key].setEvent(this);
		// }, envir);
	}
}

+ Symbol {
	// Sat 11 Nov 2023 08:18 - cancel !+> --- too cumbersome.
	// Mon 13 Nov 2023 22:29: Substitute for earlier !+> or +>
	++> { | param, envir |
		envir.envir.put(param, this);
	}
}


// +>>

//Fri 23 Feb 2024 09:34
//EventOperators
//FunctionOperators

+ Event {
	+>> { | player, envir |
		// create, store and
		// make this respond to OSC trigger messages
		// Use coupled with {} +>> key
		var estream;
		estream = this.playInEnvir(player, envir ? player, false);
		player >>> { estream.playNext };
		^estream;
	}
}

+ Function {
		+>> { | cmdName, player = \osctrigger |
		{
			cmdName ?? { cmdName = player };
			this.value.sendReply(cmdName.asOscMessage);
		} +> player;
	}
}

// =============== <+ ===================
// <+

//Fri 23 Feb 2024 09:28
//SymbolOperators

+ Symbol {
	<+ { | value, envir | envir.envir.put(this, value); }
}
// =============== !+> ==================
// !+>

//Fri 23 Feb 2024 09:25
//SmbolOperators.sc


+ Symbol {
		// // playing as synth is hardly used. Keep it for compatibility
	!+> { | player, envir |
	^this.pushPlayInEnvir(player, envir);
    }
}
// +>!

//Fri 23 Feb 2024 09:27
//SymbolOperators
//EventOperators

+ Symbol {
	// Sat 11 Nov 2023 08:19  restore +> play in envir
	// See SymbolOperators.sc
	// +> { | player, envir |
	//  	^this.pushPlayInEnvir(player, envir);
    // }

	+>! { | player, envir |
		// osc message triggers play next event of an EventStream player
		this >>> {
			Mediator.wrap(
				{ currentEnvironment[player].playNext },
				envir ? \default
			)
		}
	}
}

+ Event {
	+>! { | player, envir | // do not start
		^this.playInEnvir(player, envir, false);
	}
}
// =============== @ ===============
// @

//Fri 23 Feb 2024 12:09
//SymbolOperators

+ Symbol {
	@ { | envir | ^this.at(envir) }
	at { | envir | // has same effect as Symbol:player below?
		^Mediator.at(envir ? currentEnvironment.name).at(this);
	}
}

// @>

//Fri 23 Feb 2024 11:38
//Array

// ====== Array -> Buffer
+ Array {
	@> { | bufname |
		// send contents to buffer
		// Create new buffer and store it in bufname, under Library at Arrray (!)
		// If previous buffer exists under that name, free that buffer. (!?)
		var buf;
		buf = Library.at(Buffer, bufname); // Array.bufname? We'd need to remove on Server quit!
		if (buf.isNil) {
				buf = Buffer.sendCollection(Server.default, this);
		} {
			if ( buf.size == this.size ) {
				Buffer.sendCollection(Server.default, this);
			} {
				buf.free;
				buf = Buffer.sendCollection(Server.default, this);
			}
		};
		Library.put(Buffer, bufname, buf);
	}
}

// !!!!!!!!!!!!!!!! cleanup needed. which @> will we use for Array
+ Array { // generate bus names from base name + index of array element
	@> { | bus, playerEnvir |
		this do: { | el, in |
			el.perform('@>', format("%%", bus, in).asSymbol, playerEnvir);
		}
	}
}

// ====== Setting busses:

+ Symbol {
	// <+ { | value, envir | envir.envir.put(this, value); }
	@> { | targetBus, player |
		^().put(
			this.busify,
			targetBus.bus(nil, player ? currentEnvironment.name).index
		);
	}
}

+ Function {
	@> { | bus, player | // play as kr function in bus (or player) name
		player ?? { player = currentEnvironment.name };
		// postln("debugging function @>. bus is:" + bus + "player is" + player);
		// postln("prev synth is:" + player.envir[bus]);
		{
			Out.kr(bus.bus(nil, player), this.value.kdsr);
		}.playInEnvir(bus, player); // do not push envir !!!
	}
}

+ Event {
	@> { | bus, player |
		var busNames, busStreams, stream;
		player = player ? bus;
		busNames = this[\busNames] ?? { [bus] };
		busStreams = busNames collect: { | busName |
			stream = this[busName];
			stream ?? { stream = defaultParentEvent[busName] };
			if (stream.isNil) {
				postln("Missing value for bus: " + busName + "in Event: " + this);
				[busName.bus, 0]
			}{
				[busName.bus, stream.asStream]
			};
		};
		this[\busStreams] = busStreams;
		this[\play] = {
			var bus, val;
			~busStreams do: { | busStreamPair |
				#bus, val = busStreamPair;
				val = val.value;
				if (val.isNil) { // the next line actually never gets executed:
					postln("Stream for bus" + bus + "ended.");
				}{  // \_ is a pause. Do not set the value.
					if (val !== \_) { // only set if not pause \_
						bus.set(val);
					}
				};
			};
			val;
		};
		this.playInEnvir(player, \busses);
	}
}

+ SimpleNumber {
	@> { | bus, playerEnvir | // set bus value
		// works with new AND already existing busses.
		// Stop processes playing in this bus before setting a new value:
		playerEnvir ?? { playerEnvir = currentEnvironment.name };
		playerEnvir.envir[bus].free;
		bus.bus(nil, playerEnvir).set(this);
	}
}

+ Point {
	// create XLine from current value to x in y seconds
	@> { | bus, playerEnvir | // glide with xline
		var b;
		b = bus.bus(nil, playerEnvir ? currentEnvironment.name);
		b.get({ | v |
			// Hack: permit XLine ... with 0 to positive values :
			v = v max: 0.00000001;
			{ XLine.kr(v, x, y) }.perform('@>', bus, playerEnvir);
		})
	}
}

+ Nil {
	@> { | busPlayer, envir | // Stop player for bus
		// stop player with same name as bus in an environment
		busPlayer.stopPlayer(envir, 0);
	}
}

// <@

//Fri 23 Feb 2024 12:16
//SymbolOperators

+ Symbol {
	<@ { | bus, player |
		currentEnvironment[player].map(this, bus.bus)
	}
}

// =============== +@> ===============
// +@>

//Fri 23 Feb 2024 09:31
//EventOperators
//FunctionOperators

+ Event {
	+@> { | key | Mediator.setEvent(this, key, \busses); }
}

// +>@
+ Function {
	// set the target group of the player's environment and use it
	+>@ { | player, group = \default_group |
		player.envir[\target] = group;
		^this.pushPlayInEnvir(player, player);
	}
}
// +><@>

//Fri 23 Feb 2024 11:45
//FunctionOperators

+ Function {
	// override (bypass) the target group of the player's environment
	// but do not set it.
	+><@> { | player, group = \root |
		^this.pushPlayInEnvir(player, player, group);
	}
}

// >>@

//Fri 23 Feb 2024 12:28
//plusSymbolOSC.sc

+ Symbol {

	>>@ { | address |
		this.forwardOSC(address)
	}

}

// @@>
//Fri 23 Feb 2024 11:37
//Array

+ Array {
	@@> { | symbol, offset = 0 | ^this.brdup(symbol, offset) }
}

// =============== <+@ ===============
// <+@

//Fri 23 Feb 2024 11:59
//SymbolOperators

+ Symbol {
	<+@ { | value |
		this.bus.set(value)
	}
}



// ================ !> ==============
// !!>
//Fri 23 Feb 2024 12:02
//SymbolOperators

+ Symbol {
	!!> { | value, variable |
		this use: {
			currentEnvironment[variable] = value;
		}
	}
}
// ================ <! ==============
// <!

//Fri 23 Feb 2024 11:51
//SymbolOperators

+ Symbol {
	// EventStream played actions
	// Actions run every time an EventStream plays its next event
	<! { | func, envir | this.addEventStreamAction(func, envir); }
}

// >>!

//Fri 23 Feb 2024 12:24
//plusSymbolOSC

+ Symbol {
	>>! { | func, key |
		// Like >>> without prepending / to self.
		// For use with SendReply.
		OSC.addRaw(this, func, key);
	}
}

// ================ *> ==============
// *>

//Fri 23 Feb 2024 11:35
//ArrayOperators
//FunctionOperators
//SymbolOperators

+ Array {
	*> { | param, envir | // store in param of envir
		envir.envir.put(param, this);
	}
}

+ Function {
	*> { | player, envir | // play as routine
		this.routineInEnvir(player, envir);
	}
}

+ Symbol {
	// Pdefn
	*> { | value | ^this.pd(value) }
	pd { | value | ^Pdefn(this, value) }
}

// **>

//Fri 23 Feb 2024 11:41
//FunctionOperators

+ Function {
	**> { | player, envir | // infinite loop in envir
		{
			inf do: {
				var dur;
				dur = this.(player, envir);
				if (dur.isKindOf(SimpleNumber).not) { dur = 1 };
				dur.wait;
			}
		}.routineInEnvir(player, envir);
	}
}
// ================ &> ===============
// &>

//Fri 23 Feb 2024 12:11
//SymbolOperators

+ Symbol {
	&> { | value, envir = \default |
		envir.addKey(this, value);
	}
	addKey { | key, value | // add key to a player environment
		Mediator.at(this).put(key, value);
	}
}

// ================ ? ==============
// <?>
//Fri 23 Feb 2024 12:14
//SymbolOperators

+ Symbol {
	// ============================ Toggle. UNTESTED!!!
	<?> { | player, envir |
		^this.toggle(player, envir);
	}
	toggle { | player, envir |
		var process;
		Mediator.wrap({
			process = currentEnvironment[this];
			if (process.isPlaying) {
				process.stop
			}{
				process = player.playInEnvir(this, envir);
			}
		}, envir);
		^process;
	}
}

// >>?

//Fri 23 Feb 2024 12:27
//plusSymbolOsc

+ Symbol {

	>>? { | key | // does OSC respond to this message under this key?
		// not yet implemented
		"Symbol>>? is not yet implemented".postln;
	}

}
// ================ ! ==============
// !!*
//Fri 23 Feb 2024 12:04
//SymbolOperators

+ Symbol {
	// Operate in the environment named by me:
	!!* { | funcName |
		^this evalLocalFunc: funcName;
	}
	evalLocalFunc { | funcName |
		^this use: { currentEnvironment[funcName].value };
	}
}

// !!!

//Fri 23 Feb 2024 12:05
//SymbolOperators

+ Symbol {

	!!! { | func | ^this use: func }

	use { | func | // evaluate func in this Mediator
		// var envir, result;
		// postln("debugging symbol use");
		// envir = Mediator.at(this);
		// postln("Environment before eval func is:");
		// envir.postln;
		// result = Mediator.at(this) use: func;
		// postln("Environment after eval func is:");
		// postln(result);
		// ^result;
		^Mediator.at(this) use: func;
	}
}
// =============== > ===============
// >>>
//Fri 23 Feb 2024 12:19
//ForwardOsc - Array
//ForwardOsc - SimpleNumber
//plusSymbolOSC - Symbol

+ Array {
	>>> { | message, port |
		([message] ++ this).forwardOsc(port);
	}

	fosc { | port | this.forwardOsc(port) }
	forwardOsc { | port | ForwardOsc.forward(this, port); }
}

+ SimpleNumber {
	>>> { | message, port |
		[message, this].forwardOsc(port);
	}
}

+ Symbol {
	>>> { | func, key | // add OSC response to this message under key
		// One can add different functions for the same message under different keys
		// The receiver becomes the message that OSC will respond to.
		// The key can optionally be used to add several actions for the same message.
		// The key becomes a Notification's listener, and the receiver becomes
		// the Notification's message.
		this.addAction(func, key);
	}
}
// =============== < ===============
// <<<
//Fri 23 Feb 2024 12:25
//plusSymbolOsc

+ Symbol {
	<<< { | key | // remove action registered under this message and key pair.
		this.removeOSC(key);
	}
}
// =============== +++ =================

//Fri 23 Feb 2024 09:29
//SymbolOperators

+ Symbol {
	+++ { | obj | ^(this ++ obj).asSymbol }
}

// =============== -- =================

//Fri 23 Feb 2024 12:10
//SymbolOperators

+ Symbol {
	-- { | envir, time = 1 |
		// Stop player from specified envir
		Mediator.at(envir).at(this).stop(time);
	}
}